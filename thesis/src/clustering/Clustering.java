package clustering;

import data.Data;

public abstract class Clustering {
	protected Data data;
	public ClusterSet clusterSet;
	
	public Clustering(Data data) {
		this.data = data;
	}
	
	/*
	 * Populate leaf classes have to populate their super.clusterSet
	 */
	public abstract void generateClusters();
	
	public void exportCsv(){
		//this.clusterSet.exportCsv();
	}
}
