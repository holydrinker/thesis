package clustering;

import data.Data;
import evaluation.ClusterAssignment;

public abstract class Clustering {
	protected Data data;
	protected ClusterSet clusterSet;
	protected ClusterAssignment assignm;
	
	public Clustering(Data data, ClusterAssignment assignm) {
		this.data = data;
		this.assignm = assignm;
	}
	
	/*
	 * Leaf classes have to populate their super.clusterSet
	 */
	public abstract void generateClusters();
	
	public void exportCsv(String csvName){
		this.clusterSet.exportCsv(csvName, data.getFeatureVector());
	}
	
	public ClusterSet getClusterSet(){
		return this.clusterSet;
	}
}
