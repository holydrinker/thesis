package evaluation;

import clustering.ClusterSet;
import data.Data;

public class Metrics extends MetricsA {
	
	public Metrics(ClusterSet clusterSet, Data data) {
		super(clusterSet, data);
	}

	@Override
	protected void setParams(Data data) {
		MetricsMap map = new MetricsMap();
		short p1Class = 0;
		short p2Class = 0;
		short p1Cluster = 0;
		short p2Cluster = 0;
		short dataSize = data.size();

		//Optimized loop
		for (short i = 0; i < dataSize; i++) {
			p1Class = assignm.getClassID(i);
			p1Cluster = assignm.getClusterID(i);
			
			for (short j = (short) (i + 1); j < dataSize; j++) {
				p2Class = assignm.getClassID(j);
				p2Cluster = assignm.getClusterID(j);

				// Update MetricsMap
				if (p1Class == p2Class) {
					if (p1Cluster == p2Cluster) {
						map.add(TP_KEY);
					} else {
						map.add(FN_KEY);
					}
				} else if (p1Class != p2Class) {
					if (p1Cluster != p2Cluster) {
						map.add(TN_KEY);
					} else {
						map.add(FP_KEY);
					}
				}
			}
		}

		System.out.println(map.toString());
		this.TP = map.get(TP_KEY);
		this.TN = map.get(TN_KEY);
		this.FP = map.get(FP_KEY);
		this.FN = map.get(FN_KEY);
	}
}
