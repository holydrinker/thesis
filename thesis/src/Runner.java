import java.util.ArrayList;

import autocorrelation.AcFactory;
import autocorrelation.AutocorrelationI;
import data.Data;
import data.Datapoint;
import data.DatasetFactory;
import data.Feature;
import data.FeatureVector;
import io.DataTO;
import io.FeatureVectorTO;
import io.StreamGenerator;


public class Runner {

	public static void main(String[] args) {
		//Input args
		String fileName = "inputFile.txt";
		String datasetType = "dataset"; //Inserire DATASET per il dataset normale oppure AUTO per il dataset ottenuto con le autocorrelazioni
		String autocorrelationType = "GO"; //Inserire GO per l'autocorrelazione GO o NONE per lavorare senza autocorrelazione
		String radius = "2";
		String q = "3";
		
		//Build transfer obejcts
		StreamGenerator sg = new StreamGenerator(fileName);
		FeatureVectorTO fvTO = sg.getFeatureVectorTO();
		DataTO dataTO = sg.getDataTO();
		
		//Wrapping args to call factory for Autocorrelation
		ArrayList<Object> paramsAc = new ArrayList<Object>();
		paramsAc.add((short)Integer.parseInt(radius));
		paramsAc.add((byte)Integer.parseInt(q));
		AutocorrelationI ac = (AutocorrelationI) new AcFactory().getInstance(autocorrelationType, paramsAc);
		
		//Wrapping parameters and pass them to factory.
		ArrayList<Object> paramsData = new ArrayList<Object>();
		paramsData.add(fvTO);
		paramsData.add(dataTO);
		paramsData.add(ac);
		paramsData.add((short)Integer.parseInt(radius));
		
		//Chiamo il factory per far istanziare il dataset, passando la scelta del dataset dell'utente e i parametri per la creazione del dataset
		Data data = (Data)new DatasetFactory().getInstance(datasetType, paramsData); 
		
		//Print Test
		FeatureVector fv = data.getFeatureVector();
		for(Object obj : fv){
			Feature f = (Feature)obj;
			System.out.print(f.getName() + " ");
		}
		System.out.println("");
		
		for(Object obj : data){
			Datapoint dp = (Datapoint)obj;
			for(Object value : dp)
				System.out.print(value.toString() + " ");
			System.out.println("");
		}
	}

}
