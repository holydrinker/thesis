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

	void exportCsv(String csvName, Data data){
		try {
			PrintWriter pw = new PrintWriter("output/"+ csvName +".csv");
			
			//Write feature vector
			String nextLine = "clusterID;pixelID;coord_X;coord_Y;";
			for(Feature feature : data.getFeatureVector()){
				nextLine += (feature.getName() + ";");
			}
			nextLine = nextLine.substring(0, nextLine.length() - 1);
			pw.println(nextLine);
			
			//Write clusters
			for(Cluster cluster : this.clusters){
				short clusterID = cluster.getID();
				for(Datapoint dp : cluster){
					nextLine = clusterID + ";" + dp.toString();
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
