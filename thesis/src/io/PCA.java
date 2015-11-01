package io;
import java.io.BufferedWriter;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import weka.core.Attribute;
import weka.core.Instance;
import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.PrincipalComponents;
import weka.filters.unsupervised.attribute.Remove;

public class PCA {
	private PrincipalComponents pca = new PrincipalComponents();
	private String fileName;
	private final String relationName; //doc update
	private String dir = "dataset/";
	private ArrayList<Coord> coordList = new ArrayList<Coord>();
	
	public PCA(String fileName, int maxAttributeNames) {
		this.relationName = fileName;
		this.fileName = fileName + ".arff";
		pca.setMaximumAttributeNames(maxAttributeNames);
	}
	
	public PCA(String fileName) {
		this.relationName = fileName;
		this.fileName = fileName + ".arff";
	}

	/**
	 * Generate a new arff file in the directory /dataset
	 */
	public void createTempArff(){
		Instances dataset = null;
		Instances newDataset = null;
		 
		//generate new temp input file
		try {
			//load dataset and save coordinates
			dataset = new Instances(new FileReader(dir + fileName)); //qui muore. nè da eccezione nè niente
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
				newDataset.renameAttribute(i, "a"+(i+1));
			}
			
			//restore coordinates
			restoreCoord(newDataset, coordX_name, coordY_name); 
			
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
	}
	
	/**
	 * Save the coordinates into this.map
	 * @param dataset
	 */
	private void saveCoord(Instances dataset){
		for(int i = 0; i < dataset.size(); i++){
			Instance instance = dataset.get(i);
			double x = instance.value(0);
			double y = instance.value(1);
			coordList.add(i, new Coord(x,y));
		}
	}
	
	/**
	 * Re-inject coordinates from the this.map
	 * @param newDataset
	 * @param coordX_name
	 * @param coordY_name
	 */
	private void restoreCoord(Instances newDataset, String coordX_name, String coordY_name){
		try {
			Attribute x = new Attribute(coordX_name);
			Attribute y = new Attribute(coordY_name);
			newDataset.insertAttributeAt(y, 0);
			newDataset.insertAttributeAt(x, 0);
			
			int i = 0;
			for(Instance inst : newDataset){
				Coord coord = this.coordList.get(i);
				inst.setValue(0, coord.x);
				inst.setValue(1, coord.y);
				i++;			
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private class Coord{
		Double x;
		Double y;
		
		public Coord(Double x, Double y) {
			this.x = x;
			this.y = y;
		}
	}
}
