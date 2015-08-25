package clustering;

import java.util.LinkedList;
import java.util.List;

import data.Data;
import data.Datapoint;
import distance.EuclideanDissimilarity;

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
			Datapoint dp1 = (Datapoint)obj1;
			
			for(Object obj2 : data){
				Datapoint dp2 = (Datapoint) obj2;
				int x = dp1.getID();
				int y = dp2.getID();
				matrix[x][y] = new EuclideanDissimilarity(dp1, dp2).compute();
			}
		}
	}
	
	double getDistance(Datapoint dp1, Datapoint dp2){
		int x = dp1.getID();
		int y = dp2.getID();
		
		return this.matrix[x][y];
	}
	
	/*--------DA USARE SOLO PER I TEST ----------*/
	public DistanceMatrix(List<Datapoint> points) {
		int n = points.size();
		this.matrix = new double[n][n];
		
		for(int i = 0; i < n; i++){
			Datapoint dp1 = points.get(i);
			for(int j = 0; j < n; j++){
				Datapoint dp2 = points.get(j); 
				int x = dp1.getID();
				int y = dp2.getID();
				matrix[x][y] = new EuclideanDissimilarity(dp1, dp2).compute();
			}
		}
	}
	
	
	//Test
	public static void main(String[] args) {
		Datapoint p1 = new Datapoint(0, 2, 3, 4);
		Datapoint p2 = new Datapoint(1, 1, 1, 1);
		Datapoint p3 = new Datapoint(2, 4, 6, 7);
		
		LinkedList<Datapoint> list = new LinkedList<Datapoint>();
		list.add(p1);
		list.add(p2);
		list.add(p3);
		
		DistanceMatrix matrix = new DistanceMatrix(list);
		double dissimilarity = matrix.getDistance(p1, p2);
		System.out.println("dissimilarity: " + dissimilarity);
	}
	
	
}
