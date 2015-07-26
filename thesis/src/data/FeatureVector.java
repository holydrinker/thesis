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
	
	@Override
	public Iterator<Feature> iterator() {
		return features.iterator();
	}

}
