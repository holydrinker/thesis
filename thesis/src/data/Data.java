package data;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;

import org.omg.Messaging.SyncScopeHelper;

import io.DataTO;
import io.DatapointTO;
import io.FeatureVectorTO;

public abstract class Data implements Iterable<Datapoint> {
	private FeatureVector fv;
	public Datapoint[][] datapoints;
	
	public Data(FeatureVectorTO fvTO, DataTO stream) {
		fv = new FeatureVector(fvTO);
		
		//BUILD MATRIX
		int width = -1;
		int height = -1;
		
		for(Object obj : stream.get()){
			DatapointTO dpTO = (DatapointTO) obj;
			LinkedList<Object> params = dpTO.get();
			
			int x = ((Double)params.get(0)).intValue(); //is it a good idea?
			int y = ((Double)params.get(1)).intValue();
			
			if(x > width)
				width = x;
			if(y > height)
				height = y;			
		}
		datapoints = new Datapoint[width+1][height+1];
		
		//POPULATE MATRIX
		int idGenerator = 0;
		
		for(Object obj : stream.get()){
			DatapointTO dpTO = (DatapointTO) obj;
			LinkedList<Object> params = dpTO.get();
			
			int x = ((Double)params.get(0)).intValue();
			int y = ((Double)params.get(1)).intValue();
			
			Datapoint dp = new Datapoint(idGenerator++, dpTO);
			dp.updateMinMax(fv); //update min and max for the feature
			datapoints[x][y] = dp;
		}
		
		
		
	}
	
	public FeatureVector getFeatureVector(){
		return this.fv;
	}
	
	/**
	 * Una volta popolato il dataset, itera e per ogni valore di ogni datapoint fa il seguente controllo:
	 * se il valore � associato ad una feature continua lo scala, se il valore � associato ad una feature discreta,
	 * verifica che il valore assegnato sia un valore consentito dalla feature stessa
	 */
	protected void reformatDataset(){
		
		for(Datapoint dp : this){
			int i = 0;
			
			for(Object obj : fv){
				Feature f = (Feature)obj;
				Double value = dp.getValue(i);
				
				if(f instanceof DiscreteFeature){
					if(!checkDiscreteValue((DiscreteFeature)f, value))
						throw new InvalidDiscreteFeature(value + " is not a  valid value for feature: " + f.name);
				} else if (f instanceof ContinueFeature) {
					double newValue = ((ContinueFeature)f).getScaled(value);
					dp.setValue(i, newValue);
				}
				
				i++;
			}
		}
	}
	
	/**
	 * Controlla se value rientra nei valori ammessi dalla feature f
	 * @param f
	 * @param value
	 * @return
	 */
	private boolean checkDiscreteValue(DiscreteFeature f, Double value){
		HashSet<String> set = f.getSet();
		return set.contains(((Integer)(value.intValue())).toString());
	}
	
	@Override
	public Iterator<Datapoint> iterator() {
		return new Iterator<Datapoint>(){
			int width = datapoints[0].length;
			int height = datapoints.length;
			int x = 0;
			int y = 0;
			
			@Override
			public boolean hasNext() {
				if(x == width){
					x = 0;
					y++;
				}
				
				if(y == height)
					return false;
				else
					return true;
			}

			@Override
			public Datapoint next() {
				return datapoints[y][x++];
			}
		};
	}

}
