package io;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import data.Data;
import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.PrincipalComponents;

public class PCA {
	private PrincipalComponents pca = new PrincipalComponents();
	private String fileName;
	private String dir = "dataset/";
	
	public PCA(String fileName, int maxAttributeNames) {
		this.fileName = fileName + ".arff";
		pca.setMaximumAttributeNames(maxAttributeNames);
	}
	
	public PCA(String fileName) {
		this.fileName = fileName + ".arff";
	}

	public StreamGenerator createStreamGenerator(){
		Instances instances = null;
		Instances newInstances = null;
		 
		//generate new temp input file
		try {
			instances = new Instances(new FileReader(dir + fileName));
			pca.setInputFormat(instances);
			newInstances = Filter.useFilter(instances, pca);
		} catch (Exception e) {
			e.printStackTrace();
		}    

		//save new temp input file
		String tempPath = dir + "temp_" + fileName; 
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(tempPath));
			writer.write(newInstances.toString());
			writer.flush();
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return new StreamGenerator(tempPath);
	}
	
}
