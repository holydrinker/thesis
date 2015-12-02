package io;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.LinkedList;

import data.*;
import exception.InputFileException;

public class StreamGenerator {
	private FeatureVectorTO fvTO;
	private DataTO dataTO;
	private BufferedReader br;
	
	public StreamGenerator(String filePath) {
		LinkedList<Object> fvParams = new LinkedList<Object>();
		LinkedList<Object> dataParams = new LinkedList<Object>();
		
		int attributeCount = 0;
		String line;
		String[] splittedLine;
		
		try {
			br = new BufferedReader(new FileReader(filePath));
			line = br.readLine();
			
			while(line != null){
				splittedLine = line.split(" ");
				String firstWord = splittedLine[0];
				
				if(firstWord.equalsIgnoreCase("@attribute")){
					//omit first and second attribute (x,y)
					if(attributeCount > 1){
						String name = splittedLine[1];
						String category = splittedLine[2];
						
						if(category.equalsIgnoreCase("numeric")){
							fvParams.add(new ContinueFeature(name));
						} else if(category.equalsIgnoreCase("discrete")){
							String values = splittedLine[splittedLine.length-1];
							values = values.substring(1, values.length() - 1);
							String[] splittedValues = values.split(",");
							
							HashSet<String> set = new HashSet<String>();
							for(String s : splittedValues)
								set.add(s);
							
							fvParams.add(new DiscreteFeature(name, set));
						}
					}
						
					line = br.readLine();
					attributeCount++;
					
				} else if(firstWord.equalsIgnoreCase("@data")) {
					line = br.readLine();
					
					while(line != null){
						if(!line.equalsIgnoreCase("")){
							splittedLine = line.split(",");
							LinkedList<Object> dpParams = new LinkedList<Object>();
							
							for(String s : splittedLine){
								dpParams.add(Double.parseDouble(s));
							}
							dataParams.add(new DatapointTO(dpParams));
						}
						line = br.readLine();
					}
					
				} else if(firstWord.equalsIgnoreCase("@relation") || firstWord.equalsIgnoreCase("")){
					line = br.readLine();
					
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
