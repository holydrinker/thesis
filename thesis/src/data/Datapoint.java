package data;

import io.DatapointTO;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class Datapoint implements Iterable<Double>, Comparable<Datapoint> {
	private short pointID;
	private short classID = -1;
	private short clusterID = -1;
	private List<SpatialFeature> spatialFeatures = new ArrayList<SpatialFeature>();
	private LinkedList<Double> values = new LinkedList<Double>(); 
	
	public Datapoint(short pointID, DatapointTO to){
		this.pointID = pointID;
		LinkedList<Object> params = to.get();

		final int X_POS = 0;
		final int Y_POS = 1;
		final int FIRST_VALUE_POS = 2;
		
		short xValue = (short) (((Double)params.get(X_POS)).intValue() - 1);
		short yValue = (short) (((Double)params.get(Y_POS)).intValue() - 1);
		SpatialFeature x = new SpatialFeature("x", xValue);
		SpatialFeature y = new SpatialFeature("y", yValue);
		spatialFeatures.add(0, x);
		spatialFeatures.add(1, y);

		for(int i = FIRST_VALUE_POS; i < params.size(); i++)
			values.add((Double)params.get(i));		
	}
	
	public Datapoint(short x, short y) {
		SpatialFeature xSF = new SpatialFeature("x", (short)x);
		SpatialFeature ySF = new SpatialFeature("y", (short)y);
		this.spatialFeatures.add(0, xSF);
		this.spatialFeatures.add(1, ySF);
	}
	
	public Datapoint(short pointID) {
		this.pointID = pointID;
	}
	
	public short getID(){
		return this.pointID;
	}
	
	public short getX(){
		return this.spatialFeatures.get(0).getValue();
	}
	
	public void setX(short x){
		this.spatialFeatures.get(0).setValue(x);
	}
	
	public short getY(){
		return this.spatialFeatures.get(1).getValue();
	}
	
	public void setY(short y){
		this.spatialFeatures.get(1).setValue(y);
	}
	
	public short getSpatialFeature(int index){
		return this.spatialFeatures.get(index).getValue();
	}
	
	public short getClusterID(){
		return this.clusterID;
	}
	
	public void setClusterID(short clusterID){
		this.clusterID = clusterID;
	}
	
	public short getClassID(){
		return this.classID;
	}
	
	public void setClass(short classID){
		this.classID = classID;
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
		short x = this.spatialFeatures.get(0).getValue();
		short y = this.spatialFeatures.get(1).getValue();
		String result = this.pointID + ";" + (x + 1) + ";" + (y + 1) + ";";
		for(Object value : this.values){ 
			result += value + ";";
		}
		return result;
	}

	@Override
	public int compareTo(Datapoint arg0) {
		if (this.pointID == arg0.pointID)
			return 0;
		else return 1;
	}
}
