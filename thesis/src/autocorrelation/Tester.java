package autocorrelation;

import data.Dataset;
import distance.InverseWeight;
import io.StreamGenerator;

public class Tester {

	public static void main(String[] args) {
		StreamGenerator sg = new StreamGenerator("inputFile.txt");
		Dataset data = new Dataset(sg.getFeatureVectorTO(), sg.getDataTO());
		GetisOrd go = new GetisOrd(new InverseWeight((short)2, (byte)3));
		short x = 0;
		short y = 0;
		short radius = 2;
		go.compute(data, x, y, radius);
		//Non da errori
	}

}
