package autocorrelation;

import data.Datapoint;
import data.Dataset;

public interface AutocorrelationI {

	public Datapoint compute(Dataset data, short x, short y, short radius);
	
}
