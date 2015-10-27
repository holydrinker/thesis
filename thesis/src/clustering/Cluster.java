package clustering;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import data.Datapoint;
import io.DatapointTO;

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
	
	/* Used in order to test cluster.iterator()
	public static void main(String[] args) {
		//Make a datapoint
		LinkedList<Object> list = new LinkedList<Object>();
		list.add(0d);
		list.add(0d);
		list.add(3.1);
		list.add(5.0);
		list.add(10.0);
		DatapointTO to = new DatapointTO(list);
		Datapoint dp = new Datapoint((short)100, to);
		
		//Make a medoid
		LinkedList<Object> medoidList = new LinkedList<Object>();
		medoidList.add(1d);
		medoidList.add(1d);
		medoidList.add(1d);
		medoidList.add(1d);
		medoidList.add(1d);
		DatapointTO medoidTO  = new DatapointTO(medoidList);
		Datapoint medoid = new Datapoint((short)1, medoidTO);
		
		//Make a cluster
		LinkedList<Datapoint> clusterList = new LinkedList<Datapoint>();
		clusterList.add(dp);
		clusterList.add(dp);
		
		Cluster cluster = new Cluster((short)0, medoid, clusterList);
		for(Datapoint point : cluster){
			System.out.println(point.toString());
		}
		
	}
	*/

}
