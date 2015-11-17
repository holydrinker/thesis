package sampling;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import clustering.Cluster;
import data.Datapoint;
import io.DatapointTO;

public class ClusterRebuilder {

	public Cluster compute(){
		Cluster cluster = null;
		
		try {
			BufferedReader br = new BufferedReader(new FileReader("../output/indianpine_dataset_16_nopca.csv"));
			String line = br.readLine();
			line = br.readLine(); //skip the feature vector line
			String splitLine[] = line.split(";");
			int oldClusterID = -1;
			int clusterID = Integer.parseInt(splitLine[0]);
			boolean stop = false;
			
			List<Datapoint> list = new LinkedList<Datapoint>();
			while(line != null && !stop){
				int pointID = Integer.parseInt(splitLine[1]);
				
				LinkedList<Object> values = new LinkedList<Object>();
				for(int i = 2; i < splitLine.length; i++){
					double value = Double.parseDouble(splitLine[i]);
					values.add(value);
				}
				
				Datapoint point = new Datapoint((short)pointID, new DatapointTO(values));
				list.add(point);
				
				//check stop
				line = br.readLine();
				splitLine = line.split(";");
				oldClusterID = clusterID;
				clusterID = Integer.parseInt(splitLine[0]);
				if(clusterID != oldClusterID){
					stop = true;
				}
			}
			
			br.close();
			cluster = new Cluster((short) oldClusterID, null, list);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return cluster;
	}
	
}
