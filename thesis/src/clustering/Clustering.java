package clustering;

import data.Data;

public abstract class Clustering {
	protected Data data;
	protected ClusterSet clusterSet;
	
	public Clustering(Data data) {
		this.data = data;
	}
	
	/*
	 * Leaf classes have to populate their super.clusterSet
	 */
	public abstract void generateClusters();
	
	public void exportCsv(String csvName){
		this.clusterSet.exportCsv(csvName, data);
	}
	
	public ClusterSet getClusterSet(){
		return this.clusterSet;
	}
	
	public void setClusterSet(ClusterSet clusterSet){
		this.clusterSet = clusterSet;
	}
}
