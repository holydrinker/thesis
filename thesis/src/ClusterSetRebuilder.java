import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

import clustering.ClusterSet;
import clustering.Cluster;
import data.ContinueFeature;
import data.Data;
import data.Datapoint;
import data.Feature;
import data.FeatureVector;
import io.DatapointTO;
import io.FeatureVectorTO;

public class ClusterSetRebuilder {
	private String fileName;
	private FeatureVector featureVector;
	private String dir = "../output/";
	private String filePath;
	
	public ClusterSetRebuilder(String name, String dataType, String k, String pca) {
		fileName = name + "_" + dataType + "_" + k + "_" + pca + ".csv";
		filePath = dir + fileName;
	}
	
	public String getFilePath(){
		return this.filePath;
	} 
	
	public String getNameForWrites(){
		String temp = this.fileName.substring(0, this.fileName.length() - 4);
		return "test_" + temp;
	}
	
	public FeatureVector getFeatureVector(){
		return this.featureVector;
	}
	
	public ClusterSet generate(Data data){
		HashSet<Cluster> set = new HashSet<Cluster>(); 
		
		try {
			System.out.println(filePath);
			BufferedReader br = new BufferedReader(new FileReader(filePath));
			
			// Rebuild feature vector
			String line = br.readLine();
			LinkedList<Object> featureList = new LinkedList<Object>();
			String features[] = line.split(";");
			for(int i = 4; i < features.length; i++){
				Feature f = new ContinueFeature(features[i]);
				featureList.add(f);
			}
			featureVector = new FeatureVector(new FeatureVectorTO(featureList));
			
			// Rebuild clusters
			line = br.readLine();
			int lastClusterID = -1;
			List<Datapoint> clusterList = new LinkedList<Datapoint>();
			LinkedList<Object> values = new LinkedList<Object>();
			
			// Check switch cluster
			while(line != null){
				String split[] = line.split(";");
				int clusterID = Integer.parseInt(split[0]);
				
				if(clusterID != lastClusterID && lastClusterID != -1){
					Cluster newCluster = new Cluster((short)lastClusterID, null, clusterList);
					set.add(newCluster);
					clusterList = new LinkedList<Datapoint>();
				}
				lastClusterID = clusterID;
				
				// Extract datapoint
				int pointID = Integer.parseInt(split[1]);
				for(int i = 2; i < split.length; i++){
					//System.out.println(split[i]);
					values.add(Double.parseDouble(split[i]));
				}
				clusterList.add(new Datapoint((short) pointID, new DatapointTO(values)));
				values = new LinkedList<Object>();
				
				// Iterate
				line = br.readLine();
			}
			
			// Add the last cluster
			Cluster newCluster = new Cluster((short) lastClusterID, null, clusterList);
			set.add(newCluster);
			br.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		ClusterSet clusterSet = new ClusterSet(set);
		classRecovery(clusterSet, data); //pass null DATA if you don't need class recovery
		return new ClusterSet(set);
	}
	
	private void classRecovery(ClusterSet clusterSet, Data data){
		if(data == null) //I don't need class recovery
			return;
		
		for(Cluster cluster : clusterSet){
			for(Datapoint datapoint : cluster){
				Datapoint rootPoint = data.getDatapoint(datapoint.getID());
				datapoint.setClass(rootPoint.getClassID());
			}
		}
		
	}
	
}
