package evaluation;

import clustering.ClusterSet;

public class Metrics extends MetricsA {
	
	public Metrics(ClusterSet clusterSet, int datasetSize, ClusterAssignment assignm) {
		super(clusterSet, datasetSize, assignm);
	}

	@Override
	public void setParams(int datasetSize) {
		String TP_KEY = "TP";
		String TN_KEY = "TN";
		String FP_KEY = "FP";
		String FN_KEY = "FN";
		
		// Start
		short dp1Class = 0;
		short dp2Class = 0;
		short cp1Class = 0;
		short cp2Class = 0;
		MetricsMap map = new MetricsMap();

		for (short i = 0; i < datasetSize; i++) {
			dp1Class = assignm.getClassID(i);
			cp1Class = assignm.getClusterID(i);

			for (short j = (short) (i + 1); j < datasetSize; j++) {
				dp2Class = assignm.getClassID(j);
				cp2Class = assignm.getClusterID(j);
				
				// Update
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
