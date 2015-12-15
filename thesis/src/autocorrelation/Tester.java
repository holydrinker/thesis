package autocorrelation;

import data.Data;
import data.Datapoint;
import data.Dataset;
import distance.InverseWeight;
import io.StreamGenerator;

public class Tester {

	public static void main(String[] args) {
		StreamGenerator sg = new StreamGenerator("../dataset/ac-minitest.arff");
		Data data = new Dataset(sg.getFeatureVectorTO(), sg.getDataTO(), false);
		System.out.println(data.toString());

		short radius = 2;
		byte exp = 3;
		GetisOrd go = new GetisOrd(new InverseWeight(radius, exp));
		int featureIDX = 1;
		
		for(int x = 0; x < data.getHeight(); x++){
			for(int y = 0; y < data.getWidth(); y++){
				int value = (data.getDatapoint((short)x, (short)y).getValue(featureIDX)).intValue();
				System.out.print(value + " ");
			}
			System.out.println("");
		}
		
		short x = 0;
		short y = 0;
		Datapoint attuale = data.getDatapoint(x, y);
		Datapoint exam = go.compute(data, x, y, radius);
		double value = exam.getValue(featureIDX);
		System.out.println("valore attuale: " + attuale.getValue(featureIDX));
		System.out.println("valore autocorrelato: " + value);
		
		x = 0;
		y = 6;
		attuale = data.getDatapoint(x, y);
		exam = go.compute(data, x, y, radius);
		value = exam.getValue(featureIDX);
		System.out.println("valore attuale: " + attuale.getValue(featureIDX));
		System.out.println("valore autocorrelato: " + value);
	}   

}
