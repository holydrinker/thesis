package io;

import data.*;

public class Tester {
	
	public static void main(String[] args) {
		StreamGenerator sg = new StreamGenerator("lol.txt");
		
		FeatureVector fv = new FeatureVector(sg.getFeatureVectorTO());
		
		for(Feature f : fv)
			System.out.println(f.getName());
	}
}
