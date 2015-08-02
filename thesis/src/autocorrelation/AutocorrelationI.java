package autocorrelation;

import data.Data;
import data.Datapoint;

public interface AutocorrelationI {

	public Datapoint compute(Data data, short x, short y, short radius);
	
}
