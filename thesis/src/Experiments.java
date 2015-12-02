
import clustering.ClusterSet;
import data.Data;
import data.Dataset;
import evaluation.Metrics;
import evaluation.MetricsA;
import io.DataTO;
import io.FeatureVectorTO;
import io.StreamGenerator;

public class Experiments {

	static final String DATASET = "dataset";
	static final String AUTO_DATASET = "auto";
	static final String DO_PCA = "pca";
	static final String DONT_DO_PCA = "nopca";

	public static void main(String[] args) {
		// Console args
		String fileName = "indianpine";
		String filePath = "../dataset/" + fileName + ".arff";
		String datasetType = "dataset"; 
		String k = "16";
		String pca = "nopca";

		System.out.print("Loading data...");

		// Set the stream generator based on pca option
		StreamGenerator sg = new StreamGenerator(filePath);

		// Build transfer obejcts from stream generator
		FeatureVectorTO fvTO = sg.getFeatureVectorTO();
		DataTO dataTO = sg.getDataTO();

		// ...and than generate dataset!
		Data data = new Dataset(fvTO, dataTO, true);
		System.out.println("done!\n");
		// -------------------------------------------------------------------------------------------------------------------------------------

		System.out.println("Rebuilding cluster...");
		ClusterSetRebuilder rebuilder = new ClusterSetRebuilder(fileName, datasetType, k, pca);
		ClusterSet clusterSet = rebuilder.generate(data);
		System.out.println("Done!");
		
		System.out.println("Computing metrics...");
		MetricsA metrics = new Metrics(clusterSet, data);
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
		
		System.out.println("Everything done!");

	}

}
