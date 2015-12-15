package data;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import evaluation.ClusterAssignment;
import io.DataTO;
import io.DatapointTO;
import io.FeatureVectorTO;

public abstract class Data implements Iterable<Datapoint> {
	protected FeatureVector fv;
	protected Datapoint[][] datapoints;
	protected short size = 0;
	private ClusterAssignment assignm = new ClusterAssignment();
	
	public Data(FeatureVectorTO fvTO, DataTO stream) {
		fv = new FeatureVector(fvTO);
		
		//BUILD MATRIX
		int width = -1;
		int height = -1;
		
		for(Object obj : stream.get()){
			DatapointTO dpTO = (DatapointTO) obj; 
			LinkedList<Object> params = dpTO.get();
			
			//In pos 0 and in pos 1 DatapointTO contains the coordinates
			int x = ((Double)params.get(0)).intValue() - 1;
			int y = ((Double)params.get(1)).intValue() - 1;
			
			//update width e height
			if(y > width)
				width = y;
			if(x > height)
				height = x;			
		}
		datapoints = new Datapoint[height+1][width+1];
		
		//POPULATE MATRIX
		short id = 0;
		final int X = 0;
		final int Y = 1;
		
		for(Object obj : stream.get()){
			DatapointTO dpTO = (DatapointTO) obj;
			LinkedList<Object> params = dpTO.get();
			
			int x = ((Double)params.get(X)).intValue() - 1;
			int y = ((Double)params.get(Y)).intValue() - 1;
			short classID = (short) ((Double)params.removeLast()).intValue();
			
			Datapoint dp = new Datapoint(id, new DatapointTO(params));
			dp.setClassID(classID);
			assignm.addRecord(id, classID);
			id++;
			
			fv.updateMinMax(dp);
			datapoints[x][y] = dp;
			size++;
		}
	
	}
	
	public ClusterAssignment getClusterAssignment(){
		return this.assignm;
	}
	
	public FeatureVector getFeatureVector(){
		return this.fv;
	}
	
	public short getWidth(){
		return (short) this.datapoints[0].length;
	}
	
	public short getHeight(){
		return (short) this.datapoints.length;
	}
	
	public short size(){
		return this.size;
	}
	
	public Datapoint getDatapoint(short x, short y){
		return this.datapoints[x][y];
	}
	
	/**
	 * Una volta popolato il dataset, itera e per ogni valore di ogni datapoint fa il seguente controllo:
	 * se il valore è associato ad una feature continua lo scala, se il valore è associato ad una feature discreta,
	 * verifica che il valore assegnato sia un valore consentito dalla feature stessa
	 */
	protected void scaling(){
		final int FIRST_VALUE = 0;
		
		for(Datapoint dp : this){
			int i = FIRST_VALUE;
		
			for(Object obj : fv){
				Feature f = (Feature)obj;
				Double value = dp.getValue(i);
		
				if (f instanceof DiscreteFeature) {
					if (!checkDiscreteValue((DiscreteFeature) f, value))
						throw new InvalidDiscreteFeature(value + " is not a  valid value for feature: " + f.getName());
				} else if (f instanceof ContinueFeature) {
					double newValue = ((ContinueFeature) f).getScaled(value);
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
				
				//Skip null cell
				while(y < height && datapoints[y][x] == null){
					if(++x == width){
						x = 0;
						y++;
					}
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
