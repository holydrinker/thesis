                                                                                                                                        package autocorrelation;

import data.Datapoint;
import data.Dataset;
import distance.InverseWeight;
import io.StreamGenerator;

public class Tester {

	public static void main(String[] args) {
		System.out.println("autocorrelation.Tester");
		
		//Giusto un dataset per il testing del package
		StreamGenerator sg = new StreamGenerator("D:/dataset/ac.txt");
		Dataset data = new Dataset(sg.getFeatureVectorTO(), sg.getDataTO());
		
		//Stampo il dataset facendo vedere solo la seconda feature, quella che mi interessa, cioè VALUE
		int featureIdx = 1;
		System.out.println("DATASET PRE-AUTOCORRELATION");
		data.printAutocorrelationTest(featureIdx);
		
		//Setting up autcorrelation
		short radius = 2;
		byte q = 3;
		GetisOrd go = new GetisOrd(new InverseWeight(radius, q));
		
		//Test
		short x = 0;
		short y = 0;
		Datapoint dp1 = go.compute(data, x, y, radius);
		System.out.println("I: " + dp1.getValue(featureIdx));
		
		x = 5;
		y = 0;
		Datapoint dp2 = go.compute(data, x, y, radius);
		System.out.println("II: " + dp2.getValue(featureIdx));
		
		x = 5;
		y = 5;
		Datapoint dp3 = go.compute(data, x, y, radius);
		System.out.println("III: " + dp3.getValue(featureIdx));
	
	}
}
