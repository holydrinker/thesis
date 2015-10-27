package autocorrelation;

import data.Datapoint;
import data.Dataset;
import distance.InverseWeight;
import io.StreamGenerator;

public class Tester {

	public static void main(String[] args) {
		System.out.println("autocorrelation.Tester\n");
		String filePath = "dataset/ac-minitest.txt";
		
		//Carico un dataset per il testing del package
		StreamGenerator sg = new StreamGenerator(filePath);
		Dataset data = new Dataset(sg.getFeatureVectorTO(), sg.getDataTO());
		
		//Stampo il dataset facendo vedere solo la seconda feature, quella che mi interessa, cioè VALUE
		int featureIdx = 1;
		System.out.println("DATASET PRE-AUTOCORRELATION");
		data.printAutocorrelationTest(featureIdx);
		System.out.println("");
		
		//Setting up autcorrelation
		short radius = 2;
		byte q = 3;
		GetisOrd go = new GetisOrd(new InverseWeight(radius, q));
		
		// Test
		short x;
		short y;
		
		//Circondato da valori alti
		x = 0;
		y= 0;
		Datapoint dp1 = go.compute(data, x, y, radius);
		System.out.println(dp1.getValue(featureIdx));
		
		//Circondato da valori bassi
		x = 5;
		y = 6;
		Datapoint dp2 = go.compute(data, x, y, radius);
		System.out.println(dp2.getValue(featureIdx));
		
		//Circondato da valori casuali
		x = 5;
		y = 0;
		Datapoint dp3 = go.compute(data, x, y, radius);
		System.out.println(dp3.getValue(featureIdx));
		
		/*Datapoint dp1 = null;
		for(int i = 0; i <= 5; i++){
			for(int j = 0; j <= 6; j++){
				dp1 = go.compute(data, (short)i, (short)j, radius);
				System.out.println(dp1.getValue(featureIdx));	
			}
		}*/
	}
}
