package sampling;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import clustering.Cluster;
import data.Datapoint;

public class QuadTreeDecomposer extends Decomposer {
	private static int idCode = 0;
	
	private class QUADTREESplit implements Comparable<QUADTREESplit>{
		int beginExampleIndex;
		int endExampleIndex;
		MBR mbr;
		int depth;
		int father;
		Set<QUADTREESplit> children=new TreeSet<QUADTREESplit>();
		int id=idCode++;
		Set<Datapoint> mbrData;
		
		QUADTREESplit(Set<Datapoint> mbrData,	int beginExampleIndex, int endExampleIndex, MBR mbr, int depth, int father){
			this.mbr =mbr;
			this.beginExampleIndex=beginExampleIndex;
			this.endExampleIndex=endExampleIndex;
			this.mbrData=mbrData;
			this.depth=depth;
			this.father=father;
	}
	
	
		public String toString(){
			return id+ " father:"+father+ " numExample:"+(endExampleIndex-beginExampleIndex+1);
		}
		
		@Override
		public int compareTo(QUADTREESplit o) {
			if(endExampleIndex-beginExampleIndex+1<=o.endExampleIndex-o.beginExampleIndex+1) 
				return 1; 
						else return -1;
		}
	}

	@Override
	List<Datapoint> computeCentroids(Cluster cluster, double perc) {
		List<Datapoint> centroid = new LinkedList<Datapoint>();
		int beginExampleIndex = 0;
		int endExampleIndex = cluster.size() - 1;
		MBR mbr = cluster.computeMBR(0, cluster.size() - 1);

		// initialize set of examples spatially contained in the mbr.
		TreeSet<Datapoint> mbrData = new TreeSet<Datapoint>();
		for (Datapoint point : cluster) {
			mbrData.add(point);
		}

		int numCentroids=(int)((endExampleIndex-beginExampleIndex+1)*perc);
		if(perc<1.0f){
			numCentroids+=1;
		}
		
		Map<Integer, Set<QUADTREESplit>> quadList = new HashMap<Integer, Set<QUADTREESplit>>();
		Set<QUADTREESplit> set = new TreeSet<QUADTREESplit>();
		set.add(new QUADTREESplit(mbrData, beginExampleIndex, endExampleIndex, mbr, 0, -1));
		quadList.put(0, set);

		populateQUADTREE(cluster, centroid, quadList, numCentroids);

		return centroid;
	}
	
