package data;

import java.util.HashSet;

public class DiscreteFeature extends Feature {
	private HashSet<String> values;
	
	public DiscreteFeature(String name, HashSet<String> values) {
		super(name);
		this.values = values;
	}
	
}
