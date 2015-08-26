import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import data.Datapoint;
import distance.EuclideanDissimilarity;

public class Temp {

	public static void main(String[] args) {
		
		try {
			PrintWriter writer = new PrintWriter("D:/output.txt", "UTF-8");
			writer.println("The first line");
			writer.println("The second line");
			writer.close();;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
