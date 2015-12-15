package evaluation;

import clustering.Cluster;
import clustering.ClusterSet;
import data.Datapoint;

public abstract class MetricsA {
	protected ClusterAssignment assignm;
	protected ClusterSet clusterSet;
	
	protected int TP;
	protected int FP;
	protected int TN;
	protected int FN;

	public MetricsA(ClusterSet clusterSet, int datasetSize, ClusterAssignment assignm) {
		this.clusterSet = clusterSet;
		this.assignm = assignm;
		setParams(datasetSize);
	}
	
	protected abstract void setParams(int datasetSize);
	
	public double purity(){
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
	
	public double randIndex(){
		return ((double)(TP + TN)) / (TP + FP + FN + TN);
	}
	
	public double precision(){
		return ((double)TP) / (TP + FP);
	}
	
	public double recall(){
		return ((double)TP) / (TP + FN);
	}
	
	public double fScore(double beta){
		double P = precision();
		double R = recall();
	
		double num = (Math.pow(beta, 2) + 1) * P * R;
		double den = (Math.pow(beta,2) * P + R);
		return num / den;
	}
	
}
