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

	private void setClusterAssignment(){
		if(medoid != null){
			medoid.setClusterID(id);
		}
		
		for(Datapoint datapoint : data){
			datapoint.setClusterID(id);
		}
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
	
	public boolean contains(Datapoint datapoint){
		if(medoid != null){
			if(this.medoid.equals(datapoint))
				return true;
		}
		
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
		short minX;
		short maxX;
		short minY;
		short maxY;
		
		if(medoid != null){
			minX = this.medoid.getX();
			maxX = this.medoid.getX();
			minY = this.medoid.getY();
			maxY = this.medoid.getY();
		} else {
			minX = data.get(beginExampleIndex).getX();
			maxX = data.get(beginExampleIndex).getX();
			minY = data.get(beginExampleIndex).getY();
			maxY = data.get(beginExampleIndex).getY();
			beginExampleIndex++;
		}
		
		for(int i = beginExampleIndex; i < endExampleIndex; i++){
			short x = data.get(i).getX();
			short y = data.get(i).getY();
			
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
		
		return new MBR(minX, minY, maxX, maxY, (short)(endExampleIndex - beginExampleIndex + 1)); //prima per la cardinalità c'era this.size
	}
	
	public void sort(int spatialFeatureIdx, int begin, int end){
		//aggiunto il medoide alla lista, per trattare tutto in un'unica struttura dati
		if(medoid != null){
			data.add(this.medoid);
			medoid = null;
		}
		
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
	
	public void printCoord(){ //cancellare quando funziona tutto
		System.out.println("PRINT COORD");
		String result = "";
		for(Datapoint point : this){
			result += "(" + point.getX() + "," + point.getY() + ") ";
		}
		System.out.println(result);
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
