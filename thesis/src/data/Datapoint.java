package data;

import io.DatapointTO;

import java.util.Iterator;
import java.util.LinkedList;

public class Datapoint implements Iterable<Double> {
	private int pointID;
	private LinkedList<Double> values = new LinkedList<Double>();
	
	Datapoint(int pointID, DatapointTO to){
		this.pointID = pointID;
		LinkedList<Object> params = to.get();
		
		for(int i = 2; i < params.size(); i++)
			values.add((Double)params.get(i));
	}
	
	public int getID(){
		return this.pointID;
	}
	
	public Double getValue(int idx){
		return values.get(idx);
	}
	
	public void setValue(int idx, double newValue){
		values.set(idx, newValue);
	}
	
	void updateMinMax(FeatureVector fv){
		for(Object f : fv){
			int i = 2;
			
			if((Feature)f instanceof ContinueFeature){
				ContinueFeature cf = (ContinueFeature) f;
				cf.setMax(this.getValue(i));
				cf.setMin(this.getValue(i));
			}
			i++;
		}
	}

	@Override
	public Iterator<Double> iterator() {
		return values.iterator();
	}
}
