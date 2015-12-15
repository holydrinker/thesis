
import clustering.ClusterSet;
import sampling.Sampling;

public class SamplingTester {

	public static void main(String[] args) {		
		double perc = 0.2;
		
		//Rebuild dataset
		String outputPath = "../output/indianpine_auto_20_pca.csv";
		ClusterRebuilder rebuilder = new ClusterRebuilder(outputPath, null);
		ClusterSet clusterSet = rebuilder.compute();
		
		//Sample dataset
		ClusterSet sampledSet = new Sampling(perc, clusterSet).compute(); 
		
		//Export sampled data
		String writeName = outputPath + "_sampling_" + perc;
		System.out.println("Write in: " + writeName);
		sampledSet.exportCsv(writeName, rebuilder.getFeatureVector());
		System.out.println("from " + clusterSet.size() + " to " + sampledSet.size());
		System.out.println("corret: " + clusterSet.size() * perc);
	}

}
