package evaluation_sperimental;

import java.util.HashSet;
import java.util.Iterator;

public class Cluster implements Iterable<Instance> {
	private String name;
	private HashSet<Instance> set = new HashSet<Instance>();
	
	public Cluster(String name) {
		this.name = name;
	}
	
	public String getType(){
		return this.name;
	}
	
	public void addInstance(Instance i){
		set.add(i);
	}

	@Override
	public Iterator<Instance> iterator() {
		return this.set.iterator();
	}
	
	@Override
	public boolean equals(Object arg0) {
		Cluster cluster = (Cluster) arg0;
		return this.name.equalsIgnoreCase(cluster.name);
	}
	

}
