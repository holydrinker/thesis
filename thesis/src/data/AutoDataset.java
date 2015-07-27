package data;

import autocorrelation.AutocorrelationI;
import io.DataTO;
import io.FeatureVectorTO;

public class AutoDataset extends Data {
	AutocorrelationI a;
	
	public AutoDataset(FeatureVectorTO fvTO, DataTO stream, AutocorrelationI a) {
		super(fvTO, stream);
		this.a = a;
		
	}

}
