package data;

public class Coord {
	public short x;
	public short y;
	
	public Coord(short x, short y) {
		this.x = (short) (x+1); //my matrix starts from 0, pixels start from 1
		this.y = (short) (y+1);
	}
}
