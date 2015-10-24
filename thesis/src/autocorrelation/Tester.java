package autocorrelation;

import data.Datapoint;
import data.Dataset;
import distance.InverseWeight;
import io.StreamGenerator;

public class Tester {

	public static void main(String[] args) {
		//PROVARE CON UN DATASET 2x3 E FARE I CONTI A MANO, VEDIAMO SE NE USCIAMO COSI
		
		System.out.println("autocorrelation.Tester\n");
		
		//Giusto un dataset per il testing del package
		StreamGenerator sg = new StreamGenerator("dataset/ac-dissimili.txt");
		Dataset data = new Dataset(sg.getFeatureVectorTO(), sg.getDataTO());
		
		//Stampo il dataset facendo vedere solo la seconda feature, quella che mi interessa, cioè VALUE
		int featureIdx = 1;
		System.out.println("DATASET PRE-AUTOCORRELATION");
		data.printAutocorrelationTest(featureIdx);
		System.out.println("");
		
		//Setting up autcorrelation
		short radius = 1;
		byte q = 3;
		GetisOrd go = new GetisOrd(new InverseWeight(radius, q));
		
		//Test
		short x = 5;
		short y = 6;
		
		Datapoint dp1 = null;
		for(int i = 0; i <= 5; i++){
			for(int j = 0; j <= 6; j++){
				dp1 = go.compute(data, (short)i, (short)j, radius);
				System.out.println(dp1.getValue(featureIdx));	
			}
		}
		
		//System.out.println("result: "+ dp1.getValue(featureIdx));
		
		/*
		x = 5;
		y = 0;
		Datapoint dp2 = go.compute(data, x, y, radius);
		System.out.println("(5,0): " + dp2.getValue(featureIdx));
		
		x = 5;
		y = 5;
		Datapoint dp3 = go.compute(data, x, y, radius);
		System.out.println("(5,5): " + dp3.getValue(featureIdx));*/
	
	}
}
