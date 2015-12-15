package evaluation;

import java.util.LinkedList;

/**
 * Each record is iserted in the position of the correspondin datapoint's ID.
 * Each rcord save che datapoint's class ID and his cluster ID.
 * @author holydrinker
 *
 */
public class ClusterAssignment {
	private LinkedList<Record> list = new LinkedList<Record>();
	
	public void addRecord(short pointID, short classID){
		list.add(pointID, new Record(classID));
	}

	public void setClusterID(short pointID, short clusterID){
		Record record = list.get(pointID);
		record.setClusterID(clusterID);
	}
	
	public short getClassID(short pointID){
		return this.list.get(pointID).getClassID();
	}
	
	public short getClusterID(short pointID){
		return this.list.get(pointID).getClusterID();
	}
	
	@Override
	public String toString() {
		String result = "";
		for(int i = 0; i < this.list.size(); i++){
			Record record = list.get(i);
			result += "[" + i + " - " + record.toString() + "]\n";
		}
		return result;
	}
	
	private class Record{
		private short classID;
		private short clusterID;

		public Record(short classID) {
			this.classID = classID;
		}
		
		public void setClusterID(short clusterID){
			this.clusterID = clusterID;
		}
		
		public short getClassID(){
			return this.classID;
		}
		
		public short getClusterID(){
			return this.clusterID;
		}
		
		@Override
		public String toString() {
			return this.classID + " - " + this.clusterID;
		}
	} 
	
}
