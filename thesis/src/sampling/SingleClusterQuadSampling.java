package sampling;

import java.util.List;

import clustering.Cluster;
import data.Datapoint;

public class SingleClusterQuadSampling extends SingleClusterSampling{
	private final double perc;
	private Decomposer decomposer;
	
	public SingleClusterQuadSampling(double perc) {
		this.perc = perc;
		decomposer = new QuadTreeDecomposer();
	}
	
	@Override
	Cluster compute(Cluster cluster) {
		List<Datapoint> keyPoints = decomposer.computeCentroids(cluster, perc);
		return new Cluster(cluster.getID(), null, keyPoints);
	}
	
}
