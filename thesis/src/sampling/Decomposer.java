package sampling;

import java.util.List;

import clustering.Cluster;
import data.Datapoint;

public abstract class Decomposer {
	
	abstract List<Datapoint> computeCentroids(Cluster cluster, double perc);

}
