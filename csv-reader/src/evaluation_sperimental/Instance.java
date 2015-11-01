package evaluation_sperimental;

public class Instance {
	public int id;
	public String type;
	
	public Instance(int id, String type) {
		this.id = id;
		this.type = type;
	}
	
	public Instance(int id) {
		this.id = id;
	}

	@Override
	public boolean equals(Object obj) {
		Instance other = (Instance) obj;
		return this.id == other.id;
	}
	
}
