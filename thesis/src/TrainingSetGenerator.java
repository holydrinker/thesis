import java.util.ArrayList;

import autocorrelation.AcFactory;
import autocorrelation.AutocorrelationI;
import data.Data;
import data.DataFactory;
import evaluation.ClusterAssignment;
import exception.DatasetException;
import exception.PcaException;
import io.DataTO;
import io.FeatureVectorTO;
import io.PCA;
import io.StreamGenerator;
import sampling.RandomSampler;

public class TrainingSetGenerator {
	static final String DATASET = "dataset";
	static final String AUTO_DATASET = "auto";
	static final String DO_PCA = "pca";
	static final String DONT_DO_PCA = "nopca";
	
	public static void main(String[] args) {
		// Console args
		String fileName = "indianpine";
		String filePath = "../dataset/" + fileName + ".arff";
		String datasetType = "dataset";
	
		System.out.println("START");
		System.out.print("loading data...");
		
		StreamGenerator sg = new StreamGenerator(filePath);;
	
		// Build transfer obejcts
		FeatureVectorTO fvTO = sg.getFeatureVectorTO();
		DataTO dataTO = sg.getDataTO();

		// Wrap all these parameters and pass them to data factory...
		ArrayList<Object> paramsData = new ArrayList<Object>();
		paramsData.add(fvTO);
		paramsData.add(dataTO);
		paramsData.add(null);
		paramsData.add(null);

		// ...and than generate dataset!
		Data data = (Data) new DataFactory().getInstance(datasetType, paramsData);
		System.out.println("done!\n");
		// -------------------------------------------------------------------------------------------------------------------------------------	
		
		//TRAINING SET GENERATOR
		System.out.print("Extracting training set...");
		double perc = 0.2;
		RandomSampler randomSampler = new RandomSampler(perc);
		randomSampler.generateTrainingSet(data);
		System.out.println("Done!");

	}

}
