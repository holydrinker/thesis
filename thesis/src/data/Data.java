package data;

import io.DataTO;
import io.DatapointTO;
import io.FeatureVectorTO;

public abstract class Data implements Iterable<Datapoint> {
	protected FeatureVector fv;
	protected Datapoint[][] datapoints;
	
	public Data(FeatureVectorTO fvTO, DataTO stream) {
		fv = new FeatureVector(fvTO);
		
		for(Object obj : stream.get()){
			DatapointTO dpTO = (DatapointTO) obj;
			//Il feature vector viene letto correttamente. Costruire la matrice dei datapoint
			
		}
		
	}

}
