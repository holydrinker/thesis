package data;

import java.util.HashMap;
import java.util.Iterator;

public class GroundTruth implements Iterable<Integer> {
	private HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
	
	public void put(int pointId, int classId){
		if(!map.containsKey(pointId)){
			map.put(pointId, classId);
		}
	}
	
	public Integer getPointClass(int pointID){
		return this.map.get(pointID);
	}

	@Override
	public Iterator<Integer> iterator() {
		return this.map.keySet().iterator();
	}

	public int size(){
		return this.map.size();
	}
	
	@Override
	public String toString() {
		String result = "";
		for(Integer id : this.map.keySet()){
			result += "( " + id + " , " + map.get(id) + " )\n";
		}
		return result;
	}
	
}
