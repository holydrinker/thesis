package clustering;

import data.Data;
import data.Datapoint;
import distance.Dissimilarity;
import distance.DistanceI;

public class DistanceMatrix {
	private double[][] matrix;
	
	public DistanceMatrix(Data data) {
		//Set matrix bound
		int WIDTH = data.getWidth();
		int HEIGHT = data.getHeight();
		int LENGTH = WIDTH * HEIGHT;
		matrix = new double[LENGTH][LENGTH];
		
		//Populate matrix
		for(Object obj1 : data){
			int x;
			int y;
			
			Datapoint dp1 = (Datapoint)obj1;
			x = dp1.getID();
			
			for(Object obj2 : data){
				Datapoint dp2 = (Datapoint) obj2;
				y = dp2.getID();
				
				if(dp1.equals(dp2)){
					matrix[x][y] = 0;
				} else {
					matrix[x][y] = new Dissimilarity(dp1, dp2).compute();
				}
			}
		}
	}
	
	double getDistance(Datapoint dp1, Datapoint dp2){
		int x = dp1.getID();
		int y = dp2.getID();
		
		return this.matrix[x][y];
	}
	
}
