package data;

import io.DatapointTO;

import java.util.Iterator;
import java.util.LinkedList;

public class Datapoint implements Iterable<Double> {
	private int pointID;
	private LinkedList<Double> values = new LinkedList<Double>();
	
	Datapoint(int pointID, DatapointTO to){
		this.pointID = pointID;
		
		for(Object obj : to.get())
			values.add((Double)obj);
	}
	
	public Double getValue(int idx){
		return values.get(idx);
	}

	@Override
	public Iterator<Double> iterator() {
		return values.iterator();
	}
}
