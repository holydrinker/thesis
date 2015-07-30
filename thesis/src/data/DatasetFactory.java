package data;

import io.DataTO;
import io.FeatureVectorTO;
import autocorrelation.AcFactory;
import autocorrelation.AutocorrelationI;

import java.util.List;


public class DatasetFactory implements Factory {
	private static final String DATASET = "dataset";
	private static final String AUTO_DATASET = "auto";
	
	@Override
	public Object getInstance(String request, List<Object> params) {
		FeatureVectorTO fvTO = (FeatureVectorTO)params.get(0);
		DataTO dataTO = (DataTO) params.get(1);
		AutocorrelationI ac = (AutocorrelationI) params.get(2);
		short radius = (short) params.get(3);
	
		Object returnType = null;
		
		switch(request){
			case DATASET: returnType = new Dataset(fvTO, dataTO);
				break;
				
			case AUTO_DATASET: returnType = new AutoDataset(fvTO, dataTO, ac, radius);
				break; 
		}
		
		if(returnType == null)
			throw new InvalidDatasetChoice("arg 2: " + request + " is not a valid choice for dataset setting");
		return returnType;
	}
	
}
