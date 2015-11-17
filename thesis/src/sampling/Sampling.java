package sampling;

import clustering.ClusterSet;
import clustering.Cluster;

import java.util.HashSet;
import java.util.Set;

public class Sampling {
	private ClusterSet clusterSet;
	private SingleClusterSampling scs;
	
	public Sampling(double perc, ClusterSet clusterSet) {
		this.clusterSet = clusterSet;
		scs = new SingleClusterQuadSampling(perc);
	}
	
	public ClusterSet compute(){
		Set<Cluster> newClusterList = new HashSet<Cluster>();
		for(Cluster cluster : clusterSet){
			System.out.println("size pre sampling: " + cluster.size());
			Cluster newCluster = scs.compute(cluster);
			newClusterList.add(newCluster);
			System.out.println("size post sampling: " + newCluster.size());
		}
		
		return new ClusterSet(newClusterList);
	}
}