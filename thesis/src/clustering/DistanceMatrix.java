package clustering;

import data.Data;
import data.Datapoint;
import distance.EuclideanDissimilarity;

public class DistanceMatrix {
	private double[][] matrix;
	
	public DistanceMatrix(Data data) {
		/*
		 * MaxHeapSize: 1044381696 
		 * InitialHeapSize: 67108864
		 */
		
		//Set matrix bound
		int WIDTH = data.getWidth();
		int HEIGHT = data.getHeight();
		int LENGTH = WIDTH * HEIGHT;
		matrix = new double[LENGTH][]; //diagonal matrix
		
		/*Populate diagonal matrix*/
		int id1;
		int dim;
		int count;
		int id2;
		
		for(Datapoint dp1 : data){
			id1 = dp1.getID();
			dim = id1+1;
			matrix[id1] = new double[dim]; //alla riga corrispondente all'id del dp in questione creo
			
			count = 0;
			for(Datapoint dp2 : data){
				id2 = dp2.getID();
				
				if(id2 <= id1){
					matrix[id1][id2] = new EuclideanDissimilarity(dp1, dp2).compute();
					count++;
				}
				
				if(count == dim)
					break;
			}
		}
		
	}
	
	double getDistance(Datapoint dp1, Datapoint dp2){
		int x = dp1.getID();
		int y = dp2.getID();
		
		double result;
		try {
			result = this.matrix[x][y];
		} catch (ArrayIndexOutOfBoundsException e) {
			result = this.matrix[y][x];
		}
		
		return result;
	}

}
