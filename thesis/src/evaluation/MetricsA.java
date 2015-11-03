package evaluation;

import clustering.ClusterSet;

public abstract class MetricsA {
	public static GroundTruth groundTruth = new GroundTruth();
	protected ClusterSet clusterSet;
	
	protected int TP;
	protected int FP;
	protected int TN;
	protected int FN;
	private String TP_KEY = "TP";
	private String TN_KEY = "TN";
	private String FP_KEY = "FP";
	private String FN_KEY = "FN";

	public MetricsA(ClusterSet clusterSet, int datasetSize) {
		this.clusterSet = clusterSet;
		setParams(datasetSize);
	}
	
	public void setParams(int datasetSize){
		// Start
		short dp1Class = 0;
		short dp2Class = 0;
		short cp1Class = 0;
		short cp2Class = 0;
		SimplyMetricsMap map = new SimplyMetricsMap();
		
		for(short i = 0; i < datasetSize; i++){
			dp1Class = groundTruth.getClassID(i);
			cp1Class = groundTruth.getClusterID(i);
			
			for(short j = (short) (i+1); j < datasetSize; j++){
				dp2Class = groundTruth.getClassID(j);
				cp2Class = groundTruth.getClusterID(j);
				
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
	
	public abstract double purity();
	
	public abstract double randIndex();
	
	public abstract double precision();
	
	public abstract double recall();
	
	public abstract double fScore(double beta);
	
}
