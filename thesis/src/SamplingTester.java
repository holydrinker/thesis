
import clustering.ClusterSet;
import sampling.Sampling;

public class SamplingTester {

	public static void main(String[] args) {
		String fileName = "indianpine";
		String dataType = "dataset";
		String k = "16";
		String pca = "nopca";
		
		double perc = 0.05;
		
		ClusterSetRebuilder rebuilder = new ClusterSetRebuilder(fileName, dataType, k, pca);
		ClusterSet clusterSet = rebuilder.generate(null);
		
		ClusterSet sampledSet = new Sampling(perc, clusterSet).compute(); 
		
		String writeName = rebuilder.getNameForWrites();
		System.out.println("Write in: " + writeName);
		sampledSet.exportCsv(writeName, rebuilder.getFeatureVector());
		System.out.println("from " + clusterSet.size() + " to " + sampledSet.size());
		System.out.println("corret: " + clusterSet.size() * perc);
	}

}
