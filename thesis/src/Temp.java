import java.io.FileNotFoundException;
import java.io.PrintWriter;

/*
 * Classe utilizzata solo per fare delle prove
 */
public class Temp {
	
	public static void main(String[] args) {
		String[] features = {"feature1" ,  "feauture2" , "feaure3" , "feature4"};
		int[] datapoint1 = {11, 12, 13, 14};
		int[] datapoint2 = {21, 22, 23, 24};
		int[] datapoint3 = {31, 32, 33, 34};
		
		int[][] datapoints = {datapoint1, datapoint2, datapoint3};
		
		//stampiamo su csv questi cosi
		try {
			PrintWriter pw = new PrintWriter("D:/clusterOutput.csv");
			String nextLine = "";
			
			for(String feature : features){
				nextLine += (feature + ";");
			}
			nextLine = nextLine.substring(0, nextLine.length()-1);
			pw.println(nextLine);
			
			for(int[] datapoint : datapoints){
				nextLine = "";
				for(int p : datapoint){
					nextLine += (p + ",");
				}
				nextLine = nextLine.substring(0, nextLine.length() - 1);
				pw.println(nextLine);
			}
			
			pw.close();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
