package clustering;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.Set;

import data.Data;
import data.Datapoint;
import data.Feature;

public class ClusterSet implements Iterable<Cluster> {
	private Set<Cluster> clusters;
	
	public ClusterSet(Set<Cluster> clusters) {
		this.clusters = clusters;
	}

	void exportCsv(Data data){
		try {
			PrintWriter pw = new PrintWriter("D:/clusteringOutput.csv");
			String nextLine = "clusterID;pixelID;coord_X;coord_Y;";
			
			//Write feature vector
			for(Feature feature : data.getFeatureVector()){
				nextLine += (feature.getName() + ";");
			}
			nextLine = nextLine.substring(0, nextLine.length() - 1);
			pw.println(nextLine);
			
			//Write clusters
			for(Cluster cluster : this.clusters){
				short clusterID = cluster.getID();
				
				for(Datapoint dp : cluster){
					short id = dp.getID();
					short x = data.getCoord(id).x;
					short y = data.getCoord(id).y;
					nextLine = clusterID + ";" + id + ";" + x + ";" + y + ";"; //+1 alle coordinate perchè i pixel partivano da 1 ma nella mia matrice partivano da 0
					for(Object value : dp){
						nextLine += value + ";";
					}
					nextLine = nextLine.substring(0, nextLine.length() - 1);
					pw.println(nextLine);
				}
			}
			
			pw.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Iterator<Cluster> iterator() {
		return this.clusters.iterator();
	}
}
