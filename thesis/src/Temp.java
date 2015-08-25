import data.Datapoint;
import distance.EuclideanDissimilarity;

public class Temp {

	public static void main(String[] args) {
		Datapoint dp1 = new Datapoint(1, 3, 4, 5);
		Datapoint dp2 = new Datapoint(2, 3, 4, 5);
		
		EuclideanDissimilarity dissimilarity = new EuclideanDissimilarity(dp1, dp2);
		double result = dissimilarity.compute();
		
		System.out.println("dissimilarity: " + result);
	}

}
