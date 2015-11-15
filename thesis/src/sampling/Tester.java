package sampling;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import clustering.Cluster;
import clustering.ClusterSet;
import data.Datapoint;

public class Tester {

	public static void main(String[] args) {
		Cluster cluster = generateCluster();
		Set<Cluster> set = new HashSet<Cluster>();
		set.add(cluster);
		ClusterSet clusterSet = new ClusterSet(set);
		
		Sampling sampler = new Sampling(0.5, clusterSet);
		ClusterSet sampledClusterSet = sampler.compute();
		System.out.println(sampledClusterSet.iterator().next().size());
	}
	
	private static Cluster generateCluster(){
		Datapoint medoid = new Datapoint((short)0, (short)0);
		
		List<Datapoint> list = new LinkedList<Datapoint>();
		list.add(new Datapoint((short)1, (short)0));
		list.add(new Datapoint((short)2, (short)0));
		list.add(new Datapoint((short)3, (short)0));
		list.add(new Datapoint((short)4, (short)0));
		list.add(new Datapoint((short)5, (short)0));
		list.add(new Datapoint((short)6, (short)0));
		list.add(new Datapoint((short)7, (short)0));
		list.add(new Datapoint((short)8, (short)0));
		list.add(new Datapoint((short)9, (short)0));
		
		list.add(new Datapoint((short)0, (short)1));
		list.add(new Datapoint((short)1, (short)1));
		list.add(new Datapoint((short)2, (short)1));
		list.add(new Datapoint((short)3, (short)1));
		list.add(new Datapoint((short)4, (short)1));
		list.add(new Datapoint((short)5, (short)1));
		list.add(new Datapoint((short)6, (short)1));
		list.add(new Datapoint((short)7, (short)1));
		list.add(new Datapoint((short)8, (short)1));
		list.add(new Datapoint((short)9, (short)1));
		
		list.add(new Datapoint((short)0, (short)2));
		list.add(new Datapoint((short)1, (short)2));
		list.add(new Datapoint((short)2, (short)2));
		list.add(new Datapoint((short)3, (short)2));
		list.add(new Datapoint((short)4, (short)2));
		list.add(new Datapoint((short)5, (short)2));
		list.add(new Datapoint((short)6, (short)2));
		list.add(new Datapoint((short)7, (short)2));
		list.add(new Datapoint((short)8, (short)2));
		list.add(new Datapoint((short)9, (short)2));
				
		Cluster cluster = new Cluster((short)0, medoid, list);
		return cluster;
	}

}
