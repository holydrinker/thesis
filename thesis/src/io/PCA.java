package io;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import weka.core.Attribute;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.OptionHandler;
import weka.filters.AllFilter;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.PrincipalComponents;
import weka.filters.unsupervised.attribute.Remove;
import weka.filters.unsupervised.attribute.PartitionedMultiFilter;

public class PCA {
	private PrincipalComponents pca = new PrincipalComponents();
	private String fileName;
	private final String relationName; //doc update
	private String dir = "dataset/";
	
	public PCA(String fileName, int maxAttributeNames) {
		this.relationName = fileName;
		this.fileName = fileName + ".arff";
		pca.setMaximumAttributeNames(maxAttributeNames);
	}
	
	public PCA(String fileName) {
		this.relationName = fileName;
		this.fileName = fileName + ".arff";
	}

	public StreamGenerator createStreamGenerator(){
		Instances dataset = null;
		Instances newDataset = null;
		 
		//generate new temp input file
		try {
			//load dataset
			dataset = new Instances(new FileReader(dir + fileName));
			
			//Set the coordinates filter
			String filterOptions[] = {"-R" , "1,2" , "-R" , "3-last"};
			Filter coordFilter = null; //maledetto figlio della merda
			
			//Set the pca filter
			pca.setInputFormat(dataset);
			
			//Set the multifilter
			//Filter[] filters = {pca, coordFilter};
			//PartitionedMultiFilter pmf = new PartitionedMultiFilter();
			//pmf.setFilters(filters);
			//pmf.setInputFormat(dataset);
			newDataset = Filter.useFilter(dataset, pca);
			
			//Renaming
			newDataset.setRelationName(relationName);
			int numAtt = newDataset.numAttributes();
			for(int i = 0; i < numAtt; i++){
				newDataset.renameAttribute(i, "a"+i);
			}
			System.out.println("done.");
			
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return new StreamGenerator(tempPath);
	}
	
}
