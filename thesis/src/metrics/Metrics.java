package metrics;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashMap;

public abstract class Metrics {
	protected String groundTruthPath;
	protected String outputPath;
	protected final int GT_X = 0;
	protected final int GT_Y = 1;
	protected final int RES_X = 2;
	protected final int RES_Y = 3;
	
	public Metrics(String groundTruthPath, String outputPath) {
		this.groundTruthPath = groundTruthPath;
		this.outputPath = outputPath;
	}
	
	/*private void generateMappedOutput(String groundTruthPath, String outputPath){
		try {
			BufferedReader gtReader = new BufferedReader(new FileReader(groundTruthPath));
			String nextGtLine = null;
			//Skip row number, col number and feature vector line
			for(int i = 0; i <= 2; i++){
				nextGtLine = gtReader.readLine();
			}
			
			BufferedReader resultReader = new BufferedReader(new FileReader(outputPath));
			//Skip feature vector line and get the first line
			String nextResultLine = null;
			for(int i = 0; i <= 1; i++){
				nextResultLine = resultReader.readLine();
			}
			
			//Set output file path
			BufferedWriter bw = new BufferedWriter(new FileWriter("output/indianpine_real.csv"));
		} catch (Exception e) {
			// TODO: handle exception
		}
	}*/
	
	public abstract double purity();
	
	/**
	 * Quantify the purity into a cluster.
	 */
	protected class PurityQuantifier{
		private HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
		private int count = 0;
		
		public void addInstances(int clusterID){
			if(map.containsKey(clusterID)){
				int occurrences = map.get(clusterID);
				map.remove(clusterID);
				map.put(clusterID, (occurrences+1));
			} else {
				map.put(clusterID, 1);
			}
			count++;
		}
		
		public double quantify(){
			int maxOccurrences = -1;
			
			for(Integer clusterID : map.keySet()){
				int occurrences = map.get(clusterID); 
				if(occurrences > maxOccurrences){
					maxOccurrences = occurrences;
				}
			}
			System.out.println(((double)maxOccurrences) / count);
			return ((double)maxOccurrences) / count;
		}
	}
	
}
