package clustering;

import java.util.LinkedList;
import java.util.List;

import data.Data;
import data.Datapoint;
import distance.EuclideanDissimilarity;

public class DistanceMatrix {
	public double[][] matrix;
	
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
		
		/*Populate FULL matrix 
		for(Object obj1 : data){
			Datapoint dp1 = (Datapoint)obj1;
			
			for(Object obj2 : data){
				Datapoint dp2 = (Datapoint) obj2;
				int x = dp1.getID();
				int y = dp2.getID();
				matrix[x][y] = new EuclideanDissimilarity(dp1, dp2).compute();
			}
		}*/
		
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
		
		return this.matrix[x][y];
	}
	
	/*--------DA USARE SOLO PER I TEST ----------*/
	public DistanceMatrix(List<Datapoint> points) {
		//Set matrix bound
					
				int LENGTH = points.size() * points.size();
				matrix = new double[LENGTH][]; //diagonal matrix
				
				/*Populate FULL matrix 
				for(Object obj1 : data){
					Datapoint dp1 = (Datapoint)obj1;
					
					for(Object obj2 : data){
						Datapoint dp2 = (Datapoint) obj2;
						int x = dp1.getID();
						int y = dp2.getID();
						matrix[x][y] = new EuclideanDissimilarity(dp1, dp2).compute();
					}
				}*/
				
				/*Populate diagonal matrix*/
				int id1;
				int dim;
				int count;
				int id2;
				
				for(Datapoint dp1 : points){
					id1 = dp1.getID();
					dim = id1+1;
					matrix[id1] = new double[dim]; //alla riga corrispondente all'id del dp in questione creo
					
					count = 0;
					for(Datapoint dp2 : points){
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
	
	
	//Test
	public static void main(String[] args) {
		Datapoint p1 = new Datapoint(0, 2, 3, 4);
		Datapoint p2 = new Datapoint(1, 1, 1, 1);
		Datapoint p3 = new Datapoint(2, 3, 6, 7);
		Datapoint p4 = new Datapoint(3, 9, 6, 7);
		Datapoint p5 = new Datapoint(4, 4, 9, 7);

		
		LinkedList<Datapoint> list = new LinkedList<Datapoint>();
		list.add(p1);
		list.add(p2);
		list.add(p3);
		list.add(p4);
		list.add(p5);
		
		DistanceMatrix matrix = new DistanceMatrix(list);
		//double dissimilarity = matrix.getDistance(p1, p2);
		//System.out.println("dissimilarity: " + dissimilarity);
		
		//Stampa la matrice per vedere se viene diagonale
		double[][] m = matrix.matrix;
		int row = m.length;
		
		for(int i = 0 ; i < row; i++){
			int col = m[i].length;
			
			for(int j = 0; j < col; j++){
				System.out.print(m[i][j] + " ");
			}
			System.out.println("");
		}
		
	}
	
	
}
