package autocorrelation;

import java.util.HashSet;

import data.ContinueFeature;
import data.Data;
import data.Datapoint;
import data.Feature;
import distance.WeightI;

public class GetisOrd implements AutocorrelationI {
	private WeightI weight;
	private double Z;
	
	public GetisOrd(WeightI weight) {
		this.weight = weight;
	}
	
	@Override
	public Datapoint compute(Data data, short x, short y, short radius) {
		Datapoint oldPoint = data.getDatapoint(x, y);
		Datapoint newPoint = new Datapoint(x, y);
		
		HashSet<Datapoint> neighborhood = computeNeighborhood(data, x, y, radius);
		
		int featureIdx = 0;
		for(Object obj : data.getFeatureVector()){
			Feature f = (Feature) obj;
			double newValue;
			
			if((Feature)f instanceof ContinueFeature){
				GetisOrdSingleFeature gosf = new GetisOrdSingleFeature(featureIdx, neighborhood, weight);
				newValue = gosf.compute(data, x, y);
			} else {
				newValue = oldPoint.getValue(featureIdx);
			}
			
			newPoint.addValue(newValue);
			featureIdx++;
		}
		return newPoint;
	}
	
	/**
	 * Compute the neighbodhood of the datapoint[x][y] into a range defined by radius
	 * @param data
	 * @param x
	 * @param y
	 * @param radius
	 * @return
	 */
	private HashSet<Datapoint> computeNeighborhood(Data data, short x, short y, short radius){
		HashSet<Datapoint> neighborhood = new HashSet<Datapoint>();
		
		//Set neighborhood's bounds 
		short startX = (short) (x - radius);
		short startY = (short) (y - radius);
		short endX = (short) (x + radius);
		short endY = (short) (y + radius);
				
		//Prevent OutOfBoundsException
		final short MAX_X = (short) (data.getHeight() - 1);
		final short MAX_Y = (short) (data.getWidth() - 1);
				
		if(startX < 0)
			startX = 0;
		if(startY < 0)
			startY = 0;
		if(endX > MAX_X)
			endX = MAX_X;
		if(endY > MAX_Y)
			endY = MAX_Y;
				
		//Generate
		//System.out.print("Neighborhood("+x+","+y+") = {");
		for(short loopX = startX; loopX <= endX; loopX++){
			for(short loopY = startY; loopY <= endY; loopY++){
				if(!(loopY == y && loopX == x)){
					
					Datapoint neighbour = data.getDatapoint(loopX, loopY);
					if(!(neighbour == null)){
							//System.out.print(" (" + loopX + "," + loopY + ") ");
							neighborhood.add(data.getDatapoint(loopX, loopY));
					}
				}
			}
		}
		//System.out.println("}");
		return neighborhood;
	}
}
