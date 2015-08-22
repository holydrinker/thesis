package autocorrelation;

import data.Datapoint;
import data.Dataset;
import distance.InverseWeight;
import io.StreamGenerator;

public class Tester {

	public static void main(String[] args) {
		//Giusto un dataset per il testing del package
		StreamGenerator sg = new StreamGenerator("D:/dataset/inputFile.txt");
		Dataset data = new Dataset(sg.getFeatureVectorTO(), sg.getDataTO());
		
		//Autocorrelazione da testare
		GetisOrd go = new GetisOrd(new InverseWeight((short)2, (byte)3));
		
		//Datapoint da autocorrelare
		short x = 0;
		short y = 0;
		short radius = 1;
		Datapoint dp = go.compute(data, x, y, radius);
		
		//Printing test
		int i = 0;
		for(Object value : dp)
			if(i++ == 1)
				System.out.print(value + " ");
		
	}

}
