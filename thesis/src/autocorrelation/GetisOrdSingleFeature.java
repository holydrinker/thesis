package autocorrelation;

import java.util.HashSet;

import data.Data;
import data.Datapoint;
import data.Feature;
import distance.WeightI;

public class GetisOrdSingleFeature {
	private Feature f;
	private HashSet<Datapoint> neighborhood;
	private WeightI weight;
	
	//Saving variables that are usefull in computing NUM and DEN
	private double z = 0d;
	private short featureIdx;
	private short n = 0; //dataset size. init in computeS
	
	public GetisOrdSingleFeature(Feature f, HashSet<Datapoint> neighborhood, WeightI weight) {
		this.f = f;
		this.neighborhood = neighborhood;
		this.weight = weight;
	}
	
	double compute(Data data, short x, short y){
		this.z = computeZ(data); 
		double S = computeS(data);
		double L = computeL(data, x, y);
		
		//Using just one loop I can compute NUM and DEN (just SUM)
		double num = 0d;
		double sumDen = 0d;
		
		for(Object neighbour : neighborhood){
			Datapoint j = (Datapoint) neighbour;
			short dpX = j.x;
			short dpY = j.y;
			
			double zj = j.getValue(featureIdx);
			double lij = weight.compute(x, y, dpX, dpY);
			
			num += (lij * zj);
			sumDen += (Math.pow(lij, 2));
		}
		num -= z*L;

		/*
		 * Per non far venire numeri negativi sotto aggiungo questo controllo, perchè molto spesso quando confronto il datapoint centrale con le celle
		 * a distanza 1, quindi esattamente attaccante, il peso, calcolato così: 1 / (distanza^q) viene sempre 1, e quindi al denominatore all'interno
		 * della sommatoria succede che 1 - LAMBDA^2 viene un numero negativo, che rende tutto il denominatore negativo e che quindi quando poi vado
		 * a fare la radice quadrata del denominatore mi viene fuori un NaN, che mi rende tutto il valore NaN. Quindi mi conservo i segno e lo metto
		 * davanti al valore assoluto. 
		 * 
		 * EDIT: Posso rimuovere il controllo se mi si garantisce che mai avrà senso calcolare l'autocorrelazione con peso 1.
		 */
		
		//NUM done. Completing DEN
		double den = (S/(n-1)) *  (n * sumDen - Math.pow(L, 2));
		double denSqrt = Math.sqrt(Math.abs(den));
		
		if(den < 0)
			den = denSqrt * -1;
		else
			den = denSqrt;
		
		return ( num / den );
	}
	
	//Compute z signed and initialize n
	private double computeZ(Data data){
		double z = 0;
		featureIdx = 0;
		
		for(Object feature : data.getFeatureVector()){
			if(((Feature)feature).equals(f)){	
				for(Object obj : data){
					n++;
					z += ((Datapoint)obj).getValue(featureIdx);
				}
				z = z / n;
				break;
			}
			else {
				featureIdx++;
			}
		}
		
		return z;
	}
	
	//Computing S^2
	private double computeS(Data data){
		double sum = 0d;
		for(Object dp : data){
			double zj = ((Datapoint)dp).getValue(featureIdx);
			sum += Math.pow((zj - z), 2);
		}
		
		return sum / n; 
	}
	
	//Computing Lambda(i)
	private double computeL(Data data, short x, short y){
		double sum = 0d;
		for(Object obj : data){
			Datapoint dp = (Datapoint)obj;
			sum += weight.compute(x, y, dp.x, dp.y); //calcola il peso anche con se stesso ma non fa niente, tanto è zero
		}
		return sum;
	}
	
}
