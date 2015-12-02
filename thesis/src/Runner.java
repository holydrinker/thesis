import java.util.ArrayList;

import autocorrelation.AcFactory;
import autocorrelation.AutocorrelationI;
import clustering.ClusterSet;
import clustering.Clustering;
import clustering.PAM;

import data.Data;
import data.DataFactory;
import evaluation.Metrics;
import evaluation.MetricsA;
import exception.DatasetException;
import exception.PcaException;
import io.DataTO;
import io.FeatureVectorTO;
import io.PCA;
import io.StreamGenerator;
import sampling.Sampling;

public class Runner {
	static final String DATASET = "dataset";
	static final String AUTO_DATASET = "auto";
	static final String DO_PCA = "pca";
	static final String DONT_DO_PCA = "nopca";
	
	public static void main(String[] args) {
		//Start timer
		double startTime = System.currentTimeMillis();
		
		//Console args
		String fileName = args[0];
		String filePath = "../dataset/"+ fileName + ".arff";
		String datasetType = args[1]; //auto - dataset
		String centroidsNumber = args[2]; //numero di centroidi
		String pca = args[3]; 
		Double samplingPerc = Double.parseDouble(args[4]); //if >= 1, do not sample
		String autocorrelationType = null;
		String radius = null;
		String q = null;
		
		System.out.println("START");
		System.out.print("loading data");
		
		//Set the stream generator based on pca option
		StreamGenerator sg = null;
		if(pca.equalsIgnoreCase(DO_PCA)){
			System.out.print(" applying pca...");
			new PCA(fileName).createTempArff();
			filePath = filePath.replaceAll(fileName, "temp_"+fileName);
		} else if(pca.equalsIgnoreCase(DONT_DO_PCA)) {
			System.out.print("...");
		} else {
			throw new PcaException();
		}
		sg = new StreamGenerator(filePath);
		
		//Build transfer obejcts from stream generator
		FeatureVectorTO fvTO = sg.getFeatureVectorTO();
		DataTO dataTO = sg.getDataTO();
		
		//Build autocorrelation (optional) 
		AutocorrelationI ac = null;
		if(datasetType.equalsIgnoreCase(AUTO_DATASET)){
			autocorrelationType = args[5]; //GO to use GetisOrd
			radius = args[6];
			q = args[7];
			
			//wrap args to call factory for Autocorrelation
			ArrayList<Object> paramsAc = new ArrayList<Object>();
			paramsAc.add((short)Integer.parseInt(radius));
			paramsAc.add((byte)Integer.parseInt(q));
			ac = (AutocorrelationI) new AcFactory().getInstance(autocorrelationType, paramsAc);
		} else if(!datasetType.equalsIgnoreCase(DATASET)){
			throw new DatasetException();
		}
		
		//Wrap all these parameters and pass them to data factory...
		ArrayList<Object> paramsData = new ArrayList<Object>();
		paramsData.add(fvTO);
		paramsData.add(dataTO);
		paramsData.add(ac);
		paramsData.add(radius);
		
		//...and than generate dataset!
		Data data = (Data)new DataFactory().getInstance(datasetType, paramsData);
		System.out.println("done!\n");
		//-------------------------------------------------------------------------------------------------------------------------------------		
		
		
		//CLUSTERING--------------------------------------------------------------------------------------------------------------------------
		short k = (short)(Integer.parseInt(centroidsNumber));
		Clustering PAM = new PAM(k, data);
		PAM.generateClusters();
		ClusterSet clusterSet = PAM.getClusterSet();
		String csvName = args[0] + "_" + args[1] + "_" + args[2] + "_" + args[3];
		PAM.exportCsv(csvName);
		//------------------------------------------------------------------------------------------------------------------------------------
		
		
		//EVALUATION--------------------------------------------------------------------------------------------------------------------------
		System.out.println("Computing metrics...");
		MetricsA metrics = new Metrics(clusterSet, data);
		
		System.out.print("purity: ");
		System.out.println(metrics.purity());
		
		System.out.print("rand index: ");
		System.out.println(metrics.randIndex());
		
		System.out.print("precision: ");
		System.out.println(metrics.precision());
		
		System.out.print("recall: ");
		System.out.println(metrics.recall());
		
		double beta = 1;
		System.out.print("F" + beta + " score: ");
		System.out.println(metrics.fScore(beta));
		
		//-------------------------------------------------------------------------------------------------------------------------------------
		
		
		//SAMPLING-----------------------------------------------------------------------------------------------------------------------------
		if(samplingPerc < 1d){
			System.out.println("Sampling...");
			
			ClusterSet sampledSet = new Sampling(samplingPerc, clusterSet).compute();
			PAM.setClusterSet(sampledSet);
			csvName = args[0] + "_" + args[1] + "_" + args[2] + "_" + args[3] + "_sampling_"+samplingPerc;
			PAM.exportCsv(csvName);
		}
		//-------------------------------------------------------------------------------------------------------------------------------------
		
		
		//END----------------------------------------------------------------------------------------------------------------------------------
		double endTime = System.currentTimeMillis(); 
		double time = endTime - startTime;
		double sec = time / 1000;
		double min = sec / 60;
		double hours = min / 60;
		System.out.println("Everything done in hours: " + hours);
		//-------------------------------------------------------------------------------------------------------------------------------------
	}

}