	private void populateQUADTREE(Cluster cluster, List<Datapoint> centroid, Map<Integer,Set<QUADTREESplit>> quad, int numCentroids){
		int first = 0;

		while( centroid.size() + quad.get(first).size() < numCentroids ){			
			if(first > 0 && quad.get(first).size() == quad.get(first-1).size())
				break;
						
			Set<QUADTREESplit> childQuadSet = new TreeSet<QUADTREESplit>();
			quad.put(first+1, childQuadSet); //nuovo livello di quadtree da popolare
			Set<QUADTREESplit> currentQuadSet = quad.get(first); //set MBR livello 0
			
			for(QUADTREESplit currentQuad:currentQuadSet){
				MBR mbr = currentQuad.mbr;
				int beginExampleIndex = currentQuad.beginExampleIndex;
				int endExampleIndex = currentQuad.endExampleIndex;
				Set<Datapoint> mbrData = currentQuad.mbrData;
				int currentDepth = currentQuad.depth;
				
				if(mbr.getCardinality()==1) {
					quad.get(first+1).add(currentQuad);
					currentQuad.children.add(currentQuad);
				}
				else {
					//decompongo i quadrati
					MBR mbr1 = null, mbr2 = null, mbr3 = null, mbr4 = null;
					Set<Datapoint> mbrData1 = null, mbrData2 = null, mbrData3 = null, mbrData4 = null;
					boolean q1 = false, q2 = false, q3 = false, q4 = false;
					double cX = mbr.getCentreX();
					double cY = mbr.getCentreY();
					
					final int X_INDEX = 0;
					final int Y_INDEX = 1;
					
					// sorting on X
					cluster.sort(X_INDEX, beginExampleIndex, endExampleIndex);
					int splitX = endExampleIndex;
					int splitY1 = -1, splitY2 = -1;
					for(int i = beginExampleIndex; i <= endExampleIndex; i++){
						if(cluster.getDatapoint(i).getSpatialFeature(X_INDEX) > cX){
							splitX=i-1;
							break;
						}
					}
					
					//sorting on Y
					if(splitX >= beginExampleIndex){//esistono Q1 e Q3
						cluster.sort(Y_INDEX, beginExampleIndex, splitX);
						q1=true;
						q3=true;
					
						splitY1 = splitX;
					
						for(int i = beginExampleIndex; i <= splitX; i++)
							if (cluster.getDatapoint(i).getSpatialFeature(Y_INDEX) > cY){
								splitY1=i-1;
								break;
						}
						
						if(splitY1==splitX){ // non esiste q1, ma un unico quadrante che unisce q1 e q3
							q1 = false;
							mbr3 = cluster.computeMBR(beginExampleIndex,splitY1); //mbr3=new MBR(mbr.getMinX(),mbr.getMinY(),cX,cY,splitY1-beginExampleIndex+1);
							mbrData3=new TreeSet<Datapoint>();
							for(int i=beginExampleIndex;i<=splitY1;i++)
								mbrData3.add(cluster.getDatapoint(i));
						}
						else if(splitY1==-1)// non esiste q3
						{
							q3 = false;
							mbr1 = cluster.computeMBR(splitY1+1, splitX);//mbr1=new MBR(mbr.getMinX(), cY, cX, mbr.getMaxY(), splitX-splitY1+1);
							mbrData1=new TreeSet<Datapoint>();
							for(int i = splitY1+1; i <= splitX; i++)
								mbrData1.add(cluster.getDatapoint(i));
						}
						else{			
							mbr1 = cluster.computeMBR(splitY1+1, splitX);//mbr1=new MBR(mbr.getMinX(), cY, cX, mbr.getMaxY(), splitX-splitY1+1);
							mbr3 = cluster.computeMBR(beginExampleIndex,splitY1); //mbr3=new MBR(mbr.getMinX(),mbr.getMinY(),cX,cY,splitY1-beginExampleIndex+1);
							mbrData1 = new TreeSet<Datapoint>();
							mbrData3 = new TreeSet<Datapoint>();
							for(int i=beginExampleIndex;i<=splitY1;i++)
								mbrData3.add(cluster.getDatapoint(i));
						
							for(int i=splitY1+1;i<=splitX;i++)
								mbrData1.add(cluster.getDatapoint(i));
						}
					}
					
					if (splitX + 1 <= endExampleIndex){ //esistono Q2 ae Q4
						q2 = true;
						q4 = true;
					
						cluster.sort(Y_INDEX, splitX+1, endExampleIndex);
						splitY2 = endExampleIndex;
						for(int i = splitX+1; i <= endExampleIndex; i++){
							if(cluster.getDatapoint(i).getSpatialFeature(Y_INDEX) > cY){
								splitY2=i-1;
								break;
							}
						}
						if(splitY2 == endExampleIndex) // non esiste q2, ma un unico quadrante che unisce q2 e q4
						{
							q2 = false;
							mbr4 = cluster.computeMBR(splitX+1, splitY2); //mbr4=new MBR(cX, mbr.getMinY(), mbr.getMaxX(), cY, splitY2-splitX+1);
							mbrData4 = new TreeSet<Datapoint>();
							for(int i = splitX+1; i <= splitY2; i++)
								mbrData4.add(cluster.getDatapoint(i));
						}
						else if(splitY2==splitX) //non esiste q4
						{
							q4 = false;
							mbr2 = cluster.computeMBR(splitY2+1, endExampleIndex);
							mbrData2=new TreeSet<Datapoint>();
							for(int i=splitY2+1;i<=endExampleIndex;i++)
								mbrData2.add(cluster.getDatapoint(i));
							
						}
						else{ //esiste q2 e q4
							mbr2 = cluster.computeMBR(splitY2+1, endExampleIndex); //mbr2=new MBR(cX, cY, mbr.getMaxX(), mbr.getMaxY(), endExampleIndex-splitY2+1);
							mbr4 = cluster.computeMBR(splitX+1, splitY2); //mbr4=new MBR(cX, mbr.getMinY(), mbr.getMaxX(), cY, splitY2-splitX+1);
							mbrData2 = new TreeSet<Datapoint>();
							for(int i = splitY2+1; i <= endExampleIndex; i++)
								mbrData2.add(cluster.getDatapoint(i));
						
							mbrData4=new TreeSet<Datapoint>();
							for(int i = splitX+1; i <= splitY2; i++)
								mbrData4.add(cluster.getDatapoint(i));
						}
					}
				
					//Se i punti di mbrData non appartengono al cluster in esame
					for(Datapoint point: mbrData){
						if(!cluster.contains(point)) {
						//examples not falling in the current leaf (these examples haven't  be sorted again during the mbr decomposition)
							double sX = point.getX();
							double sY = point.getY();
							
							if(q1 && sX>=mbr1.getMinX() && sX<=mbr1.getMaxX() && sY>=mbr1.getMinY() && sY<=mbr1.getMaxY())
								mbrData1.add(point);
							else if(q2 && sX>=mbr2.getMinX() && sX<=mbr2.getMaxX() && sY>=mbr2.getMinY() && sY<=mbr2.getMaxY())
								mbrData2.add(point);
							else if(q3 && sX>=mbr3.getMinX() && sX<=mbr3.getMaxX() && sY>=mbr3.getMinY() && sY<=mbr3.getMaxY())
								mbrData3.add(point);
							else if(q4 && sX>=mbr4.getMinX() && sX<=mbr4.getMaxX() && sY>=mbr4.getMinY() && sY<=mbr4.getMaxY())
								mbrData4.add(point);
							
						}
					}//end for
					
					if(q3){
							QUADTREESplit sp=new QUADTREESplit( mbrData3, beginExampleIndex, splitY1,mbr3,currentDepth+1,currentQuad.id);
							quad.get(first+1).add(sp);
							currentQuad.children.add(sp);
						
					}
					//populateQUADTREE(schema, centroid, mbrData3, beginExampleIndex, splitY1,mbr3,currentDepth+1,maxDepth);
					if(q1){
					
						QUADTREESplit sp=(new QUADTREESplit(mbrData1, splitY1+1, splitX,mbr1,currentDepth+1,currentQuad.id));
						quad.get(first+1).add(sp);
						currentQuad.children.add(sp);
						
					}
					if(q4){
						QUADTREESplit sp=(new QUADTREESplit(mbrData4, splitX+1, splitY2,mbr4,currentDepth+1,currentQuad.id));
						quad.get(first+1).add(sp);
						currentQuad.children.add(sp);
					}
					if(q2){
						QUADTREESplit sp=(new QUADTREESplit(mbrData2, splitY2+1, endExampleIndex,mbr2,currentDepth+1,currentQuad.id));
						quad.get(first+1).add(sp);
						currentQuad.children.add(sp);
					}
					}
						
				}
			first++;	
		}
		
		if (quad.get(first).size() == numCentroids) {
			for (QUADTREESplit singleQuad : quad.get(first)) {
				Datapoint centre = singleQuad.mbr.determineCentre(cluster, singleQuad.beginExampleIndex, singleQuad.endExampleIndex);
				centroid.add(centre);
			}
		} else {
			// i centroidi sono tra i livelli first-1 e first in [first-1] i
			// quadranti sono ordinati per cardinalità

			int numQuadrantPrevious = quad.get(first - 1).size();
			boolean flag = true;
			for (QUADTREESplit sp : quad.get(first - 1)) {
				if (flag) {
					// aggiungo i centroidi dei figli di sp
					for (QUADTREESplit childSp : sp.children) {
						Datapoint centre = childSp.mbr.determineCentre(cluster, childSp.beginExampleIndex, childSp.endExampleIndex);
						centroid.add(centre);
					}
					if (centroid.size() + numQuadrantPrevious - 1 >= numCentroids)
						flag = false;

				} else {
					// aggiungo il centroide del nodo corrente
					Datapoint centre = sp.mbr.determineCentre(cluster, sp.beginExampleIndex, sp.endExampleIndex);
					centroid.add(centre);
				}
				numQuadrantPrevious--; // ne ho processato uno
			}

		}
	}
}
