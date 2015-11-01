package evaluation;

import java.util.HashMap;

import clustering.Cluster;
import clustering.ClusterSet;
import data.Data;
import data.Datapoint;
import data.Feature;
import data.FeatureVector;
import data.GroundTruth;

public class Metrics extends MetricsA {

	public Metrics(GroundTruth groundTruthPath, ClusterSet clusterSet) {
		super(groundTruthPath, clusterSet);
	}

	@Override
	public double purity() {
		double result = 0d;
		int count = 0;
		PurityQuantifier pq = null;
		
		for(Object obj : clusterSet){
			Cluster cluster = (Cluster) obj ;
			pq = new PurityQuantifier();
			
			for(Object obj1 : cluster){
				int pointID = ((Datapoint) obj1).getID();
				int clusterID = groundTruth.getPointClass(pointID);
				pq.addInstances(clusterID);
			}
			
			purityMap.put((int)cluster.getID(), pq);
			result += pq.quantify();
			count++;
		}
		
		return result / count;
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
