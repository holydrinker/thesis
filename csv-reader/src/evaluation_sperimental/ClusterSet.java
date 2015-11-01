package evaluation_sperimental;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class ClusterSet implements Iterable<Cluster>{
	Set<Cluster> set = new HashSet<Cluster>();
	
	public void addCluster(Cluster cluster){
		this.set.add(cluster);
	}

	@Override
	public Iterator<Cluster> iterator() {
		return this.set.iterator();
	}
}
