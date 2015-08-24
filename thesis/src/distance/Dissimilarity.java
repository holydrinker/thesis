package distance;

import data.Datapoint;

public class Dissimilarity implements DistanceI {
	private Datapoint dp1;
	private Datapoint dp2;
	
	public Dissimilarity(Datapoint dp1, Datapoint dp2) {
		this.dp1 = dp1;
		this.dp2 = dp2;
	}
	
	@Override
	public Double compute() {
		double acc1 = 0d;
		int n1 = 0;
		for(Object value : this.dp1){
			acc1 += (double) value;
			n1++;
		}
		double avg1 = acc1 / n1;
		
		double acc2 = 0d;
		int n2 = 0;
		for(Object value: this.dp2){
			acc2 += (double) value;
			n2++;
		}
		double avg2 = acc2 / n2;
		
		return Math.abs( (avg1 - avg2) );	
	}

}
