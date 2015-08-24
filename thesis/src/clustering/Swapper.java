package clustering;

import java.util.HashSet;
import java.util.Set;

import data.Data;
import data.Datapoint;

public class Swapper {
	private Set<Datapoint> medoids = new HashSet<Datapoint>();
	private DistanceMatrix distanceMatrix;
	
	public Swapper(Set<Datapoint> medoids, Data data, DistanceMatrix distanceMatrix) {
		this.distanceMatrix = distanceMatrix;
	}
	
	Set<Datapoint> compute(){
		return this.medoids;
	}
	
	//TO-DO
	//Implementare tutta la logica e i metoidi privati previsti dal progetto
}
