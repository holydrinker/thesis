package data;

import io.DataTO;
import io.FeatureVectorTO;

import java.util.Iterator;

public class Dataset extends Data {

	public Dataset(FeatureVectorTO fvTO, DataTO stream, boolean scaling) {
		super(fvTO, stream);
		if(scaling){
			super.scaling();
		}
	}

	@Override
	public Iterator<Datapoint> iterator() {
		return super.iterator();
	}
	
	@Override
	public String toString() {
		String result = this.fv.toString() + "\n";
		
		int height = this.getHeight();
		int width = this.getWidth();
		
		for(int i = 0; i < height; i++){
			for(int j = 0; j < width; j++){
				Datapoint datapoint = this.datapoints[i][j];
				if(datapoint != null)
					result += datapoint.toString() + "\n";
			}
		}
		return result;
	}

}
