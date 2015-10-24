package metrics;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.TreeMap;
import java.util.Map.Entry;
import java.util.LinkedList;

public class Methods {
	private TreeMap<Coord, Integer> groundTruth = new TreeMap<Coord, Integer>();
	private TreeMap<Coord, Integer> classification = new TreeMap<Coord, Integer>();
	
	public Methods(String groundTruthPath, String resultPath) {
		classIndexRefactoring(groundTruthPath, resultPath);
		//initGroundTruth();
		//initClassification(outputFileName);
	}
	
	public double compute(){
		int count = 0;
		int tp = 0;
		
		for(Coord main : classification.keySet()){
			int mainClassification = classification.get(main);
			for(Coord temp : classification.keySet()){
				if(!main.equals(temp)){
					int tempClassification = classification.get(temp);
					
					//mi prendo la stessa coppia dal ground truth
					System.out.println(main);
					System.out.println(temp);
					int mainGround = groundTruth.get(main);
					int tempGround = groundTruth.get(temp);
					
					//faccio la verifica
					if(mainClassification == tempClassification){
						if(mainGround == tempGround){
							tp++;
						}
					} else {
						if(mainGround != tempGround){
							tp++;
						}
					}
					count++;
				}
			}
		}
		
		return tp / count;
	}
	
