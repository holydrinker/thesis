package autocorrelation;

import java.util.HashSet;

import data.ContinueFeature;
import data.Data;
import data.Datapoint;
import data.Feature;
import distance.EuclideanDistance;
import distance.WeightI;

public class GetisOrd implements AutocorrelationI {
	private WeightI weight;
	
	public GetisOrd(WeightI weight) {
		this.weight = weight;
	}
	
	@Override
	public Datapoint compute(Data data, short x, short y, short radius) {
		Datapoint dp = data.getDatapoint(x, y);
		HashSet<Datapoint> neighborhood = computeNeighborhood(data, x, y, radius);
		
		int valueIdx = 0;
		int featureIdx = 0;
		
		for(Object obj : data.getFeatureVector()){
			Feature f = (Feature) obj;
			if((Feature)f instanceof ContinueFeature){
				double newFeatureValue = new GetisOrdSingleFeature_FUORI(featureIdx, neighborhood, weight).compute(data, x, y);
				dp.setValue(valueIdx, newFeatureValue);
			}
			valueIdx++;
			featureIdx++;
		}
	
		return dp;
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
		System.out.print("Neighborhood("+x+","+y+")");
		for(short loopX = startX; loopX <= endX; loopX++){
			for(short loopY = startY; loopY <= endY; loopY++){
				if(!(loopY == y && loopX == x)){
					
					Datapoint neighbour = data.getDatapoint(loopX, loopY);
					if(!(neighbour == null)){
						//double distance = new EuclideanDistance(x, y, loopX, loopY).compute();
						//if(distance <= radius){
							System.out.print(",(" + loopX + "," + loopY + ")");
							neighborhood.add(data.getDatapoint(loopX, loopY));
						//}
					}
				}
			}
		}
		System.out.println("");
		return neighborhood;
	}
}
