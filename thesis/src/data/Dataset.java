package data;

import io.DataTO;
import io.FeatureVectorTO;

import java.util.Iterator;

public class Dataset extends Data {

	public Dataset(FeatureVectorTO fvTO, DataTO stream) {
		super(fvTO, stream);
	}

	@Override
	public Iterator<Datapoint> iterator() {
		return super.iterator();
	}

}
