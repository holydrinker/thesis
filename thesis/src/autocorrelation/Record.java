package autocorrelation;

import data.Datapoint;

public class Record {
	short x;
	short y;
	Datapoint dp;
	
	public Record(short x, short y, Datapoint dp) {
		this.x = x;
		this.y = y;
		this.dp = dp;
	}
}
