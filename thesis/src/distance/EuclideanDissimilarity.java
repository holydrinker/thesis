package distance;

import java.util.LinkedList;
import java.util.List;

import data.Datapoint;

public class EuclideanDissimilarity implements DistanceI {
	private Datapoint dp1;
	private Datapoint dp2;
	
	public EuclideanDissimilarity(Datapoint dp1, Datapoint dp2) {
		this.dp1 = dp1;
		this.dp2 = dp2;
	}

	@Override
	public Double compute() {
		List<Double> dimensions = new LinkedList<Double>();
		
		//First datapoint initalize dimensions list
		int i = 0;
		for(Double value : this.dp1){
			dimensions.add(i++, value);
		}
		
		//Second datapoint update dimensions
		i = 0;
		for(Double value : this.dp2){
			double oldValue = dimensions.get(i);
			dimensions.set(i++, (oldValue - value) );
		}
		
		//Compute euclidean distance
		double sum = 0d;
		for(double value : dimensions){
			sum += Math.pow(value, 2);
		}
		return Math.sqrt(sum);
	}
}
