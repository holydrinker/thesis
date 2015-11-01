package evaluation_sperimental;

import java.util.HashMap;
import java.util.LinkedList;

public class Test {

	public static void main(String[] args) {
		
		//GROUND TRUTH
		Instance a1 = new Instance(1, "a");
		Instance a2 = new Instance(2, "a");
		Instance a3 = new Instance(3, "a");
		Instance a4 = new Instance(4, "a");
		Instance a5 = new Instance(5, "a");
		Instance a6 = new Instance(6, "a");
		Instance a7 = new Instance(7, "a");
		Instance a8 = new Instance(8, "a");
		Instance b1 = new Instance(9, "b");
		Instance b2 = new Instance(10, "b");
		Instance b3 = new Instance(11, "b");
		Instance b4 = new Instance(12, "b");
		Instance b5 = new Instance(13, "b");
		Instance c1 = new Instance(14, "c");
		Instance c2 = new Instance(15, "c");
		Instance c3 = new Instance(16, "c");
		Instance c4 = new Instance(17, "c");
		
		LinkedList<Instance> dataset = new LinkedList<Instance>();
		dataset.add(a1);
		dataset.add(a2);
		dataset.add(a3);
		dataset.add(a4);
		dataset.add(a5);
		dataset.add(a6);
		dataset.add(a7);
		dataset.add(a8);
		
		dataset.add(b1);
		dataset.add(b2);
		dataset.add(b3);
		dataset.add(b4);
		dataset.add(b5);
		
		dataset.add(c1);
		dataset.add(c2);
		dataset.add(c3);
		dataset.add(c4);
		
		
		//CLASSIFICATION
		
		Cluster cluster;
		ClusterSet clusterSet = new ClusterSet();
		
		cluster = new Cluster("a");
		cluster.addInstance(a1);
		cluster.addInstance(a2);
		cluster.addInstance(a3);
		cluster.addInstance(a4);
		cluster.addInstance(a5);
		cluster.addInstance(b1);
		clusterSet.addCluster(cluster);
		
		cluster = new Cluster("b");
		cluster.addInstance(b2);
		cluster.addInstance(b3);
		cluster.addInstance(b4);
		cluster.addInstance(b5);
		cluster.addInstance(a6);
		cluster.addInstance(c1);
		clusterSet.addCluster(cluster);
		
		cluster = new Cluster("c");
		cluster.addInstance(c2);
		cluster.addInstance(c3);
		cluster.addInstance(c4);
		cluster.addInstance(a7);
		cluster.addInstance(a8);
		clusterSet.addCluster(cluster);
		
		
		// TEST
		int TP = 0; //documenti simili nella stessa classe
		int TN = 0; //documenti diversi in classi diverse
		int FP = 0; //documenti diversi nella stessa classe
		int FN = 0; //documenti simili in classi diverse
		PrettyMap pmap = new PrettyMap();
		
		String typeP1 = null;
		String typeP2 = null;
		String typeC1 = null;
		String typeC2 = null;
		
		for(Instance p1 : dataset){
			typeP1 = p1.type;
			
			for(Instance p2: dataset){	
				typeP2 = p2.type;
				
				if(!p1.equals(p2)){
					for(Cluster c : clusterSet){
						String clusterType = c.getType();
						for(Instance p : c){
							if(p.equals(p1)){
								typeC1 = clusterType;
							} else if (p.equals(p2)) {
								typeC2 = clusterType;
							}
						}	
					}
					
					//Update
					if (typeP1.equals(typeP2)) {
						if (typeC1.equals(typeC2)) {
							pmap.addPair(new Pair(p1,p2), "TP");
						} else {
							pmap.addPair(new Pair(p1,p2), "FN");
						}
					} else if (!typeP1.equals(typeP2)) {
						if (!typeC1.equals(typeC2)) {
							pmap.addPair(new Pair(p1,p2), "TN");
						} else {
							pmap.addPair(new Pair(p1,p2), "FP");
						}
					}
				}
				
			}
		}
		
		HashMap<String, Integer> result = pmap.count();
		System.out.println(result.toString());
	}

}
