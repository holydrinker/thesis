import java.util.ArrayList;

import autocorrelation.AcFactory;
import autocorrelation.AutocorrelationI;
import clustering.Clustering;
import clustering.PAM;

import data.Data;
import data.DataFactory;
import io.DataTO;
import io.FeatureVectorTO;
import io.StreamGenerator;

public class Runner {
	//TESTARE SE I VALORI DI AUTOCORRELAZIONE HANNO SENSO
	
	public static void main(String[] args) {
		//Start timer
		double startTime = System.currentTimeMillis(); 
		
		//Console args
		String fileName = "D:/dataset/indianpine.arff"; //Il path e il nome del file saranno dei parametri
		String datasetType = "auto"; //DATASET or AUTO lower case
		String autocorrelationType = null;
		String radius = null;
		String q = null;
		String centroidsNumber = "6"; //numero di centroidi
		
		System.out.println("START\n");
		System.out.print("loading data...");
		
		//If you choose autocorrelation... 
		AutocorrelationI ac = null;
		if(datasetType.equalsIgnoreCase("auto")){
			autocorrelationType = "GO"; //GO to use GetisOrd
			radius = "3";
			q = "3";
			
			//...wrap args to call factory for Autocorrelation
			ArrayList<Object> paramsAc = new ArrayList<Object>();
			paramsAc.add((short)Integer.parseInt(radius));
			paramsAc.add((byte)Integer.parseInt(q));
			ac = (AutocorrelationI) new AcFactory().getInstance(autocorrelationType, paramsAc);
		}
		
		//Build transfer obejcts
		StreamGenerator sg = new StreamGenerator(fileName);
		FeatureVectorTO fvTO = sg.getFeatureVectorTO();
		DataTO dataTO = sg.getDataTO();
		
		//Wrapping parameters and pass them to data factory.
		ArrayList<Object> paramsData = new ArrayList<Object>();
		paramsData.add(fvTO);
		paramsData.add(dataTO);
		paramsData.add(ac);
		paramsData.add(radius);
		
		//User customized dataset creation
		Data data = (Data)new DataFactory().getInstance(datasetType, paramsData); 
		
		/*PRINTING TEST...
		FeatureVector fv = data.getFeatureVector();
		for(Object obj : fv){
			Feature f = (Feature)obj;
			System.out.print(f.getName() + " ");
		}
		System.out.println("");
		
		int count = 0;
		for(Object obj : data){
			Datapoint dp = (Datapoint)obj;
			for(Object value : dp)
				System.out.print(value.toString() + " ");
			System.out.println("");
			count++;
		}
		System.out.println("Dataset size: " + count);*/
			
		System.out.println("done\n");
		
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
