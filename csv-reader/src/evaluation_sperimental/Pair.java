package evaluation_sperimental;

public class Pair{
	Instance a;
	Instance b;
	
	public Pair(Instance a, Instance b) {
		this.a = a;
		this.b = b;
	}
	
	@Override
	public boolean equals(Object obj) {
		boolean result = false;
		
		Pair other = (Pair) obj;
		if(this.a.equals(other.a) && this.b.equals(other.b))
			result = true;
		else if(this.a.equals(other.b) && this.b.equals(other.a))
			result = true;
		
		return result;
	}
	
	@Override
	public String toString() {
		return "(" + this.a + "," + this.b + ")";
	}
}