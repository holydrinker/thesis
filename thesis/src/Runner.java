import java.util.ArrayList;

import autocorrelation.AcFactory;
import autocorrelation.AutocorrelationI;
import clustering.ClusterSet;
import clustering.Clustering;
import clustering.PAM;

import data.Data;
import data.DataFactory;
import evaluation.ClusterAssignment;
import evaluation.Metrics;
import evaluation.MetricsA;
import exception.DatasetException;
import exception.PcaException;
import io.DataTO;
import io.FeatureVectorTO;
import io.PCA;
import io.StreamGenerator;

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
		String filePath = "dataset/"+ fileName + ".arff";
		String datasetType = args[1]; //auto - dataset
		String autocorrelationType = null;
		String radius = null;
		String q = null;
		String centroidsNumber = args[2]; //numero di centroidi
		String pca = args[3]; 
		
		System.out.println("START");
		System.out.print("loading data");
		
		//Set the stream generator based on pca option
		//Sostituire con un altro factory
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
		
		//Build transfer obejcts
		FeatureVectorTO fvTO = sg.getFeatureVectorTO();
		DataTO dataTO = sg.getDataTO();
		
		//Build autocorrelation (optional) 
		AutocorrelationI ac = null;
		if(datasetType.equalsIgnoreCase(AUTO_DATASET)){
			autocorrelationType = args[4]; //GO to use GetisOrd
			radius = args[5];
			q = args[6];
			
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
		ClusterAssignment assignm = data.getClusterAssignment(); //This object will lead the clustering evaluation
		System.out.println("done!\n");
		//-------------------------------------------------------------------------------------------------------------------------------------		
		
		
		//CLUSTERING AND EVALUATION------------------------------------------------------------------------------------------------------------
		short k = (short)(Integer.parseInt(centroidsNumber));
		Clustering PAM = new PAM(k, data, assignm); //lo passo al clustering che lo modifica
		PAM.generateClusters();
		ClusterSet clusterSet = PAM.getClusterSet();
		String csvName = args[0] + "_" + args[1] + "_" + k + "_" + args[3];
		PAM.exportCsv(csvName);
		
		System.out.println("Computing metrics for k = " + k);
		MetricsA metrics = new Metrics(clusterSet, data.size(), assignm); //lo usano le metriche
		System.out.print("purity: ");
		System.out.println(metrics.purity());
		System.out.print("rand index: ");
		System.out.println(metrics.randIndex());
		System.out.print("precision: ");
		System.out.println(metrics.precision());
		System.out.print("recall: ");
		System.out.println(metrics.recall());
		final double BETA = 1;
		System.out.print("F" + BETA + " score: ");
		System.out.println(metrics.fScore(BETA));
		//------------------------------------------------------------------------------------------------------------------------------------
		
		
		//END----------------------------------------------------------------------------------------------------------------------------------
		double endTime = System.currentTimeMillis(); 
		double time = endTime - startTime;
		double secondi = time / 1000;
		double minuti = secondi / 60;
		double ore = minuti / 60;
		System.out.println("Everything done in hours: " + ore);
		//-------------------------------------------------------------------------------------------------------------------------------------
	}

}
