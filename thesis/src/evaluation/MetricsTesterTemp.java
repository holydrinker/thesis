package evaluation;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;

import autocorrelation.AcFactory;
import autocorrelation.AutocorrelationI;
import clustering.Cluster;
import clustering.ClusterSet;
import data.Data;
import data.DataFactory;
import data.Datapoint;
import exception.DatasetException;
import exception.PcaException;
import io.DataTO;
import io.FeatureVectorTO;
import io.PCA;
import io.StreamGenerator;

public class MetricsTesterTemp {
	static final String DATASET = "dataset";
	static final String AUTO_DATASET = "auto";
	static final String DO_PCA = "pca";
	static final String DONT_DO_PCA = "nopca";
	
	//Cercare di avere un solo file di input per avere si ail ground truth e sia il dataset.
	
	public static void main(String[] args) {
		//Console args
		String fileName = args[0];
		String filePath = "../dataset/"+ fileName + ".arff";
		String datasetType = args[1]; //auto - dataset
		String autocorrelationType = null;
		String radius = null;
		String q = null;
		String centroidsNumber = args[2]; //numero di centroidi
		String pca = args[3]; 
		
		System.out.println("Start metrics test");
		System.out.print("loading data");
		
		//Set the stream generator based on pca option
		//Sostituire con un altro factory
		StreamGenerator sg = null;
		if(pca.equalsIgnoreCase(DO_PCA)){
			System.out.print(" applying pca...");
			new PCA(fileName).createTempArff();
			filePath.replaceAll(fileName, "temp_"+fileName);
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
		System.out.println("done!\n");
		
		//-----------------------------------------------------------------------------------------------
		
		System.out.println("Rebuilding clusters...");
		
		String outputFile = args[0] + "_" + args[1] + "_" + args[2] + "_" + args[3] + ".csv";
		String clusterFilePath = "../output/" + outputFile;
		HashSet<Cluster> set = new HashSet<Cluster>();
		
		try {
			BufferedReader br = new BufferedReader(new FileReader(clusterFilePath));
			String nextLine = br.readLine();
			nextLine = br.readLine();
			
			int lastID = -1;
			int clusterID = -1;
			Datapoint medoid = null;
			LinkedList<Datapoint> pointList = null;
			
			//cicla ed estrai il nuovo datapoint
			while(nextLine != null) {
				String split[] = nextLine.split(";");
				clusterID = Integer.parseInt(split[0]);
				
				int id = Integer.parseInt(split[1]);
				int x = Integer.parseInt(split[2]);
				int y = Integer.parseInt(split[3]);
				
				Datapoint dp = new Datapoint((short) id);
				dp.x = (short)x;
				dp.y = (short)y;
				for(int i = 4; i < split.length; i++){
					dp.addValue(Double.parseDouble(split[i]));
				}

				//Se abbiamo cambiato cluster, salva il precedente (ovviamente facendo eccezione sul primo, che non ha un precedente)
				if(clusterID != lastID){
					if(lastID != -1){
						Cluster cluster = new Cluster((short)lastID, medoid, pointList);
						set.add(cluster);
					}
					lastID = clusterID;
					//e setta la variabili per accogliere il nuovo medoide
					medoid = dp;
					pointList = new LinkedList<Datapoint>();
				} else {
					pointList.add(dp);
				}
				
				nextLine = br.readLine();
			}
			
			br.close();
			Cluster cluster = new Cluster((short)clusterID, medoid, pointList);
			set.add(cluster);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		ClusterSet clusterSet = new ClusterSet(set);
		System.out.println("habemus clusterset\n");
		//---------------------------------------------------------------habemus dataset and clusterset!
		
		Metrics metrics = new Metrics(data.getGroundTruth(), clusterSet);
		
	}
}
