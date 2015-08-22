package data;

import io.FeatureVectorTO;

import java.util.Iterator;
import java.util.LinkedList;

public class FeatureVector implements Iterable<Feature>{
	private LinkedList<Feature> features = new LinkedList<Feature>();
	
	public FeatureVector(FeatureVectorTO to) {
		for(Object obj : to.get())
			features.add((Feature)obj);
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
	
}
