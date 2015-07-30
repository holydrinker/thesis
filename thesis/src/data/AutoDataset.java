package data;

import java.util.LinkedList;

import autocorrelation.AutocorrelationI;
import io.DataTO;
import io.DatapointTO;
import io.FeatureVectorTO;

public class AutoDataset extends Data {
	//Questa classe va modifica nella progettazione. Non l'ho ancora fatto perchè sono scettico su come ho risolto i problemi.
	
	public AutoDataset(FeatureVectorTO fvTO, DataTO stream, AutocorrelationI a, short radius) {
		super(fvTO, generateNewStream(fvTO, stream, a, radius));		
		super.reformatDataset();
	}

	private static DataTO generateNewStream(FeatureVectorTO fvTO, DataTO stream, AutocorrelationI a, short radius){
		Dataset dataset = new Dataset(fvTO, stream);
		short MAX_X = (short) (dataset.datapoints[0].length);
		short MAX_Y = (short) (dataset.datapoints.length);
		
		//Per ogni datapoint ne genero uno nuovo con l'autocorrelazione in capsulato in un transfer object e accumulato in questa lista
		LinkedList<Object> dataParams = new LinkedList<Object>();
		
		for(int x = 0; x < MAX_X; x++){
			for(int y = 0; y < MAX_Y; y++){
				LinkedList<Object> dpValues = new LinkedList<Object>();
				dpValues.add(new Double(x));
				dpValues.add(new Double(y));
				
				Datapoint newDp = a.compute(dataset, (short)x, (short)y, (short)radius); //come far arrivare qui il raggio?
				for(Object value : newDp)
					dpValues.add(value);
				dataParams.add(new DatapointTO(dpValues));
			}
		}
		
		//restituisco uno nuovo stream di datapoint
		return new DataTO(dataParams);
	}
}
