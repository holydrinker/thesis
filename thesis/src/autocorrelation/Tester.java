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
		
		short radius = 1;
		byte q = 3;
		
		//Autocorrelazione da testare
		GetisOrd go = new GetisOrd(new InverseWeight(radius, q));
		
		//Datapoint da autocorrelare
		short x = 0;
		short y = 0;
		Datapoint dp = go.compute(data, x, y, radius);
		
		//Print
		System.out.println("autocorrelation.Tester");
		int i = 0;
		for(Object value : dp)
			if(i++ == 1)
				System.out.print(value + " ");
	
	}

}
