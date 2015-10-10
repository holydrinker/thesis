import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/*
 * Classe utilizzata solo per fare delle prove
 */
public class Temp {
	
	public static void main(String[] args) {
		BufferedReader br;
		try {
			
			br = new BufferedReader(new FileReader("dataset/prova.txt"));
			String line = br.readLine();
			System.out.println(line);
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}
