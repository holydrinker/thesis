package autocorrelation;

import data.Datapoint;

class Record{
	short x;
	short y;
	Datapoint dp;
	
	public Record(short x, short y, Datapoint dp) {
		this.x = x;
		this.y = y;
		this.dp = dp;
	}
}
