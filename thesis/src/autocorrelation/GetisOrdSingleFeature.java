package autocorrelation;

import data.Datapoint;
import data.Dataset;
import data.Feature;
import distance.WeightI;

public class GetisOrdSingleFeature {
	private Feature f;
	private Neighborhood neighborhood;
	private WeightI weight;
	
	//Salvo in queste variabili tutto ciò che serve per il calcolo finale di numeratore e denominatore
	private double z = 0d;
	private short featureIdx;
	
	public GetisOrdSingleFeature(Feature f, Neighborhood neighborhood, WeightI weight) {
		this.f = f;
		this.neighborhood = neighborhood;
		this.weight = weight;
	}
	
	double compute(Dataset data, short x, short y){
		double S = computeS(data, x, y);
		double L = computeL(data, x, y); //Basterebbe calcolarlo una sola volta
		
		/*
		 * Calcolo separatamente il numeratore e denominatore.
		 * Con un unico ciclo posso calcolare l'intero numeratore e la sommatoria del denominatore 
		 */
		double num = 0d;
		double sumDen = 0d;
		
		for(Object neighbour : neighborhood){
			Record r = (Record) neighbour;
			double zj = (r.dp).getValue(featureIdx);
			double lij = weight.compute(x, y, r.x, r.y);
			
			num += (lij * zj) - z * L;
			sumDen += (Math.pow(lij, 2) - Math.pow(L, 2));
		}
		
		//a questo punto il numeratore è già completo, va completato il calcolo del denominatore
		short n = neighborhood.size();
		double den = (S/(n-1)) *  (n * sumDen);
				
		return num / den;
	}
	
	private double computeS(Dataset data, short x, short y){
		Datapoint dp = data.datapoints[y][x];
		
		double sum = 0d;
		short n = neighborhood.size();
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
				z += dp.getValue(featureIdx); //datapoint in questione
				z = z / n;
				break;
			}
			else
				featureIdx++;
		}
		
		//Calcolo la sommatoria. Qui conosco già la posizione del valore della feature, perchè ho salvato la variabile i dal ciclo precedente
		for(Object neighbour : neighborhood){
			Record r = (Record) neighbour;
			Datapoint nb = r.dp;
			double zj = nb.getValue(featureIdx);
			sum += Math.pow((zj - z), 2);
		}
		
		//restituisco la sommatoria diviso n
		return sum / n; 
	}
	
	private double computeL(Dataset data, short x, short y){
		double sum = 0d;
		
		for(Object neighbour : neighborhood){
			Record r = (Record) neighbour;
			sum += weight.compute(x, y, r.x, r.y);
		}
		return sum;
	}
	
}
