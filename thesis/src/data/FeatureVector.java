package data;

import io.FeatureVectorTO;

import java.util.Iterator;
import java.util.LinkedList;

public class FeatureVector implements Iterable<Feature>{
	private LinkedList<Feature> features = new LinkedList<Feature>();
	
	public FeatureVector(FeatureVectorTO to) {
		LinkedList<Object> params = to.get();
		for(int i = 0; i < params.size()-1; i++){
			features.add((Feature)params.get(i));
		}
	}
	
	public Feature getFeature(int idx){
		return this.features.get(idx);
	}
	
	void updateMinMax(Datapoint dp){
		for(Object f : this){
			int valueIdx = 0;
			
			if((Feature)f instanceof ContinueFeature){
				ContinueFeature cf = (ContinueFeature) f;
				cf.setMax(dp.getValue(valueIdx));
				cf.setMin(dp.getValue(valueIdx));
			}
			valueIdx++;
		}
	}
	
	@Override
	public Iterator<Feature> iterator() {
		return features.iterator();
	}
	
	@Override
	public String toString() {
		String result = "";
		for(Feature feature : this){
			result += feature.name + " ";
		}
		return result;
	}
}
