package data;

import io.DatapointTO;

import java.util.Iterator;
import java.util.LinkedList;

public class Datapoint implements Iterable<Double> {
	private short pointID;
	public short x;
	public short y;
	private LinkedList<Double> values = new LinkedList<Double>(); 
	
	Datapoint(short pointID, DatapointTO to){
		this.pointID = pointID;
		LinkedList<Object> params = to.get();
		
		final int X_POS = 0;
		final int Y_POS = 1;
		final int FIRST_VALUE_POS = 2;
		
		this.x = (short) ((Double)params.get(X_POS)).intValue();
		this.y = (short) ((Double)params.get(Y_POS)).intValue();
		for(int i = FIRST_VALUE_POS; i < params.size(); i++)
			values.add((Double)params.get(i));
	}
	
	public short getID(){
		return this.pointID;
	}
	
	public Double getValue(int idx){
		return values.get(idx);
	}
	
	public void setValue(int idx, double newValue){
		values.set(idx, newValue);
	}

	@Override
	public boolean equals(Object obj) {
		Datapoint dp = (Datapoint) obj;
		return this.pointID == dp.pointID;
	}
	
	@Override
	public Iterator<Double> iterator() {
		return values.iterator();
	}
}
