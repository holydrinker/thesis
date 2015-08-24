package clustering;

import java.util.HashSet;
import java.util.Set;

import data.Data;
import data.Datapoint;
import distance.DistanceI;

public class PAM extends Clustering {
	private short k;
	//Matrice delle distanze che deve essere utilizzata che probabilmente dovranno utilizzare sia il builder sia lo swapper, quindi settato a visibilità di package
	private DistanceMatrix distanceMatrix;
	
	public PAM(short k, Data data) {
		super(data);
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
		//Creare un nuovo cluster per ogni medoide
		//popolarlo
		//aggiungerlo a super.clusterSet
	}
}
