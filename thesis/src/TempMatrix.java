import java.util.Iterator;

import data.Datapoint;

public class TempMatrix implements Iterable {

	private class Record{
		int value;
		
		public Record(int value) {
			this.value = value;
		}
		
		@Override
		public String toString() {
			return "" + this.value;
		}
	}
	
	Record[][] data = new Record[2][2];
	
	public TempMatrix() {
		data[0][1] = new Record(3);
		data[1][1] = new Record(1);
		data[1][0] = new Record(4);
	}

	@Override
	public Iterator iterator() {
		return new Iterator(){
			int width = data[0].length;
			int height = data.length;
			int x = 0;
			int y = 0;
			
			@Override
			public boolean hasNext() {
				if(x == width){
					x = 0;
					y++;
				}
				
				
				
				if(y == height)
					return false;
				else
					return true;
				
			}

			@Override
			public Object next() {
				return data[y][x++];
			}
		};
		
		
	}
	
	public static void main(String[] args) {
		TempMatrix m = new TempMatrix();
		for(Object obj : m){
			System.out.println(obj);
		}
	}
	
	
	
}
