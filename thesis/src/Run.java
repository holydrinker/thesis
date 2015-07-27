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
		String fileName = "lol.txt";
		String datasetType = "dataset";
		String autocorrelationType = "";
		
		//Costurisco transfer obejct
		StreamGenerator sg = new StreamGenerator(fileName);
		FeatureVectorTO fvTO = sg.getFeatureVectorTO();
		DataTO dataTO = sg.getDataTO();
		
		//Incapsulo tutto nei parametri da passare al factory
		ArrayList<Object> params = new ArrayList<Object>();
		params.add(fvTO);
		params.add(dataTO);
		params.add(autocorrelationType); //sarà nullo in caso di dataset senza autocorrelazione
		
		//Chiamo il factory per far istanziare il dataset
		Data data = (Data)new DatasetFactory().getInstance(datasetType, params);
		
		//TEST
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
