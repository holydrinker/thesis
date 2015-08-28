package clustering;

import java.util.Iterator;
import java.util.List;

import data.Datapoint;

public class Cluster implements Iterable<Datapoint>{
	private short id;
	private Datapoint medoid;
	private List<Datapoint> datapoints;
	
	public Cluster(short id, Datapoint medoid, List<Datapoint> datapoints) {
		this.id = id;
		this.medoid = medoid;
		this.datapoints = datapoints;
	}

	public Datapoint getMedoid(){
		return this.medoid;
	}
	
	public short getID(){
		return this.id;
	}
	
	@Override
	public Iterator<Datapoint> iterator() {
		return this.datapoints.iterator();
	}
	
	@Override
	public boolean equals(Object obj) {
		Cluster otherCluster = (Cluster) obj;
		return this.id == otherCluster.id;
	}

}
