package evaluation;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.LinkedList;

import clustering.Cluster;
import clustering.ClusterSet;
import data.ContinueFeature;
import data.Datapoint;
import data.FeatureVector;
import io.FeatureVectorTO;

public class ClusterRebuilder {
	private String outputPath;
	private ClusterAssignment assignm;
	private FeatureVector fv;
	
	public ClusterRebuilder(String outputPath, ClusterAssignment assignm) {
		this.outputPath = outputPath;
		this.assignm = assignm;
	}
	
	public ClusterSet compute(){
		BufferedReader br = null;
		HashSet<Cluster> set = new HashSet<Cluster>();
		
		try {
			br = new BufferedReader(new FileReader(outputPath));
			String nextLine = br.readLine();
			
			//Build feature vector
			String splitLine[] = nextLine.split(";");
			LinkedList<Object> featureList = new LinkedList<Object>();
			
			//Build feature vector
			for(int i = 4; i < splitLine.length; i++){
				ContinueFeature f = new ContinueFeature(splitLine[i]);
				featureList.add(f);
			}
			fv = new FeatureVector(new FeatureVectorTO(featureList));
			//Feature vector built!
			
			//Start mining data
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
				dp.setX((short) x);
				dp.setY((short)y);
				for(int i = 4; i < split.length; i++){
					dp.addValue(Double.parseDouble(split[i]));
				}

				//Se abbiamo cambiato cluster, salva il precedente (ovviamente facendo eccezione sul primo, che non ha un precedente)
				if(clusterID != lastID){
					if(lastID != -1){
						Cluster cluster = new Cluster((short)lastID, medoid, pointList, assignm);
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
			Cluster cluster = new Cluster((short)clusterID, medoid, pointList, assignm);
			set.add(cluster);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return new ClusterSet(set);
	}
	
	public FeatureVector getFeatureVector(){
		return this.fv;
	}
	
}
