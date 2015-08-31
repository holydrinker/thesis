package autocorrelation;

import java.util.HashSet;

import data.ContinueFeature;
import data.Data;
import data.Datapoint;
import data.Feature;
import distance.WeightI;

public class GetisOrd implements AutocorrelationI {
	private WeightI weight;
	
	public GetisOrd(WeightI weight) {
		this.weight = weight;
	}
	
	@Override
	public Datapoint compute(Data data, short x, short y, short radius) {
		//Get datapoint
		Datapoint dp = data.getDatapoint(x, y);
		
		//Compute datapoint's neighborhood
		HashSet<Record> neighborhood = computeNeighborhood(data, x, y, radius);
		
		/*Neighborhood testing...
		System.out.println("|------------NEIGHBORHOOD--------------|");
		for(Record r : neighborhood){
			for(Object value : r.dp)
				System.out.print(value + " ");
			System.out.println("");
		}
		System.out.println("|--------------------------------------|");
		*/
		
		//Compute a new autocorrelated valued for each continue feature
		int i = 0; //use this to iterate over datapoint's values, skipping discrete features
		for(Object obj : data.getFeatureVector()){
			Feature f = (Feature) obj;
			if((Feature)f instanceof ContinueFeature){
				double newFeatureValue = new GetisOrdSingleFeature(f, neighborhood, weight).compute(data, x, y);
				dp.setValue(i, newFeatureValue);
			}
			i++;
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
	private HashSet<Record> computeNeighborhood(Data data, short x, short y, short radius){
		HashSet<Record> neighborhood = new HashSet<Record>();
		
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
		for(short loopX = startX; loopX <= endX; loopX++){
			for(short loopY = startY; loopY <= endY; loopY++){
				if(!(loopY == y && loopX == x)){
					//check if the neighbor if a null cell
					if(!(data.getDatapoint(loopX, loopY) == null)){
						neighborhood.add(new Record(loopX, loopY, data.getDatapoint(loopX, loopY)));
					}
				}
			}
		}
		
		return neighborhood;
	}
}
