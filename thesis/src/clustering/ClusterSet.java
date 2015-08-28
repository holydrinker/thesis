package clustering;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.Set;

import data.Datapoint;
import data.Feature;
import data.FeatureVector;

public class ClusterSet implements Iterable<Cluster> {
	private Set<Cluster> clusters;
	
	public ClusterSet(Set<Cluster> clusters) {
		this.clusters = clusters;
	}

	void exportCsv(FeatureVector fv){
		try {
			PrintWriter pw = new PrintWriter("D:/clusters.csv");
			String nextLine = "clusterID;datapointID;";
			
			//Write feature vector
			for(Feature feature : fv){
				nextLine += (feature.getName() + ";");
			}
			nextLine = nextLine.substring(0, nextLine.length() - 1);
			pw.println(nextLine);
			
			//Write clusters
			for(Cluster cluster : this.clusters){
				short clusterID = cluster.getID();
				
				for(Datapoint dp : cluster){
					nextLine = clusterID + ";" + dp.getID() + ";";
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
