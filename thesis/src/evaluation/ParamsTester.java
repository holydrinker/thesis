package evaluation;

import data.Dataset;

import java.util.HashSet;
import java.util.LinkedList;

import clustering.Cluster;
import clustering.ClusterSet;
import data.Datapoint;
import io.StreamGenerator;

public class ParamsTester {

	public static void main(String[] args) {
		//Dataset
		StreamGenerator sg = new StreamGenerator("../dataset/evaluation.arff");
		Dataset dataset = new Dataset(sg.getFeatureVectorTO(), sg.getDataTO(), true);
		
		/*ClusterSet*/
		LinkedList<Datapoint> listC1 = new LinkedList<Datapoint>();
		LinkedList<Datapoint> listC2 = new LinkedList<Datapoint>();
		LinkedList<Datapoint> listC3 = new LinkedList<Datapoint>();
		Datapoint medoid1 = null;
		Datapoint medoid2 = null;
		Datapoint medoid3 = null;
		
		for(Datapoint dp : dataset){
			int id = dp.getID();
			
			if(id == 0)
				medoid1 = dp;
			else if(id > 0 && id <= 4)
				listC1.add(dp);
			else if (id == 5)
				listC2.add(dp);
			else if (id == 6 || id == 7)
				listC3.add(dp);
			else if (id == 8)
				medoid2 = dp;
			else if (id > 8 && id <= 11)
				listC2.add(dp);
			else if (id == 12)
				listC1.add(dp);
			else if (id == 13)
				medoid3 = dp;
			else if(id > 13 && id <= 15)
				listC3.add(dp);
			else if (id == 16)
				listC2.add(dp);
		}

		Cluster c1 = new Cluster((short) 1, medoid1, listC1);
		Cluster c2 = new Cluster((short) 2, medoid2, listC2);
		Cluster c3 = new Cluster((short) 3, medoid3, listC3);
		
		HashSet<Cluster> listSet = new HashSet<Cluster>();
		listSet.add(c1);
		listSet.add(c2);
		listSet.add(c3);
		ClusterSet clusterSet = new ClusterSet(listSet);
		
		
		/*main
		String outputPath = "../output/indianpine_auto_16_pca.csv";
		ClusterSet clusterSet = new ClusterRebuilder(outputPath).compute();
		System.out.println("Computing metrics...");*/
		
		MetricsA metrics = new SamplingMetrics (clusterSet, dataset);
		System.out.print("purity: ");
		System.out.println(metrics.purity());
		System.out.print("RI: ");
		System.out.println(metrics.randIndex());
		System.out.print("P: ");
		System.out.println(metrics.precision());
		System.out.print("R: ");
		System.out.println(metrics.recall());
		double beta = 1;
		System.out.print("F" + beta + ": ");
		System.out.println(metrics.fScore(beta));
	}
}
