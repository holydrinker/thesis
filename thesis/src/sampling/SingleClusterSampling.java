package sampling;

import clustering.Cluster;

public abstract class SingleClusterSampling {
	protected final double perc;
	
	public SingleClusterSampling(double perc) {
		this.perc = perc;
	}
	
	abstract Cluster compute(Cluster cluster);
	
}
