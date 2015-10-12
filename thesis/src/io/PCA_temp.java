package io;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

import weka.core.Instance;
import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Add;
import weka.filters.unsupervised.attribute.PrincipalComponents;
import weka.filters.unsupervised.attribute.Remove;

public class PCA_temp {
	private PrincipalComponents pca = new PrincipalComponents();
	private String fileName;
	private final String relationName; //doc update
	private String dir = "dataset/";
	private HashMap<Integer, Coord> coordMap = new HashMap<Integer, Coord>();
	
	public PCA_temp(String fileName, int maxAttributeNames) {
		this.relationName = fileName;
		this.fileName = fileName + ".arff";
		pca.setMaximumAttributeNames(maxAttributeNames);
	}
	
	public PCA_temp(String fileName) {
		this.relationName = fileName;
		this.fileName = fileName + ".arff";
	}

	public StreamGenerator createStreamGenerator(){
		Instances dataset = null;
		Instances newDataset = null;
		 
		//generate new temp input file
		try {
			//load dataset and save coordinates
			dataset = new Instances(new FileReader(dir + fileName));
			String coordX_name = dataset.attribute(0).name();
			String coordY_name = dataset.attribute(1).name();
			saveCoord(dataset);
			
			//remove coordinates before performing pca
			String[] removeOption = {"-R" , "1-2"};
			Remove remove = new Remove();
			remove.setOptions(removeOption);
			remove.setInputFormat(dataset);
			dataset = Filter.useFilter(dataset, remove);
			
			pca.setInputFormat(dataset);
			newDataset = Filter.useFilter(dataset, pca);
			
			//Renaming
			newDataset.setRelationName(relationName);
			int numAtt = newDataset.numAttributes();
			for(int i = 0; i < numAtt; i++){
				newDataset.renameAttribute(i, "a"+i);
			}
			
			//restore coordinates
			//restoreCoord(newDataset, coordX_name, coordY_name); TODO
			
		} catch (Exception e) {
			e.printStackTrace();
		}    

		//save new temp input file
		String tempPath = dir + "temp_" + fileName; 
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(tempPath));
			writer.write(newDataset.toString());
			writer.flush();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return new StreamGenerator(tempPath);
	}
	
	/**
	 * Save the coordinates into a map
	 * @param dataset
	 */
	private void saveCoord(Instances dataset){
		for(int i = 0; i < dataset.size(); i++){
			Instance instance = dataset.get(i);
			double x = instance.value(0);
			double y = instance.value(1);
			coordMap.put(i, new Coord(x, y));
		}
	}
	
	/**
	 * Re-inject coordinates from the map
	 * @param newDataset
	 * @param coordX_name
	 * @param coordY_name
	 */
	private void restoreCoord(Instances newDataset, String coordX_name, String coordY_name){
		try {
			Add add = new Add();
			add.setAttributeName(coordX_name);
			add.setInputFormat(newDataset);
			newDataset = Filter.useFilter(newDataset, add);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private class Coord{
		double x;
		double y;
		
		public Coord(double x, double y) {
			this.x = x;
			this.y = y;
		}
	}
}
