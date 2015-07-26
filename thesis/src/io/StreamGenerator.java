package io;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.LinkedList;

import data.*;

public class StreamGenerator {
	private FeatureVectorTO fvTO;
	private DataTO dataTO;
	private BufferedReader br;
	
	public StreamGenerator(String fileName) {
		LinkedList<Object> fvParams = new LinkedList<Object>();
		LinkedList<Object> dataParams = new LinkedList<Object>();
		
		String line;
		String[] splittedLine;
		
		try {
			br = new BufferedReader(new FileReader("D:/dataset/"+fileName));
			line = br.readLine();
			
			while(line != null){
				splittedLine = line.split(" ");
				String firstWord = splittedLine[0];
				
				if(firstWord.equalsIgnoreCase("@attribute")){
					String name = splittedLine[1];
					String category = splittedLine[2];
					
					if(category.equalsIgnoreCase("numeric"))
						fvParams.add(new ContinueFeature(name));
					else if(category.equalsIgnoreCase("discrete"))
						fvParams.add(new DiscreteFeature(name, new HashSet<String>())); //how can i pass the right hashset? :\
					
					line = br.readLine();
					
				} else if(firstWord.equalsIgnoreCase("@data")) {
					line = br.readLine();
					
					while(line != null){
						splittedLine = line.split(",");
						LinkedList<Object> dpParams = new LinkedList<Object>();
						
						for(String s : splittedLine)
							dpParams.add(Double.parseDouble(s));
						dataParams.add(new DatapointTO(dpParams));
						
						line = br.readLine();
					}
					
				} else {
					throw new InputFileException();
				}
			
			}
				
			fvTO = new FeatureVectorTO(fvParams);
			dataTO = new DataTO(dataParams);
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			
		} catch (IOException e){
			e.printStackTrace();
		}
	}
	
	public FeatureVectorTO getFeatureVectorTO(){
		return this.fvTO;
	}
	
	public DataTO getDataTO(){
		return this.dataTO;
	}

}
