package data;

import io.DatapointTO;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class Datapoint implements Iterable<Double>, Comparable<Datapoint> {
	private short pointID;
	private List<SpatialFeature> spatialFeature = new ArrayList<SpatialFeature>();
	private short classID;
	private LinkedList<Double> values = new LinkedList<Double>(); 
	
	public Datapoint(short pointID, DatapointTO to){ 
		this.pointID = pointID;
		LinkedList<Object> params = to.get();

		final int X_POS = 0;
		final int Y_POS = 1;
		final int FIRST_VALUE_POS = 2;
		
		short x = (short) (((Double)params.get(X_POS)).intValue() - 1);
		short y = (short) (((Double)params.get(Y_POS)).intValue() - 1);
		spatialFeature.add(0, new SpatialFeature("x", x));
		spatialFeature.add(1, new SpatialFeature("y", y));
		
		for(int i = FIRST_VALUE_POS; i < params.size(); i++)
			values.add((Double)params.get(i));
	}
	
	public Datapoint(short x, short y) {
		this.spatialFeature.add(0, new SpatialFeature("x", x));
		this.spatialFeature.add(1, new SpatialFeature("y", y));
	}
	
	public Datapoint(short pointID) {
		this.pointID = pointID;
	}
	
	public short getX(){
		return this.spatialFeature.get(0).getValue();
	}
	
	public void setX(short x){
		if(spatialFeature.size() == 0){
			spatialFeature.add(0, new SpatialFeature("x", x));
		} else {
			spatialFeature.get(0).setValue(x);
		}
	}
	
	public short getY(){
		return this.spatialFeature.get(1).getValue();
	}
	
	public void setY(short y){
		if(spatialFeature.size() <= 1){
			spatialFeature.add(1, new SpatialFeature("y", y));
		} else {
			spatialFeature.get(1).setValue(y);
		}
	}
	
	public short getSpatialFeature(int index){
		return this.spatialFeature.get(index).getValue();
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
	
	public void addValue(double value){
		this.values.add(value);
	}

	public void setClassID(short classID){
		this.classID = classID;
	}
	
	public short getClassID(){
		return this.classID;
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
	
	@Override
	public String toString() {
		String result = this.pointID + ";" + (this.getX() + 1) + ";" + (this.getY() + 1) + ";";
		for(Object value : this.values){ 
			result += value + ";";
		}
		result = result.substring(0, result.length()-1); //togli l'ultima virgola
		return result;
	}

	@Override
	public int compareTo(Datapoint arg0) {
		if (this.pointID == arg0.pointID)
			return 0;
		else return 1;
	}
}
