package data;

import io.DataTO;
import io.FeatureVectorTO;
import autocorrelation.AutocorrelationI;

import java.util.List;


public class DatasetFactory implements Factory {
	private static final String DATASET = "dataset";
	private static final String AUTO_DATASET = "auto";
	
	@Override
	public Object getInstance(String request, List<Object> params) {
		FeatureVectorTO fvTO = (FeatureVectorTO)params.get(0);
		DataTO dataTO = (DataTO) params.get(1);
		AutocorrelationI ac = null; //(AutocorrelationI) params.get(2);
		
		Object returnType = null;
		
		switch(request){
			case DATASET:
				returnType = new Dataset(fvTO, dataTO);
				break;
				
			case AUTO_DATASET:
				returnType = new AutoDataset(fvTO, dataTO, ac);
				break;
		}
		
		return returnType;
	}

}
