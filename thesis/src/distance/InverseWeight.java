package distance;

public class InverseWeight implements WeightI {
	private double radius; //raggio d'azione per costruire il vicinato
	private final byte q; //esponente ottimale = 3
	private DistanceI distance; //funzione di distanza
	
	public InverseWeight(short radius, byte q) {
		if(q < 0)
			throw new InvalidExponent(q + "is not a valid exponent to compute InverseWeight value");
		
		this.radius = radius;
		this.q = q;
	}
	
	@Override
	public Double compute(short x1, short y1, short x2, short y2) {
		distance = new EuclideanDistance(x1, y1, x2, y2);
		double d = distance.compute();
		
		if(d == 0)
			return 1d;
		else if(d > radius)
			return 0d;
		else
			return ( 1 / (Math.pow(d, q)) );
	}

}
