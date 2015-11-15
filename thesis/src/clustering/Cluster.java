package clustering;

import java.util.Iterator;
import java.util.List;

import data.Datapoint;
import sampling.MBR;

public class Cluster implements Iterable<Datapoint>{
	private short id;
	private Datapoint medoid;
	private List<Datapoint> data;
	
	public Cluster(short clusterID, Datapoint medoid, List<Datapoint> datapoints) {
		this.id = clusterID;
		this.medoid = medoid;
		this.data = datapoints;
		
		setClusterAssignment();
	}

	public Datapoint getMedoid(){
		return this.medoid;
	}
	
	public short getID(){
		return this.id;
	}
	
	public short size(){
		int size = data.size();
		if(medoid != null){
			size++;
		}
		return (short) size;
	}
	
	private void setClusterAssignment(){
		if(medoid != null){
			medoid.setClusterID(this.id);
		}
		
		for(Datapoint datapoint : this.data){
			datapoint.setClusterID(this.id);
		}
	}
	
	public boolean contains(Datapoint datapoint){
		if(this.medoid.equals(datapoint))
			return true;
		
		for(Datapoint point : data){
			if(point.equals(datapoint))
				return true;
		}
		return false;
	}
	
	public Datapoint getDatapoint(int index){
		return data.get(index);
	}
	
	public MBR computeMBR(int beginExampleIndex, int endExampleIndex){
		short minX = this.medoid.getX();
		short maxX = this.medoid.getX();
		short minY = this.medoid.getY();
		short maxY = this.medoid.getY();
		
		for(Datapoint point : this.data){
			short x = point.getX();
			short y = point.getY();
			
			if(x < minX){
				minX = x;
			}
			if(x > maxX){
				maxX = x;
			}
			if(y < minY){
				minY = y;
			}
			if(y > maxY){
				maxY = y;
			}
		}
		
		return new MBR(minX, maxX, minY, maxY, (short) (endExampleIndex-beginExampleIndex+1));
	}
	
	public void sort(int spatialFeatureIdx, int begin, int end){
		//aggiunto il medoide alla lista, per trattare tutto in un'unica struttura dati
		this.data.add(this.medoid);
		//chiamo il quicksort che ordina la lista di dati appena creata
		quicksort(spatialFeatureIdx, begin, end);
	}
	
	private void quicksort(int spatialFeatureIdx, int inf, int sup){
		if(sup >= inf){
			int pos = partition(spatialFeatureIdx, inf, sup);
			if( (pos-inf) < (sup-pos+1) ){
				quicksort(spatialFeatureIdx, inf, pos-1);
				quicksort(spatialFeatureIdx, pos+1, sup);
			} else {
				quicksort(spatialFeatureIdx, pos+1, sup);
				quicksort(spatialFeatureIdx, inf, pos-1);
			}
		}
	}
	
	private int partition(int spatialFeatureIdx, int inf, int sup){
		int i,j;
		i = inf;
		j = sup;
		int med = (inf + sup) / 2;
		short x = data.get(med).getSpatialFeature(spatialFeatureIdx);
		
		swap(inf, med);
		
		while(true){
			short xi = data.get(i).getSpatialFeature(spatialFeatureIdx);
			while(i <= sup && xi <= x){
				i++;
				if(i <= sup){
					xi = data.get(i).getSpatialFeature(spatialFeatureIdx);
				}
			}
			
			double xj = data.get(j).getSpatialFeature(spatialFeatureIdx);
			while(xj > x){
				j--;
				xj = data.get(j).getSpatialFeature(spatialFeatureIdx);
			}
			
			if(i < j){
				swap(i,j);
			} else 
				break;
		}
		
		swap(inf, j);
		return j;
	}
	
	private void swap(int i, int j){
		Datapoint temp;
		temp = data.get(i);
		data.set(i, data.get(j));
		data.set(j, temp);
	}
	
	@Override
	public Iterator<Datapoint> iterator() {
		return new Iterator<Datapoint>(){
			int i = -1;
			Iterator<Datapoint> dpIterator = data.iterator();
			
			@Override
			public boolean hasNext() {
				if(i == -1){
					return true;
				} else {
					return dpIterator.hasNext();
				}
			}

			@Override
			public Datapoint next() {
				if(i == -1){
					i++;
					if(medoid != null){
						return medoid;
					} else {
						if(dpIterator.hasNext()){
							return dpIterator.next();
						}
					}
				} else {
					return dpIterator.next();
				}
				
				return null;
			}
		};
	}
	
	@Override
	public boolean equals(Object obj) {
		Cluster otherCluster = (Cluster) obj;
		return this.id == otherCluster.id;
	}
	
	@Override
	public String toString() {
		String result = "";
		
		for(Datapoint datapoint : this){
			result += datapoint.toString() + "\n";
		}
		
		return result;
	}

}
