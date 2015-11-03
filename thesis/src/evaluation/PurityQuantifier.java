package evaluation;

import java.util.HashMap;

public class PurityQuantifier {
	private HashMap<Integer, Integer> map = new HashMap<Integer, Integer>(); //real class id - occurences
	private int count = 0;
	
	void addOccurrence(Integer classID){
		if(map.containsKey(classID)){
			int occurrences = map.get(classID);
			map.remove(classID);
			map.put((int) classID, (occurrences+1));
		} else {
			map.put((int) classID, 1);
		}
		count++;
	}
	
	double quantify(){
		int maxOccurrences = -1;
		
		for(Integer clusterID : map.keySet()){
			int occurrences = map.get(clusterID); 
			if(occurrences > maxOccurrences){
				maxOccurrences = occurrences;
			}
		}
		double purity = ((double)maxOccurrences) / count;
		return purity;
	}
	
}
