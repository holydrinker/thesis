package autocorrelation;

import java.util.HashSet;
import java.util.Iterator;
import data.Dataset;

class Neighborhood implements Iterable<Record>{
	private HashSet<Record> istances = new HashSet<Record>(); 
	
	Neighborhood(Dataset data, short x, short y, short radius){
		//get neighborhood's bounds 
		short startX = (short) (x - radius);
		short startY = (short) (y - radius);
		short endX = (short) (x + radius);
		short endY = (short) (y + radius);
		
		//Prevent OutOfBoundsException
		final short MAX_X = (short) (data.datapoints[0].length - 1);
		final short MAX_Y = (short ) (data.datapoints.length - 1);
		
		if(startX < 0)
			startX = 0;
		if(startY < 0)
			startY = 0;
		if(endX > MAX_X)
			endX = MAX_X;
		if(endY > MAX_Y)
			endY = MAX_Y;
		
		//Generate
		for(short col = startY; col <= endY; col++){
			for(short row = startX; row <= endX; row++){
				if(!(col == y && row == x)){
					istances.add(new Record(row, col, data.datapoints[col][row]));
				}
			}
		}
	}

	@Override
	public Iterator<Record> iterator() {
		return istances.iterator();
	}
	
	short size(){
		return (short) (istances.size());
	}
	
}
