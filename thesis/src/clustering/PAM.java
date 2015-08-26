package clustering;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import data.Data;
import data.Datapoint;

public class PAM extends Clustering {
	private short k;
	private DistanceMatrix distanceMatrix;
	
	public PAM(short k, Data data) {
		super(data);
		this.k = k;
		this.distanceMatrix = new DistanceMatrix(data);
	}

	@Override
	public void generateClusters() {
		Set<Datapoint> medoids = generateMedoids();
		populateClusters(medoids);
	}
	
	private Set<Datapoint> generateMedoids(){
		Set<Datapoint> medoids = new HashSet<Datapoint>();
		medoids = new Builder(this.k, super.data, this.distanceMatrix).compute();
		medoids = new Swapper(medoids, super.data, this.distanceMatrix).compute();
		return medoids;
	}
	
	private void populateClusters(Set<Datapoint> medoids){
		Map<Datapoint, LinkedList<Datapoint>> clusterList = new HashMap<Datapoint, LinkedList<Datapoint>>();
		for(Datapoint medoid : medoids){
			clusterList.put(medoid, new LinkedList<Datapoint>());
		}
		
		//Every datapoint...
		for(Datapoint dp : this.data){
			Datapoint choosenMedoid = null;
			double minDistance = Double.MAX_VALUE;
			
			//...choose his closest medoid...
			for(Datapoint medoid : medoids){
				if(!dp.equals(medoid)){
					double distance = this.distanceMatrix.getDistance(dp, medoid);
					if(distance < minDistance){
						choosenMedoid = medoid;
						minDistance = distance;
					}
				}
			}
			
			//...and we add it to the choosen medoid's list
			for(Entry<Datapoint, LinkedList<Datapoint>> entry : clusterList.entrySet()){
				if(entry.getKey().equals(choosenMedoid)){
					LinkedList<Datapoint> choosenMedoidList = entry.getValue();
					choosenMedoidList.add(dp);
				}
			}
		}
		
		//In the end, we create one cluster for each cluster list...
		Set<Cluster> clusterSet = new HashSet<Cluster>();
		short clusterID = 0;
		
		for(Entry<Datapoint, LinkedList<Datapoint>> entry : clusterList.entrySet()){
			Datapoint medoid = entry.getKey();
			List<Datapoint> list = entry.getValue();
			Cluster cluster = new Cluster(clusterID++, medoid, list);
			clusterSet.add(cluster);
		}
		
		//...and wrap'em all in a cluster set
		super.clusterSet = new ClusterSet(clusterSet);
	}
}
