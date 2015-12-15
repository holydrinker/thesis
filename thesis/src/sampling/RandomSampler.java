package sampling;

import java.util.List;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Random;

import data.Data;
import data.Datapoint;

public class RandomSampler {
	private double perc;
	
	public RandomSampler(double perc) {
		this.perc = perc;
	}
	
	public void generateTrainingSet(Data data){
		short n = ((Double)(data.size() * perc)).shortValue();
		
		// Build root list
		List<Datapoint> rootList = new LinkedList<Datapoint>();
		for(Datapoint point : data){
			rootList.add(point);
		}
		
		// Extract random point from rootList and put them into randomList
		List<Datapoint> randomList = new LinkedList<Datapoint>();
		Random r = new Random();
		
		for(short i = 0; i < n; i++){
			int index = r.nextInt(rootList.size());
			Datapoint point = rootList.get(index);
			randomList.add(point);
			rootList.remove(index);
		}
		
		exportCSV(randomList);
	}
	
	private void exportCSV(List<Datapoint> randomList){
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter("../dataset/randomTrainingSet_sampling" + perc + ".csv"));
			bw.write("pointID;classID;coord_X;coord_Y");
			bw.newLine();
			
			for(Datapoint point : randomList){
				short pointID = point.getID();
				short classID = point.getClassID();
				short x = point.getX();
				short y = point.getY();
				
				bw.write(pointID + ";" + classID + ";" + x + ";" + y);
				bw.newLine();
			}
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
