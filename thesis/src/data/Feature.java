package data;

public abstract class Feature {
	protected String name;
	
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
}
