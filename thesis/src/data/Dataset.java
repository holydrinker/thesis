package data;

import io.DataTO;
import io.FeatureVectorTO;

import java.util.Iterator;

public class Dataset extends Data {

	public Dataset(FeatureVectorTO fvTO, DataTO stream) {
		super(fvTO, stream);
		//super.scaling();
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
		
		for(int i = 0; i < width; i++){
			for(int j = 0; j < height; j++){
				Datapoint datapoint = this.datapoints[i][j];
				result += datapoint.toString() + "\n";
			}
		}
		return result;
	}

}
