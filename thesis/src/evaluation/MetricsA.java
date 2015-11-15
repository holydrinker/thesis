package evaluation;

import clustering.ClusterSet;
import data.Data;
import data.Datapoint;

public abstract class MetricsA {
	protected ClusterSet clusterSet;
	protected ClusterAssignment assignm = new ClusterAssignment();
	
	protected int TP;
	protected int FP;
	protected int TN;
	protected int FN;
	private final String TP_KEY = "TP";
	private final String TN_KEY = "TN";
	private final String FP_KEY = "FP";
	private final String FN_KEY = "FN";

	public MetricsA(ClusterSet clusterSet, Data data) {
		this.clusterSet = clusterSet;
		setParams(data);
	}
	
	public void setParams(Data data){
		// Generate cluster assignment
		for(Datapoint datapoint : data){
			assignm.addRecord(datapoint.getID(), datapoint.getClassID(), datapoint.getClusterID());
		}
		
		// Start count
		MetricsMap map = new MetricsMap();
		short dp1Class = 0;
		short dp2Class = 0;
		short cp1Class = 0;
		short cp2Class = 0;
		short dataSize = data.size();
		
		for(short i = 0; i < dataSize; i++){
			dp1Class = assignm.getClassID(i);
			cp1Class = assignm.getClusterID(i);
			
			for(short j = (short) (i+1); j < dataSize; j++){
				dp2Class = assignm.getClassID(j);
				cp2Class = assignm.getClusterID(j);
				
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
	
	public abstract double purity();
	
	public abstract double randIndex();
	
	public abstract double precision();
	
	public abstract double recall();
	
	public abstract double fScore(double beta);
	
}
