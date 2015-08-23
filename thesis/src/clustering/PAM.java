package clustering;

import java.util.HashSet;
import java.util.Set;

import data.Data;
import data.Datapoint;
import distance.DistanceI;

public class PAM extends Clustering {
	private short k;
	private DistanceI d;
	//Qui serve una matrice delle distanze che deve essere utilizzata che probabilmente dovranno utilizzare sia il builder sia lo swapper
	
	public PAM(short k, DistanceI d, Data data) {
		super(data);
	}

	@Override
	public void generateClusters() {
		Set<Datapoint> medoids = generateMedoids();
		populateClusters(medoids);
	}
	
	private Set<Datapoint> generateMedoids(){
		Set<Datapoint> medoids = new HashSet<Datapoint>();
		medoids = new Builder(this.k, super.data).compute();
		medoids = new Swapper(medoids, super.data).compute();
		
		return medoids;
	}
	
	private void populateClusters(Set<Datapoint> medoids){
		//Creare un nuovo cluster
		//popolarlo
		//aggiungerlo a super.clusterSet
	}
}
