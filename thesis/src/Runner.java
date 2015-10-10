import java.util.ArrayList;

import autocorrelation.AcFactory;
import autocorrelation.AutocorrelationI;
import clustering.Clustering;
import clustering.PAM;

import data.Data;
import data.DataFactory;
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
	
	//IL FILE TEMPORANEO VIENE SALVATO SENZA LA FORMATTAZIONE CHE VOGLIO IO, QUINDI DEVO MODIFICARE LO STREAM GENERATOR
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
		StreamGenerator sg;
		if(pca.equalsIgnoreCase(DO_PCA)){
			System.out.print(" applying pca...");
			sg = new PCA(fileName).createStreamGenerator();
		} else if(pca.equalsIgnoreCase(DONT_DO_PCA)) {
			System.out.print("...");
			sg = new StreamGenerator(filePath);
		} else {
			throw new PcaException();
		}
		System.exit(0);
		
		//Build transfer obejcts
		FeatureVectorTO fvTO = sg.getFeatureVectorTO();
		DataTO dataTO = sg.getDataTO();
		
		//Build autocorrelation (optional) 
		AutocorrelationI ac = null;
		if(datasetType.equalsIgnoreCase(AUTO_DATASET)){
			autocorrelationType = "GO"; //GO to use GetisOrd
			radius = "3";
			q = "3";
			
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
		
		//CLUSTERING
		short k = (short)(Integer.parseInt(centroidsNumber));
		Clustering PAM = new PAM(k, data);
		PAM.generateClusters();
		System.out.println("Everything done:");
		
		//Export clusters in csv
		PAM.exportCsv();
		
		//end timer
		double endTime = System.currentTimeMillis(); 
		double time = endTime - startTime;
		System.out.println("millisecondi = " + time);
		double secondi = time / 1000;
		System.out.println("secondi = " + secondi);
		double minuti = secondi / 60;
		System.out.println("minuti = " + minuti);
		double ore = minuti / 60;
		System.out.println("ore = " + ore);
	}

}
