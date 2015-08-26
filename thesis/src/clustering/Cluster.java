package clustering;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import data.Data;
import data.Datapoint;

public class Cluster implements Iterable<Datapoint>{
	private short id;
	private Datapoint medoid;
	private LinkedList<Datapoint> datapoints = new LinkedList<Datapoint>(); //Assicurarsi che la linkedlist sia la scelta più giusta
	
	public Cluster(short s, Datapoint medoid2, List<Datapoint> list) {
		// TODO Auto-generated constructor stub
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

}
