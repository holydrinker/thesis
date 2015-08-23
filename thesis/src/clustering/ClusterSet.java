package clustering;

import java.util.HashSet;
import java.util.Iterator;

public class ClusterSet implements Iterable<Cluster> {
	private HashSet<Cluster> clusters = new HashSet<Cluster>();
	
	void exportCsv(){
		//todo
	}

	@Override
	public Iterator<Cluster> iterator() {
		return this.clusters.iterator();
	}
}
