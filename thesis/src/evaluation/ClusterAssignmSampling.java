package evaluation;

/**
 * Each record is iserted in the position of the correspondin datapoint's ID.
 * Each rcord save che datapoint's class ID and his cluster ID.
 * @author holydrinker
 *
 */
public class ClusterAssignmSampling {
	private Record list[];
	private Record[] optList;
	
	public ClusterAssignmSampling(int datasize) {
		list = new Record[datasize];
	}
	
	public int size(){
		return this.optList.length;
	}
	
	public void addRecord(short pointID, short classID, short clusterID){
		list[pointID] = new Record(classID, clusterID);
	}

	public short getClassID(short index){
		return list[index].getClassID();
	}
	
	public short getClusterID(short index){
		return list[index].getClusterID();
	}
	
	void optimize(){
		int size = 0;
		for(int i = 0; i < list.length; i++){
			if(list[i] != null){
				size++;
			}
		}
		
		Record[] optList = new Record[size];
		int idx = 0;
		for(int i = 0; i < list.length; i++){
			if(list[i] != null){
				optList[idx++] = list[i];
			}
		}
		this.optList = optList;
	}
	
	@Override
	public String toString() {
		String result = "";
		for(int i = 0; i < this.list.length; i++){
			Record record = list[i];
			result += "[" + i + " - " + record.toString() + "]\n";
		}
		return result;
	}
	
	private class Record{
		private short classID;
		private short clusterID;

		public Record(short classID, short clusterID) {
			this.classID = classID;
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
