package distance;

public class InverseWeight implements WeightI {
	private double radius; 
	private final byte q;
	private DistanceI distance;
	
	public InverseWeight(short radius, byte q) {
		if(q < 0)
			throw new InvalidExponent(q + "is not a valid exponent to compute InverseWeight value");
		
		this.radius = radius;
		this.q = q;
	}
	
	@Override
	public Double compute(short x1, short y1, short x2, short y2) {
		double result;
		
		distance = new EuclideanDistance(x1, y1, x2, y2);
		double d = distance.compute();
		
		//Slide 1282
		if(d == 0)
			result = 1d; 
		else if(d > radius)
			result = 0d;
		else
			result = ( 1 / (Math.pow(d, q)) );	
		
		return result;
	}

	
	
}
