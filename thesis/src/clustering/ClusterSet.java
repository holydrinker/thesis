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

	public void exportCsv(String csvName, FeatureVector fv){
		try {
			PrintWriter pw = new PrintWriter(csvName +".csv"); //levare il primo output per il sampling
			
			//Write feature vector
			String nextLine = "clusterID;pixelID;coord_X;coord_Y;";
			for(Feature feature : fv){
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

	public short size(){
		int count = 0;
		for(Cluster cluster : this){
			count += cluster.size();
		}
		return (short) count;
	}
	
	@Override
	public Iterator<Cluster> iterator() {
		return this.clusters.iterator();
	}
}
