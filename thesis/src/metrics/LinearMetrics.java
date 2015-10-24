package metrics;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class LinearMetrics extends Metrics {

	public LinearMetrics(String groundTruthPath, String outputPath) {
		super(groundTruthPath, outputPath);
	}

	@Override
	public double purity() {
		double puritySum = 0d;
		int clusterNum = 0;
		
		try {
			BufferedReader gtReader = new BufferedReader(new FileReader(super.groundTruthPath));
			String nextGtLine = null;
			//Skip row number, col number and feature vector line
			for(int i = 0; i <= 2; i++){
				nextGtLine = gtReader.readLine();
			}
			
			BufferedReader resultReader = new BufferedReader(new FileReader(super.outputPath));
			//Skip feature vector line and get the first line
			String nextResultLine = null;
			for(int i = 0; i <= 1; i++){
				nextResultLine = resultReader.readLine();
			}
			
			//LET'S START
			int lastResultClusterID = -1;
			int realClusterID = -1;
			PurityQuantifier purity = null;
			
			while(nextResultLine != null){
				String[] resultLine = nextResultLine.split(";");
				int currentResultClusterID = Integer.parseInt(resultLine[0]);
				
				//If the cluster id changes, we have to create a new BagOfInstances
				if(currentResultClusterID != lastResultClusterID){
					if(purity != null){
						puritySum += purity.quantify();
						clusterNum++;
					}
					purity = new PurityQuantifier();
					lastResultClusterID = currentResultClusterID;
				}
					
				// Get corresponding realCluster
				int resX = Integer.parseInt(resultLine[RES_X]);
				int resY = Integer.parseInt(resultLine[RES_Y]);
				boolean found = false;

				while (nextGtLine != null && !found) {
					String[] gtLine = nextGtLine.split(",");
					int realX = Integer.parseInt(gtLine[GT_X]);
					int realY = Integer.parseInt(gtLine[GT_Y]);

					if (resX == realX && resY == realY) {
						realClusterID = Integer.parseInt(gtLine[gtLine.length - 1]);
						found = true;
						purity.addInstances(realClusterID);
					}

					// If the corresponding instances was found, restare the file reader
					if (found) {
						gtReader.close();
						gtReader = new BufferedReader(new FileReader(groundTruthPath));
						for (int i = 0; i <= 2; i++) 
							nextGtLine = gtReader.readLine();
						
					} else {
						nextGtLine = gtReader.readLine();
					}	
				}

				// Read next line 
				nextResultLine = resultReader.readLine();
			}
				
			// Add last cluster's data
			puritySum += purity.quantify();
			clusterNum++;
			
			//Close io objects.
			gtReader.close();
			resultReader.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		System.out.println("clusterNum: " + clusterNum);
		double purity = puritySum / clusterNum;
		return purity;
	}

}
