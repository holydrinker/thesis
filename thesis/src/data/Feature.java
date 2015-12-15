package data;

public abstract class Feature {
	private String name;
	
	public Feature(String name) {
		this.name = name;
	}
	
	public String getName(){
		return this.name;
	}
	
	@Override
	public boolean equals(Object obj) {
		Feature f = (Feature) obj;
		return this.getName().equals(f.getName());
	}
	
	@Override
	public String toString() {
		return this.name;
	}
}
