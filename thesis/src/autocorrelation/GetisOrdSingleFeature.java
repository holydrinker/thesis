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
	private short n;
	
	public GetisOrdSingleFeature(Feature f, HashSet<Record> neighborhood, WeightI weight) {
		this.f = f;
		this.neighborhood = neighborhood;
		this.weight = weight;
		this.n = (short) (neighborhood.size() + 1); //Neighbour size + central datapoint
	}
	
	double compute(Data data, short x, short y){
		double S = computeS(data, x, y);
		double L = computeL(data, x, y); //I can compute them just one time (maybe in GetisOrd class) for each feature. It may been optimized.
		
		//Using just one loop I can compute NUM and DEN (just SUM)
		double num = 0d;
		double sumDen = 0d;
		
		for(Object neighbour : neighborhood){
			Record r = (Record) neighbour;
			short dpX = r.x;
			short dpY = r.y;
			Datapoint dp = (r.dp);
			
			double zj = dp.getValue(featureIdx);
			double lij = weight.compute(x, y, dpX, dpY); //LAMBDA PROBLEM
			
			num += (lij * zj) - (z * L);
			sumDen += (Math.pow(lij, 2) - Math.pow(L, 2));
		}

		/*
		 * Per non far venire numeri negativi sotto aggiungo questo controllo, perchè molto spesso quando confronto il datapoint centrale con le celle
		 * a distanza 1, quindi esattamente attaccante, il peso, calcolato così: 1 / (distanza^q) viene sempre 1, e quindi al denominatore all'interno
		 * della sommatoria succede che 1 - LAMBDA^2 viene un numero negativo, che rende tutto il denominatore negativo e che quindi quando poi vado
		 * a fare la radice quadrata del denominatore mi viene fuori un NaN, che mi rende tutto il valore NaN. Quindi mi conservo i segno e lo metto
		 * davanti al valore assoluto.
		 */
		
		//NUM done. Completing DEN
		double den = (S/(n-1)) *  (n * sumDen);
		double denSqrt = Math.sqrt(Math.abs(den));
		
		if(den < 0)
			den = denSqrt * -1;
		else
			den = denSqrt;
		
		//System.out.println("num: " + num);
		//System.out.println("den: " + den);
		return num/den;
	}
	
	//Computing S^2
	private double computeS(Data data, short x, short y){
		Datapoint dp = data.getDatapoint(x,y);
		featureIdx = 0;
		
		/*
		 * Per prima cosa identifico la feature che mi interessa.
		 * Per il calcolo di S mi servono due cicli (perchè se prima non ho calcolato z segnato non posso calcolare la sommatoria):
		 * - Il primo calcolerà z segnato sul vicinato e sul datapoint in questione
		 * - Il secondo calcolerà la sommatoria della formula, che verrà poi divisa per n.
		 */
		
		//Calcolo z segnato prima sul vicinato e poi aggiungo il valore del datapoint in questione
		
		//Identifico la feature su cui lavorare e poi itero solo sul vicinato lavorando solo sulla feature che mi interessa
		for(Object feature : data.getFeatureVector()){
			if(((Feature)feature).equals(f)){	
				for(Object neighbour : neighborhood){
					Record r = (Record) neighbour;
					z += (r.dp).getValue(featureIdx);
				}
				z += dp.getValue(featureIdx); //add datapoint value to his neighbours' values
				z = z / n;
				break;
			}
			else
				featureIdx++;
		}
		
		//Compute the SUMMATORY. I know the feature index yet, 'cause I saved it in variable featureIdx, so...
		double sum = 0d;
		for(Object neighbour : neighborhood){
			Record r = (Record) neighbour;
			double zj = (r.dp).getValue(featureIdx);
			sum += Math.pow((zj - z), 2);
		}
		//In the end, add central datapoint value
		double zj = dp.getValue(featureIdx);
		sum += Math.pow((zj - z), 2);
		
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
