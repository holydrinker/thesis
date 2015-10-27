package metrics;

/*
 * Classe utilizzata solo per fare delle prove
 */
public class MetricsRunner {

	public static void main(String[] args) {
		String groundTruthPath = "dataset/indianpine.csv";
		String outputPath = "output/clustering_indianpine_dataset_16_nopca_medoid.csv";
		
		Metrics metrics = new LinearMetrics(groundTruthPath, outputPath);
		double purity = metrics.purity();
		System.out.println("purity: " + purity);
	}
	
}