	private void classIndexRefactoring(String groundTruthPath, String resultPath){
		final int GT_X = 0;
		final int GT_Y = 1;
		final int RES_X = 2;
		final int RES_Y = 3;
		
		try {
			BufferedReader gtReader = new BufferedReader(new FileReader(groundTruthPath));
			String nextGtLine = null;
			//Skip row number, col number and feature vector line
			for(int i = 0; i <= 2; i++){
				nextGtLine = gtReader.readLine();
			}
			
			BufferedReader resultReader = new BufferedReader(new FileReader(resultPath));
			//Skip feature vector line and get the first line
			String nextResultLine = null;
			for(int i = 0; i <= 1; i++){
				nextResultLine = resultReader.readLine();
			}
			
			//Set output file path
			BufferedWriter bw = new BufferedWriter(new FileWriter("output/indianpine_real.csv"));
			
			//LET'S START
			HashMap<Integer, Integer> map = new HashMap<Integer, Integer>(); //temp
			
			int lastResultClusterID = -1;
			int realCluster = -1;
			
			while(nextResultLine != null){
				String[] resultLine = nextResultLine.split(";");
				int currentResultClusterID = Integer.parseInt(resultLine[0]);
				
				//If the cluster id changes, we have to map the new medoid on his grount truth's class
				if(currentResultClusterID != lastResultClusterID){
					lastResultClusterID = currentResultClusterID;
					
					//Set realCluster
					int resX = Integer.parseInt(resultLine[RES_X]);
					int resY = Integer.parseInt(resultLine[RES_Y]);
					System.out.println("medoid id: (" + resultLine[RES_X] + "," + resultLine[RES_Y] + ")"); //temp
					boolean found = false;
					
					while(nextGtLine != null && !found){
						String[] gtLine = nextGtLine.split(",");
						int realX = Integer.parseInt(gtLine[GT_X]);
						int realY = Integer.parseInt(gtLine[GT_Y]);
						
						if(resX == realX && resY == realY){
							realCluster = Integer.parseInt(gtLine[gtLine.length-1]);
							found = true;
							map.put(lastResultClusterID, realCluster);
						}
						
						//If mapping was found, restart the csv iterator
						if(found){
							gtReader.close();
							gtReader = new BufferedReader(new FileReader(groundTruthPath));
							for(int i = 0; i <= 2; i++){
								nextGtLine = gtReader.readLine();
							}
						} else {
							nextGtLine = gtReader.readLine();
						}
						
					}
				}
				nextResultLine = resultReader.readLine();
				
				//Set the real cluster ID
				resultLine[0] = realCluster+"";
				String newLine = "";
				for(String s : resultLine){
					newLine += (s + ";");
				}
				bw.write(newLine);
			}
			
			//Close all.
			gtReader.close();
			resultReader.close();
			bw.close();
			
			//Delete when it works
			for(Entry<Integer, Integer> entry: map.entrySet()){
				System.out.println(entry.getKey() + " -> " + entry.getValue());
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void maximum(String groundTruthPath, String resultPath){
		final int GT_X = 0;
		final int GT_Y = 1;
		final int RES_X = 2;
		final int RES_Y = 3;
		
		try {
			BufferedReader gtReader = new BufferedReader(new FileReader(groundTruthPath));
			String nextGtLine = null;
			//Skip row number, col number and feature vector line
			for(int i = 0; i <= 2; i++){
				nextGtLine = gtReader.readLine();
			}
			
			BufferedReader resultReader = new BufferedReader(new FileReader(resultPath));
			//Skip feature vector line and get the first line
			String nextResultLine = null;
			for(int i = 0; i <= 1; i++){
				nextResultLine = resultReader.readLine();
			}
			
			//Set output file path
			BufferedWriter bw = new BufferedWriter(new FileWriter("output/indianpine_real.csv"));
			
			//LET'S START
			LinkedList<Record> records = new LinkedList<Record>();
			int lastID = Integer.parseInt((nextResultLine.split(";"))[0]); //set last id on the first id in the file
			Record temp = new Record(lastID);
			
			while(nextResultLine != null){
				//Get the id cluster from our classification model
				String[] resultLine = nextResultLine.split(";");
				int currentID = Integer.parseInt(resultLine[0]);
				
				/*
				 * Oh, a new cluster ID appears! 
				 * Save the old record in the records list.
				 * Create a new record for the new clusterID and populate it.
				 */
				if(currentID != lastID){
					temp = new Record(currentID);
				}
				lastID = currentID;
				
				//Set coordinate to map each cluster in his real class id
				int resX = Integer.parseInt(resultLine[RES_X]);
				int resY = Integer.parseInt(resultLine[RES_Y]);
				boolean found = false;

				/*
				 * Looking for the real class id:
				 * for each cluster map each instance of the cluster on his real class.
				 * In the end, for each cluster, switch the cluster id with the real class id the occurs most frequently.  
				 */
				while (nextGtLine != null && !found) {
					String[] gtLine = nextGtLine.split(",");
					int realX = Integer.parseInt(gtLine[GT_X]);
					int realY = Integer.parseInt(gtLine[GT_Y]);

					if (resX == realX && resY == realY) {
						found = true;
						int realClusterID = Integer.parseInt(gtLine[gtLine.length - 1]);
						temp.add(realClusterID);
					}

					// If mapping was found, restart the csv iterator. Else, iterate reading a new line.
					if (found) {
						gtReader.close();
						gtReader = new BufferedReader(new FileReader(groundTruthPath));
						for (int i = 0; i <= 2; i++) {
							nextGtLine = gtReader.readLine();
						}
					} else {
						nextGtLine = gtReader.readLine();
					}
						
					
				}
				nextResultLine = resultReader.readLine();
				
				/*Set the real cluster ID
				resultLine[0] = realCluster+"";
				String newLine = "";
				for(String s : resultLine){
					newLine += (s + ";");
				}
				bw.write(newLine);
				*/
				
				//temp print for testing
				for(Record record : records){
					System.out.println(record.clusterID + "->" + record.getMax().key);
				}
			}
			
			//Close all.
			gtReader.close();
			resultReader.close();
			bw.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	private void initGroundTruth(){
		String filePath = "dataset/indianpine.csv";
		try {
			//Set buffered reader. It have to start from third line.
			BufferedReader br = new BufferedReader(new FileReader(filePath));
			int firstRecordIdx = 2;
			String line = null;
			for(int i = 0; i <= firstRecordIdx; i++){
				line = br.readLine();
			}
			
			//Populate
			String[] split;
			int xIdx = 0;
			int yIdx = 1;
			int classIdx = (line.split(",")).length - 1;
			
			while(line != null){
				split = line.split(",");
				int x = Integer.parseInt(split[xIdx]);
				int y = Integer.parseInt(split[yIdx]);
				int c = Integer.parseInt(split[classIdx]);
				classification.put(new Coord(x, y), c);
				
				line = br.readLine();
			}
			
			br.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}	
	
	public void initClassification(String filePath){
		try {	
			//Set buffered reader. It have to start from third line.
			BufferedReader br = new BufferedReader(new FileReader(filePath));
			int firstRecordIdx = 1;
			String line = null;
			for(int i = 0; i <= firstRecordIdx; i++){
				line = br.readLine();
			}
			
			//Populate
			String[] split;
			int xIdx = 2;
			int yIdx = 3;
			int classIdx = 0;
			
			while(line != null){
				split = line.split(";");
				int x = Integer.parseInt(split[xIdx]);
				int y = Integer.parseInt(split[yIdx]);
				int c = Integer.parseInt(split[classIdx]);
				classification.put(new Coord(x, y), c);
				
				line = br.readLine();
			}
			
			br.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	} 
	
	private class Record{
		private int clusterID;
		private HashMap<Integer, Integer> pairs = new HashMap<Integer, Integer>();
		
		public Record(int clusterID) {
			this.clusterID = clusterID;
		}
		
		public int getClusterID(){
			return this.clusterID;
		}
		
		public void add(int id){
			if(pairs.containsKey(id)){
				int occurrences = pairs.get(id);
				pairs.remove(id);
				pairs.put(id, occurrences);
			}
		}
		
		public Pair getMax(){
			int keyMax = -1;
			int valueMax = -1;
			
			for(Entry<Integer, Integer> pair : pairs.entrySet()){
				int key = pair.getKey();
				int value = pair.getValue();
				
				if(value > valueMax){
					valueMax = value;
					keyMax = key;
				}
			}
			
			return new Pair(keyMax, valueMax);
		}
	}
	
	private class Pair{
		int key;
		int value;
		
		public Pair(int key, int value) {
			this.key = key;
			this.value = value;
		}
	}
	
	private class Coord implements Comparable{
		int x;
		int y;
		
		public Coord(int x, int y) {
			this.x = x;
			this.y = y;
		}

		@Override
		public int compareTo(Object o) {
			Coord coord = (Coord) o;
			if(this.x < coord.x){
				return -1;
			} else if(this.x > coord.x){
				return +1;
			} else {
				if(this.y < coord.y){
					return -1;
				} else if (this.y > coord.y) {
					return +1;
				} else {
					return 0;
				}
			}
		}
		
		@Override
		public boolean equals(Object obj) {
			Coord coord = (Coord) obj;
			return this.compareTo(coord) == 0;
		}
		
		@Override
		public String toString() {
			return "(" + x + "," + y + ")";
		}
		
	}

	
	public static void main(String[] args) {
		String groundTruthPath = "dataset/indianpine.csv";
		String outputFileName = "output/clustering_indianpine_dataset_16_nopca_medoid.csv";
		
		Methods p = new Methods(groundTruthPath, outputFileName);
	}
}
