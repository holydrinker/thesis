package data;

import io.DatapointTO;

import java.util.Iterator;
import java.util.LinkedList;

public class Datapoint implements Iterable<Double> {
	private short pointID;
	private LinkedList<Double> values = new LinkedList<Double>(); 
	
	Datapoint(short pointID, DatapointTO to){
		this.pointID = pointID;
		LinkedList<Object> params = to.get();
		
		//pos 0 = x; pos 1 = y
		final int FIRST_VALUE = 2;
		for(int i = FIRST_VALUE; i < params.size(); i++)
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
