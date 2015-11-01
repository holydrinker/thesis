package evaluation;

public class Pair{
	int id1;
	int id2;
	
	public Pair(int id1, int id2) {
		this.id1 = id1;
		this.id2 = id2;
	}
	
	@Override
	public boolean equals(Object obj) {
		boolean result = false;
		
		Pair other = (Pair) obj;
		if(id1 == other.id1 && id2 == other.id2)
			result = true;
		else if(id1 == other.id2 && id2 == other.id1)
			result = true;
		
		return result;
	}
	
	@Override
	public String toString() {
		return "(" + this.id1 + "," + this.id2 + ")";
	}
}