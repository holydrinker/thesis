package clustering;

import java.util.Iterator;
import java.util.LinkedList;

import data.Data;
import data.Datapoint;

public class Cluster implements Iterable<Datapoint>{
	private short id;
	private static short idGen = 0;
	private Datapoint medoid;
	private LinkedList<Datapoint> datapoints = new LinkedList<Datapoint>(); //Assicurarsi che la linkedlist sia la scelta più giusta
	
	public Cluster(Datapoint medoid, Data data) {
		this.id = idGen++;
		//Popolare il cluster. In che modo?
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
