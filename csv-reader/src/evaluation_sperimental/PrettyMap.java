package evaluation_sperimental;

import java.util.HashMap;
import java.util.Map.Entry;

public class PrettyMap {
	private HashMap<Pair, String> map = new HashMap<Pair, String>();
	
	public void addPair(Pair pair, String value){
		if(!containsKey(pair)){
			this.map.put(pair, value);
		}
	}
	
	public HashMap<String, Integer> count(){
		int TP = 0;
		int TN = 0;
		int FP = 0;
		int FN = 0;
		
		for(Entry<Pair, String> entry : map.entrySet()){
			switch(entry.getValue()){
			case "TP":
				TP++;
				break;
				
			case "TN":
				TN++;
				break;
				
			case "FP":
				FP++;
				break;
				
			case "FN":
				FN++;
				break;
			}
		}
		
		HashMap<String, Integer> result = new HashMap<String, Integer>();
		result.put("TP", TP);
		result.put("TN", TN);
		result.put("FP", FP);
		result.put("FN", FN);
		return result;
	}
	
	private boolean containsKey(Pair key){
		for(Pair pair : this.map.keySet()){
			if(key.equals(pair))
				return true;
		}
		return false;
	}
	
	
	@Override
	public String toString() {
		String result = "";
		for(Pair key : map.keySet()){
			String value = map.get(key);
			result += key.toString() + " -> " + value + " ";
		}
		return result;
	}
	
	public static void main(String[] args) {
		PrettyMap pmap = new PrettyMap();
		Pair pair = new Pair(new Instance(1), new Instance(2));
		System.out.println(pmap.containsKey(pair));
		
		
	}
}
