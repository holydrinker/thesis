import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Convert {

	public static void main(String[] args) {
		String fileName = "indianpine"; //SET HERE THE NAME OF YOUR INPUT FILE
		String inputPath = "input/" + fileName + ".csv";
		String outputPath = "output/" + fileName + ".arff";
		
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(outputPath));
			bw.write("@relation " + fileName);
			bw.newLine();
			
			BufferedReader br = new BufferedReader(new FileReader(inputPath));
			br.readLine();
			br.readLine();
			
			//feature vector
			System.out.print("Feature vector...");
			String line = br.readLine();
			String[] split = line.split(",");
			generateFeatureVector(split.length, bw);
			System.out.println("ok\n");
			
			//data
			System.out.print("Data...");
			bw.write("@data");
			bw.newLine();
			
			while(line != null){
				System.out.println(line);
				bw.write(line);
				bw.newLine();
				
				line = br.readLine();
			}
			System.out.println("ok\n");

			
			//close io tool
			br.close();
			bw.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	private static void generateFeatureVector(int numAttributes, BufferedWriter bw) throws IOException{
		bw.write("@attribute coord_X numeric");
		bw.newLine();
		
		bw.write("@attribute coord_Y numeric");
		bw.newLine();
		
		for(int i = 0; i < numAttributes-3; i++){ //ignore coordinates and last att (class)
			bw.write("@attribute " + "a" + (i+1) + " numeric");
			bw.newLine();
		}
	}

}
