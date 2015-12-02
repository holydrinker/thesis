package evaluation;

import clustering.Cluster;
import clustering.ClusterSet;
import data.Data;
import data.Datapoint;

public abstract class MetricsA {
	protected ClusterSet clusterSet;
	protected ClusterAssignment assignm;
	
	protected int TP;
	protected int FP;
	protected int TN;
	protected int FN;
	protected final String TP_KEY = "TP";
	protected final String TN_KEY = "TN";
	protected final String FP_KEY = "FP";
	protected final String FN_KEY = "FN";

	public MetricsA(ClusterSet clusterSet, Data data) {
		this.clusterSet = clusterSet;
		assignm = new ClusterAssignment(data.size());
		//setClusterAssignment(data);
		setClusterAssignment(clusterSet);
		setParams(data);
	}
	
	private void setClusterAssignment(Data data){
		for (Datapoint datapoint : data) {
			assignm.addRecord(datapoint.getID(), datapoint.getClassID(), datapoint.getClusterID());
		}
	}
	
	private void setClusterAssignment(ClusterSet clusterSet){
		for(Cluster cluster : clusterSet){
			for(Datapoint point : cluster){
				assignm.addRecord(point.getID(), point.getClassID(), point.getClusterID());
			}
		}
	}
	
	protected abstract void setParams(Data data);
	
	public double purity() {
		double result = 0d;
		int clusterCount = 0;
		PurityQuantifier pq = null;
		
		for(Object obj : clusterSet){
			Cluster cluster = (Cluster) obj ;
			
			pq = new PurityQuantifier();			
			for(Datapoint dp : cluster){
				short pointID = dp.getID();
				int classID = assignm.getClassID(pointID);
				pq.addOccurrence(classID);
			}
			
			result += pq.quantify();
			clusterCount++;
		}
		
		return result / clusterCount;
	}

	public double randIndex() {
		return ((double)(TP + TN)) / (TP + FP + FN + TN);
	}

	public double precision() {
		return ((double)TP) / (TP + FP);
	}

	public double recall() {
		return ((double)TP) / (TP + FN);
	}

	public double fScore(double beta) {
		double num = (Math.sqrt(beta) + 1) * precision() * recall();
		double den = (Math.sqrt(beta) * precision() + recall());
		return num / den;
	}
	
}
