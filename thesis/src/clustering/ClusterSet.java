package clustering;

import java.util.Iterator;
import java.util.Set;

public class ClusterSet implements Iterable<Cluster> {
	private Set<Cluster> clusters;
	
	public ClusterSet(Set<Cluster> clusters) {
		this.clusters = clusters;
	}

	void exportCsv(){
		//todo
	}

	@Override
	public Iterator<Cluster> iterator() {
		return this.clusters.iterator();
	}
}
