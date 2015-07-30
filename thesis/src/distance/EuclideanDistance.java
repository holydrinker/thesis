package distance;

public class EuclideanDistance extends BidimensionalDistance {

	public EuclideanDistance(short x1, short y1, short x2, short y2) {
		super(x1, y1, x2, y2);
	}

	@Override
	public Double compute() {
		double x = Math.pow(x1-x2, 2);
		double y = Math.pow(y1-y2, 2);
		return Math.sqrt(x + y);
	}

}
