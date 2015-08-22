
public class Temp {

	public static void main(String[] args) {
		class Inner{}
		
		Inner[][] matrix = new Inner[2][4];

		/*
		matrix[0][0] = 1;
		matrix[0][1] = 2;
		matrix[0][2] = 3;
		matrix[0][3] = 4;
		matrix[1][0] = 5;
		matrix[1][1] = 6;
		matrix[1][2] = 7;
		matrix[1][2] = 8;
		*/
		
		//System.out.println(matrix[0][0] + " " + matrix[0][1] + " " + matrix[0][2]);
		
		for(Inner obj : matrix[0]){
			System.out.println(obj);
		}
		
		
	}

}
