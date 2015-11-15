package data;

import io.DataTO;
import io.FeatureVectorTO;
import autocorrelation.AutocorrelationI;

import java.util.List;

public class DataFactory implements Factory {
	private static final String DATASET = "dataset";
	private static final String AUTO_DATASET = "auto";
	
	@Override
	public Object getInstance(String request, List<Object> params) {
		Object returnType = null;
		
		FeatureVectorTO fvTO = (FeatureVectorTO)params.get(0);
		DataTO dataTO = (DataTO) params.get(1);
		
		if(request.equalsIgnoreCase(DATASET)){
			returnType = new Dataset(fvTO, dataTO, true);
		} else if (request.equalsIgnoreCase(AUTO_DATASET)){
			AutocorrelationI ac = (AutocorrelationI) params.get(2);
			short radius = (short) Integer.parseInt(params.get(3).toString());
			returnType = new AutoDataset(fvTO, dataTO, ac, radius);
		} else {
			throw new InvalidDatasetChoice("arg 2: " + request + " is not a valid choice for dataset setting");
		}
	
		return returnType;
	}
	
}
