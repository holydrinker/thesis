package sampling;

import java.util.List;

import clustering.Cluster;
import data.Datapoint;

public class SingleClusterQuadSampling extends SingleClusterSampling{
	private Decomposer decomposer;
	
	SingleClusterQuadSampling(double perc) {
		super(perc);
		decomposer = new QuadTreeDecomposer();
	}
	
	@Override
	Cluster compute(Cluster cluster) {
		List<Datapoint> keyPoints = decomposer.computeCentroids(cluster, perc);
		return new Cluster(cluster.getID(), null, keyPoints, null);
	}
	
}
