package data;

public class ContinueFeature extends Feature {
	private double min;
	private double max;
	
	public ContinueFeature(String name) {
		super(name);
		min = Double.MAX_VALUE;
		max = -1;
	}
	
	public void setMin(double min){
		if(min < this.min)
			this.min = min;
	}
	
	public void setMax(double max){
		if(max > this.max)
			this.max = max;
	}
	
	public double getScaled(double value){		
		return (value - min) / (max - min);
	}
}
