package autocorrelation;

import java.util.HashSet;

import data.Data;
import data.Datapoint;
import data.Feature;
import distance.WeightI;

public class GetisOrdSingleFeature {
	private Feature f;
	private HashSet<Record> neighborhood;
	private WeightI weight;
	
	//Saving variables that are usefull in computing NUM and DEN
	private double z = 0d;
	private short featureIdx;
	
	public GetisOrdSingleFeature(Feature f, HashSet<Record> neighborhood, WeightI weight) {
		this.f = f;
		this.neighborhood = neighborhood;
		this.weight = weight;
	}
	
	double compute(Data data, short x, short y){
		double S = computeS(data, x, y);
		double L = computeL(data, x, y); //I can compute them just one time (maybe in GetisOrd class) for each feature. It may been optimized.
		
		//Using just one loop I can compute NUM and DEN (just SUM)
		double num = 0d;
		double sumDen = 0d;
		
		for(Object neighbour : neighborhood){
			Record r = (Record) neighbour;
			double zj = (r.dp).getValue(featureIdx);
			double lij = weight.compute(x, y, r.x, r.y);
			
			num += (lij * zj) - (z * L);
			sumDen += (Math.pow(lij, 2) - Math.pow(L, 2));
		}
		
		//NUM done. Completing DEN
		short n = (short) (neighborhood.size());
		double den = (S/(n-1)) *  (n * sumDen);
				
		return num / den;
	}
	
	//Computing S^2
	private double computeS(Data data, short x, short y){
		Datapoint dp = data.getDatapoint(x,y);
		
		double sum = 0d;
		short n = (short) (neighborhood.size());
		featureIdx = 0;
		
		/*
		 * Per prima cosa identifico la feature che mi interessa.
		 * Per il calcolo di S mi servono due cicli (perchè se prima non ho calcolato z segnato non posso calcolare la sommatoria):
		 * - Il primo calcolerà z segnato sul vicinato e sul datapoint in questione
		 * - Il secondo calcolerà la sommatoria della formula, che verrà poi divisa per n.
		 */
		
		//Calcolo z segnato prima sul vicinato e poi aggiungo il valore del datapoint in questione
		for(Object feature : data.getFeatureVector()){
			if(((Feature)feature).equals(f)){	
				for(Object neighbour : neighborhood){
					Record r = (Record) neighbour;
					z += (r.dp).getValue(featureIdx);
				}
				z += dp.getValue(featureIdx); //add datapoint value to his neighbours' values
				z = z / (n+1); //neighbour + datapoint
				break;
			}
			else
				featureIdx++;
		}
		
		//Compute the SUMMATORY. I know the feature index yet, 'cause I saved it in variable featureIdx, so...
		for(Object neighbour : neighborhood){
			Record r = (Record) neighbour;
			double zj = (r.dp).getValue(featureIdx);
			sum += Math.pow((zj - z), 2);
		}
		//In the end, add datapoint value
		sum += dp.getValue(featureIdx);
		
		return sum / n; 
	}
	
	//Computing Lambda(i)
	private double computeL(Data data, short x, short y){
		double sum = 0d;
		
		for(Object neighbour : neighborhood){
			Record r = (Record) neighbour;
			sum += weight.compute(x, y, r.x, r.y);
		}
		return sum;
	}
	
}
