package distance;

public abstract class BidimensionalDistance implements DistanceI {
	protected short x1;
	protected short y1;
	protected short x2;
	protected short y2;
	
	public BidimensionalDistance(short x1, short y1, short x2, short y2) {
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
	}
	
	@Override
	public abstract Double compute();

}