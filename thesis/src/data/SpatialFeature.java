package data;

public class SpatialFeature extends Feature {
	private short value;
	
	public SpatialFeature(String name, short value) {
		super(name);
		this.value = value;
	}
	
	public short getValue(){
		return this.value;
	}

	public void setValue(short value){
		this.value = value;
	}
}
