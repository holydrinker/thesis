import java.util.ArrayList;

import data.Data;
import data.Datapoint;
import data.DatasetFactory;
import data.Feature;
import data.FeatureVector;
import io.DataTO;
import io.FeatureVectorTO;
import io.StreamGenerator;


public class Run {

	public static void main(String[] args) {
		String fileName = "inputFile.txt";
		String datasetType = "dataset";
		String autocorrelationType = "";
		
		//Build transfer obejcts
		StreamGenerator sg = new StreamGenerator(fileName);
		FeatureVectorTO fvTO = sg.getFeatureVectorTO();
		DataTO dataTO = sg.getDataTO();
		
		//Wrapping parameters and pass them to factory.
		ArrayList<Object> params = new ArrayList<Object>();
		params.add(fvTO);
		params.add(dataTO);
		params.add(autocorrelationType); //sarà nullo in caso di dataset senza autocorrelazione
		
		//Chiamo il factory per far istanziare il dataset, passando la scelta del dataset dell'utente e i parametri per la creazione del dataset
		Data data = (Data)new DatasetFactory().getInstance(datasetType, params); 
		
		//Test
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
