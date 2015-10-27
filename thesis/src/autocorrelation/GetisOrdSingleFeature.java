package autocorrelation;

import java.util.HashSet;

import data.Data;
import data.Datapoint;
import data.Feature;
import distance.WeightI;

public class GetisOrdSingleFeature {
	private int featureIdx;
	private HashSet<Datapoint> neighborhood;
	private WeightI weight;
	private short n;
	private double Z;
	private double S;
	private double L;
	
	public GetisOrdSingleFeature(int featureIdx, HashSet<Datapoint> neighborhood, WeightI weight) {
		this.featureIdx = featureIdx;
		this.neighborhood = neighborhood;
		this.weight = weight;	
	}
	
	double compute(Data data, short x, short y){
		this.n = data.size();
		Z = computeZ(data, featureIdx);
		S = computeS(data, featureIdx);
		L = computeL(x, y);
		
		/* Test
		if(featureIdx == 1){
			System.out.println("z: " + Z);
			System.out.println("S: " + S);
			System.out.println("L: " + L);
		} */
		
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
		num -= Z*L;

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
	
	// Compute Z
	private double computeZ(Data data, int featureIdx){
		double z = 0d;
		for(Object obj : data){
			z += ((Datapoint)obj).getValue(featureIdx);
		}
		return ( z / n );
	}
	
	// Compute S^2
	private double computeS(Data data, int featureIdx){
		double sum = 0d;
		for(Object dp : data){
			double zj = ((Datapoint)dp).getValue(featureIdx);
			sum += Math.pow((zj - Z), 2);
		}
		return sum / n; 
	}
	
	// Compute L(i)
	private double computeL(short x, short y){
		double sum = 0d;
		
		for(Object obj : neighborhood){
			Datapoint neighbour = (Datapoint)obj;
			sum +=  weight.compute(x, y, neighbour.x, neighbour.y);
		}
		return sum;
	}
	
}
