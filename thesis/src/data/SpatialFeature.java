package data;

public class SpatialFeature extends Feature {
	private short value;
	
	public SpatialFeature(String name, short value) {
		super(name);
		this.value = value;
	}

	short getValue(){
		return this.value;
	}
	
	void setValue(short value){
		this.value = value;
	}
	
}
