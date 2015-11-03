package evaluation;

import java.util.HashMap;
import java.util.Iterator;

public class SimplyMetricsMap implements Iterable<String> {
	private HashMap<String, Integer> map = new HashMap<String, Integer>();
	
	SimplyMetricsMap() {
		map.put("TP", 0);
		map.put("TN", 0);
		map.put("FP", 0);
		map.put("FN", 0);
	}
	
	void add(String key){
		int value = map.get(key);
		map.remove(key);
		map.put(key, value+1);
	}
	
	int get(String key){
		return this.map.get(key);
	}
	
	@Override
	public String toString() {
		return this.map.toString();
	}

	@Override
	public Iterator<String> iterator() {
		return this.map.keySet().iterator();
	}

}
