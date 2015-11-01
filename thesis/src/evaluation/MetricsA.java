package evaluation;

import java.util.HashMap;

import clustering.Cluster;
import clustering.ClusterSet;
import data.Data;
import data.Datapoint;
import data.GroundTruth;

public abstract class MetricsA {
	protected GroundTruth groundTruth;
	protected ClusterSet clusterSet;
	
	protected HashMap<Integer, PurityQuantifier> purityMap = new HashMap<Integer, PurityQuantifier>(); 
	
	protected int TP;
	protected int FP;
	protected int TN;
	protected int FN;
	
	protected final int GT_X = 0;
	protected final int GT_Y = 1;
	protected final int RES_X = 2;
	protected final int RES_Y = 3;
	
	public MetricsA(GroundTruth groundTruth, ClusterSet clusterSet) {
		this.groundTruth = groundTruth;
		this.clusterSet = clusterSet;
		System.out.print("set params..."); //
		setParams();
		System.out.println("done");
	}
	
	public void setParams(){
		// Start
		double dp1Class = 0d;
		double dp2Class = 0d;
		double cp1Class = 0d;
		double cp2Class = 0d;
		PrettyMap pmap = new PrettyMap();

		int count = 0;
		
		for (Integer id1 : groundTruth) {
			dp1Class = groundTruth.getPointClass(id1);
			for (Integer id2 : groundTruth) {
				dp2Class = groundTruth.getPointClass(id2);
				
				count++;
				int mln = 0;

				if (id1 != id2) {
					for (Cluster cluster : clusterSet) {
						double pClass = cluster.getID();

						for (Datapoint p : cluster) {
							if (p.getID() == id1) {
								cp1Class = pClass;
							} else if (p.getID() == id2) {
								cp2Class = pClass;
							}
						}
					}

					// Update
					if (dp1Class == dp2Class) {
						if (cp1Class == cp2Class) {
							pmap.addPair(new Pair(id1, id2), "TP");
						} else {
							pmap.addPair(new Pair(id1, id2), "FN");
						}
					} else if (dp1Class != dp2Class) {
						if (cp1Class != cp2Class) {
							pmap.addPair(new Pair(id1, id2), "TN");
						} else {
							pmap.addPair(new Pair(id1, id2), "FP");
						}
					}
					if(count >1000000){
						System.out.println("mln: " + (mln++) );
						count = 0;
					}
				}
			}
		}

		HashMap<String, Integer> result = pmap.count();
		this.TP = result.get("TP");
		this.TN = result.get("TN");
		this.FP = result.get("FP");
		this.FN = result.get("FN");
		System.out.println(result.toString());
	}
	
	/**
	 * Quantify the purity into a cluster.
	 */
	protected class PurityQuantifier{
		private HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
		private int count = 0;
		
		public void addInstances(int clusterID){
			if(map.containsKey(clusterID)){
				int occurrences = map.get(clusterID);
				map.remove(clusterID);
				map.put(clusterID, (occurrences+1));
			} else {
				map.put(clusterID, 1);
			}
			count++;
		}
		
		public double quantify(){
			int maxOccurrences = -1;
			
			for(Integer clusterID : map.keySet()){
				int occurrences = map.get(clusterID); 
				if(occurrences > maxOccurrences){
					maxOccurrences = occurrences;
				}
			}
			return ((double)maxOccurrences) / count;
		}
		
		public int getMax(){
			int maxOccurrences = -1;
			
			for(Integer clusterID : map.keySet()){
				int occurrences = map.get(clusterID);
				if(occurrences > maxOccurrences)
					maxOccurrences = occurrences;
			}
			return maxOccurrences;
		}
	}
	
	public abstract double purity();
	
	public abstract double randIndex();
	
	public abstract double precision();
	
	public abstract double recall();
	
	public abstract double fScore(double beta);
	
}
