package sampling;

import clustering.ClusterSet;
import clustering.Cluster;

import java.util.HashSet;

public class Sampling {
	private ClusterSet clusterSet;
	private SingleClusterSampling scs;
	
	public Sampling(double perc, ClusterSet clusterSet) {
		this.clusterSet = clusterSet;
		scs = new SingleClusterQuadSampling(perc);
	}
	
	public ClusterSet compute(){
		HashSet<Cluster> newClusterList = new HashSet<Cluster>();
		for(Cluster cluster : clusterSet){
			Cluster newCluster = scs.compute(cluster);
			newClusterList.add(newCluster);
		}
		
		return new ClusterSet(newClusterList);
	}
}