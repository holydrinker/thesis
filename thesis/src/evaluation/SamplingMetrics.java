package evaluation;

import clustering.Cluster;
import clustering.ClusterSet;
import data.Data;
import data.Datapoint;

public class SamplingMetrics extends MetricsA{

	public SamplingMetrics(ClusterSet clusterSet, Data data) {
		super(clusterSet, data);
	}

	@Override
	protected void setParams(Data data) {
		ClusterAssignmSampling ass = new ClusterAssignmSampling(data.size());

		// Generate cluster assignment
		for (Cluster cluster : clusterSet) {
			for (Datapoint point : cluster) {
				ass.addRecord(point.getID(), point.getClassID(), point.getClusterID());
			}
		}
		ass.optimize();

		// Start count
		MetricsMap map = new MetricsMap();
		short dp1Class = 0;
		short dp2Class = 0;
		short cp1Class = 0;
		short cp2Class = 0;
		short dataSize = (short) ass.size();

		for (short i = 0; i < dataSize; i++) {
			dp1Class = ass.getClassID(i);
			cp1Class = ass.getClusterID(i);

			for (short j = (short) (i + 1); j < dataSize; j++) {
				dp2Class = ass.getClassID(j);
				cp2Class = ass.getClusterID(j);

				// Update MetricsMap
				if (dp1Class == dp2Class) {
					if (cp1Class == cp2Class) {
						map.add(TP_KEY);
					} else {
						map.add(FN_KEY);
					}
				} else if (dp1Class != dp2Class) {
					if (cp1Class != cp2Class) {
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
