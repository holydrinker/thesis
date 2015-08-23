package clustering;

import java.util.HashSet;
import java.util.Set;

import data.Data;
import data.Datapoint;

public class Builder {
	private HashSet<Datapoint> medoids = new HashSet<Datapoint>();
	
	public Builder(short k, Data data) {
		for(int i = 0; i < k; i++){
			Datapoint nextMedoid = getNextMedoid(data);
			this.medoids.add(nextMedoid);
		}
		
	}
	
	Set<Datapoint> compute(){
		return this.medoids;
	}
	
	private Datapoint getNextMedoid(Data data){
		return null;
	}
}
