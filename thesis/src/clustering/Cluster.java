package clustering;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import data.Datapoint;
import evaluation.Metrics;
import io.DatapointTO;

public class Cluster implements Iterable<Datapoint>{
	private short id;
	private Datapoint medoid;
	private List<Datapoint> datapoints;
	
	public Cluster(short clusterID, Datapoint medoid, List<Datapoint> datapoints) {
		this.id = clusterID;
		this.medoid = medoid;
		this.datapoints = datapoints;
		
		Metrics.groundTruth.setClusterID(medoid.getID(), clusterID);
		for(Datapoint dp : datapoints){
			Metrics.groundTruth.setClusterID(dp.getID(), clusterID);
		}
	}

	public Datapoint getMedoid(){
		return this.medoid;
	}
	
	public short getID(){
		return this.id;
	}
	
	public short size(){
		return (short) (this.datapoints.size() + 1);
	}
	
	@Override
	public Iterator<Datapoint> iterator() {
		return new Iterator<Datapoint>(){
			int i = -1;
			Iterator<Datapoint> dpIterator = datapoints.iterator();
			
			@Override
			public boolean hasNext() {
				if(i == -1){
					return true;
				} else {
					return dpIterator.hasNext();
				}
			}

			@Override
			public Datapoint next() {
				if(i == -1){
					i++;
					return medoid;
				} else {
					return dpIterator.next();
				}
			}
		};
	}
	
	@Override
	public boolean equals(Object obj) {
		Cluster otherCluster = (Cluster) obj;
		return this.id == otherCluster.id;
	}
	
	@Override
	public String toString() {
		String result = "";
		for(Datapoint datapoint : this){
			result += datapoint.toString() + "\n";
		}
		return result;
	}

}
