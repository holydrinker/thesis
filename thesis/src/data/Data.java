package data;

import java.util.Iterator;
import java.util.LinkedList;

import io.DataTO;
import io.DatapointTO;
import io.FeatureVectorTO;

public abstract class Data implements Iterable<Datapoint> {
	protected FeatureVector fv;
	public Datapoint[][] datapoints;
	
	public Data(FeatureVectorTO fvTO, DataTO stream) {
		fv = new FeatureVector(fvTO);
		
		//BUILD MATRIX
		int width = -1;
		int hieght = -1;
		
		for(Object obj : stream.get()){
			DatapointTO dpTO = (DatapointTO) obj;
			LinkedList<Object> params = dpTO.get();
			
			int x = ((Double)params.get(0)).intValue(); //forse posso farlo meglio
			int y = ((Double)params.get(1)).intValue();
			
			if(x > width)
				width = x;
			if(y > hieght)
				hieght = y;
			datapoints = new Datapoint[hieght][width];			
		}
		
		//POPULATE MATRIX
		int idGenerator = 0;
		
		for(Object obj : stream.get()){
			DatapointTO dpTO = (DatapointTO) obj;
			LinkedList<Object> params = dpTO.get();
			
			int x = ((Double)params.get(0)).intValue() - 1; //idem qua. Attenzione però che il pixel con coordinate n,m verrà posizionao nella cella n-1, m-1 per evitare Out of bounds
			int y = ((Double)params.get(1)).intValue() - 1;
			datapoints[y][x] = new Datapoint(idGenerator++, dpTO);
		}
		
	}
	
	public FeatureVector getFeatureVector(){
		return this.fv;
	}
	
	@Override
	public Iterator<Datapoint> iterator() {
		return new Iterator<Datapoint>(){
			int height = datapoints.length;
			int width = datapoints[0].length;
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
