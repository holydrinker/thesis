package evaluation;

import java.util.HashMap;

import clustering.Cluster;
import clustering.ClusterSet;
import data.Datapoint;

public class Metrics extends MetricsA {
	
	public Metrics(ClusterSet clusterSet, int datasetSize) {
		super(clusterSet, datasetSize);
	}

	@Override
	public double purity() {
		double result = 0d;
		int clusterCount = 0;
		PurityQuantifier pq = null;
		
		for(Object obj : clusterSet){
			Cluster cluster = (Cluster) obj ;
			
			pq = new PurityQuantifier();			
			for(Datapoint dp : cluster){
				short pointID = dp.getID();
				int classID = groundTruth.getClassID(pointID);
				pq.addOccurrence(classID);
			}
			
			result += pq.quantify();
			clusterCount++;
		}
		
		return result / clusterCount;
	}

	@Override
	public double randIndex() {
		return ((double)(TP + TN)) / (TP + FP + FN + TN);
	}

	@Override
	public double precision() {
		return ((double)TP) / (TP + FP);
	}

	@Override
	public double recall() {
		return ((double)TP) / (TP + FN);
	}

	@Override
	public double fScore(double beta) {
		double num = (Math.sqrt(beta) + 1) * precision() * recall();
		double den = (Math.sqrt(beta) * precision() + recall());
		return num / den;
	}

}
