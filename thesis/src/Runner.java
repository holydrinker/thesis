import java.util.ArrayList;

import autocorrelation.AcFactory;
import autocorrelation.AutocorrelationI;
import clustering.Clustering;
import clustering.PAM;
import data.Data;
import data.Datapoint;
import data.DataFactory;
import data.Feature;
import data.FeatureVector;
import io.DataTO;
import io.FeatureVectorTO;
import io.StreamGenerator;

public class Runner {
	//TESTARE SE I VALORI DI AUTOCORRELAZIONE HANNO SENSO
	
	public static void main(String[] args) {
		//Console args
		String fileName = "D:/dataset/indianpine.arff"; //Il path e il nome del file saranno dei parametri
		String datasetType = "dataset"; //DATASET or AUTO lower case
		String autocorrelationType = null;
		String radius = null;
		String q = null;
		String centroidsNumber = "36"; //numero di centroidi
		
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
		System.out.println("Dataset size: " + count);
		*/
		
		//CLUSTERING
		short k = (short)(Integer.parseInt(centroidsNumber));
		Clustering PAM = new PAM(k, data);
		PAM.generateClusters();
	}

}
