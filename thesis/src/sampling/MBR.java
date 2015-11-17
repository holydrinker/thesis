package sampling;

import clustering.Cluster;
import data.Datapoint;

public class MBR extends Quadrant {
	private short minX;
	private short maxX;
	private short minY;
	private short maxY;
	private short cardinality;

	public MBR(short minX, short minY, short maxX, short maxY, short cardinality){
		this.minX = minX;
		this.maxX = maxX;
		this.minY = minY;
		this.maxY = maxY;
		this.cardinality = cardinality;
	}

	short getMinX(){ 
		return this.minX;
	}
	
	short getMaxX(){
		return this.maxX;
	}
	
	short getMinY(){
		return this.minY;
	}
	
	short getMaxY(){
		return this.maxY;
	}
	
	short getCardinality(){
		return this.cardinality;
	}
	
	double getCentreX(){
		return ((double)(maxX - minX)) / 2;
	}
	
	double getCentreY(){
		return ((double)(maxY - minY)) / 2;
	}

	Datapoint determineCentre(Cluster cluster, int beginExampleIndex, int endExampleIndex){
		//the sensor point closest to the geometrical centre of the mbr
		Datapoint centre = cluster.getDatapoint(beginExampleIndex);
		double cX = (minX + maxX) / 2;
		double cY= (minY + maxY) / 2;
		
		double minDist = Math.pow(cX - centre.getSpatialFeature(0), 2) + Math.pow(cY - centre.getSpatialFeature(1), 2);
	
		for(int i = beginExampleIndex+1; i <= endExampleIndex; i++){
			Datapoint dp = cluster.getDatapoint(i);
			double d = Math.pow(cX - dp.getSpatialFeature(0), 2) + Math.pow(cY - dp.getSpatialFeature(1), 2);
			if(d < minDist){
				minDist = d;
				centre = dp;
			}
		}
		return centre;
	}
	
}
