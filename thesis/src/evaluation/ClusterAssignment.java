package evaluation;

/**
 * Each record is inserted in the position of the correspondin datapoint's ID.
 * Each rcord save che datapoint's class ID and his cluster ID.
 * @author holydrinker
 *
 */
public class ClusterAssignment {
	private Record[] rlist;
	
	public ClusterAssignment(int size) {
		rlist = new Record[size];
	}
	
	public void addRecord(short pointID, short classID, short clusterID){
		rlist[pointID] = new Record(classID, clusterID);
	}

	public short getClassID(short pointID){
		return this.rlist[pointID].getClassID();
	}
	
	public short getClusterID(short pointID){
		return this.rlist[pointID].getClusterID();
	}
	
	public int size(){
		return this.rlist.length;
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
