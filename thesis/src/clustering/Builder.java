package clustering;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import data.Data;
import data.Datapoint;

public class Builder {
	private Set<Datapoint> selectedObject = new HashSet<Datapoint>();
	
	public Builder(short k, Data data, DistanceMatrix distanceMatrix) {
		for(int i = 0; i < k; i++){
			Datapoint nextMedoid = getNextMedoid(data, distanceMatrix);
			selectedObject.add(nextMedoid);
		}
	}
	
	Set<Datapoint> compute(){
		return this.selectedObject;
	}
	
	private Datapoint getNextMedoid(Data data, DistanceMatrix distanceMatrix){
		//Here I'm gonna save (candidate - distance from all other points) associations
		HashMap<Datapoint, Double> logBook = new HashMap<Datapoint, Double>();

		//Compute distance from one candidate to all other unselected object
		for (Object obj1 : data) {			
			Datapoint candidate = (Datapoint) obj1;
			
			if(!selectedObject.contains(candidate)){
				double distanceSum = 0d;

				for (Object obj2 : data) {
					Datapoint notCandidate = (Datapoint) obj2;
					distanceSum += distanceMatrix.getDistance(candidate, notCandidate);
				}
				logBook.put(candidate, distanceSum);
			}
		}

		//Choose the datapoint that minimizes/decreases distanceSum value
		Datapoint choosen = null;
		double distanceMin = Double.MAX_VALUE;

		for (Entry<Datapoint, Double> pair : logBook.entrySet()) {
			double distanceSum = pair.getValue();

			if (distanceSum < distanceMin) {
				distanceMin = distanceSum;
				choosen = (Datapoint) pair.getKey();
			}
		}

		return choosen;
	}
	
}
