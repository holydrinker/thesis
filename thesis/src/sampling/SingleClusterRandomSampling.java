package sampling;

import java.util.List;
import java.util.LinkedList;
import java.util.Random;

import clustering.Cluster;
import data.Datapoint;

public class SingleClusterRandomSampling extends SingleClusterSampling {

	public SingleClusterRandomSampling(double perc) {
		super(perc);
	}

	@Override
	Cluster compute(Cluster cluster) {

		// Init list
		List<Datapoint> rootList = new LinkedList<Datapoint>();
		for(Datapoint point : cluster){
			rootList.add(point);
		}
		
		// Set theresold
		short theresold = ((Double)(cluster.size() / 100 * super.perc)).shortValue();
		
		// Extract random points e put them into randomList
		List<Datapoint> randomList = new LinkedList<Datapoint>();
		Random r = new Random();
		for(short i = 0; i < theresold; i++){
			int idx = r.nextInt(rootList.size());
			Datapoint point = rootList.get(idx);
			randomList.add(point);
			rootList.remove(idx);
		}
		
		return new Cluster(cluster.getID(), null, randomList, null);
	}

}
