package autocorrelation;

import data.ContinueFeature;
import data.Datapoint;
import data.Dataset;
import data.Feature;
import distance.WeightI;

public class GetisOrd implements AutocorrelationI {
	private WeightI weight;
	
	public GetisOrd(WeightI weight) {
		this.weight = weight;
	}
	
	@Override
	public Datapoint compute(Dataset data, short x, short y, short radius) {
		Datapoint dp = data.datapoints[y][x];
		Neighborhood neighborhood = new Neighborhood(data, x, y, radius);
		int i = 0;
		
		for(Object obj : data.getFeatureVector()){
			Feature f = (Feature) obj;
			if(f instanceof ContinueFeature){
				double newFeatureValue = new GetisOrdSingleFeature(f, neighborhood, weight).compute(data, x, y);
				dp.setValue(i, newFeatureValue);
			}
			i++;
		}
	
		return dp;
	}
	
}
