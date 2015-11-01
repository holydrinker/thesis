package evaluation;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.LinkedList;

import clustering.Cluster;
import clustering.ClusterSet;
import data.Datapoint;

public class ClusterRebuilder {
	private String outputPath;
	
	public ClusterRebuilder(String outputPath) {
		this.outputPath = outputPath;
	}
	
	public ClusterSet compute(){
		BufferedReader br = null;
		HashSet<Cluster> set = new HashSet<Cluster>();
		
		try {
			br = new BufferedReader(new FileReader(outputPath));
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
		
		return new ClusterSet(set);
	}
	
}
