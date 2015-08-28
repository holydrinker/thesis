package clustering;

import java.io.PrintWriter;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import data.Data;
import data.Datapoint;

public class Swapper {
	private Set<Datapoint> medoids = new HashSet<Datapoint>();
	private DistanceMatrix distanceMatrix;
	private Map<Datapoint,DistancePair> distanceMap;
	private Map<SwapPair, Double> swapMap;
	
	//TEST VARIABLES
	private int positivi = 0;
	private int negativi = 0;
	private int zeri = 0;
	
	public Swapper(Set<Datapoint> medoids, Data data, DistanceMatrix distanceMatrix) {
		this.distanceMatrix = distanceMatrix;
		this.medoids = medoids;
		
		boolean loop = true; 
		int loopCount = 0;
		PrintWriter writer = null;
		try {
			writer = new PrintWriter("D:/swap log.txt", "UTF-8"); //qui salvo il log delle operazioni si swapping
		} catch (Exception e1) {
			e1.printStackTrace();
		} 
		
		while(loop){
			this.negativi = 0;
			this.positivi = 0;
			this.zeri = 0;
			
			//Tracks the first and the second closer selected object from all unselected object
			initDistanceMap(data);
			
			//Compute T_ih for each pair S x U and save every result into swapMap
			initSwapMap(data);
				
			//Swapping
			Entry<SwapPair,Double> pair = choosePair();
			SwapPair swapPair = pair.getKey();
			double t = pair.getValue();
			
			//Swap or stop
			if(t < 0){
				swapping(swapPair);
			} else {
				loop = false;
			}
			
			try {
				writer.println("loopCount: " + (loopCount++) + " positivi: " + this.positivi + " negativi: " + this.negativi + " zeri: " + this.zeri);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		writer.close();
	}
	
	/*
	 * Return the new medoid set
	 */
	Set<Datapoint> compute(){
		return this.medoids;
	}
	
	/*
	 * Swapping phase:
	 * Si parte considerando il prodotto cartesiano fra i selectedObject e gli unselectedObject, quindi considereremo tutte le coppie (i,h) € S x U.
	 * Il nostro obiettivo è valutare se swappando i e h la nuova somma delle dissimilarità fra gli oggetti e il medoide loro più vicino è tale da considerare
	 * questo scambio conveniente.
	 * 
	 * Per far ciò calcoleremo un valore T_ih. In base al valore assunto da tale variabile considereremo lo swap come conveniente o meno.
	 * Per il calcolo di T_ih c'è bisogno di calcolare K_jih (un calcolo fatto su ogni j € a U - {j}. Per il calcolo di D_ij bisogna far in modo
	 * di tenere traccia di Dj e Ej (distanza da medoide più vicino a secondo medoide più vicino).
	 * 
	 * Per ogni coppia (i,j) si ha che T_ih = SUM(K_jih | j € U).
	 * Una volta aver fatto ciò per ogni coppia venuta fuori dal prodotto cartesiano, si seleziona la coppia che minimizza T_ih.
	 * Se il T_ih scelto < 0 allora si swappano e si aggiornano Dj e Ej per ogni elemento di U e si ripete tutto.
	 * Se il T_ih scelto > 0 l'algoritmo si ferma.
	 * 
	 */
	
	/*
	 * Tracks the distance between the first and the second closest selected object for every unselected object
	 */
	private void initDistanceMap(Data data){
		this.distanceMap = new HashMap<Datapoint, DistancePair>();
		
		for(Datapoint point : data){
			double first = Double.MAX_VALUE;
			double second = Double.MAX_VALUE;
			
			for(Datapoint medoid : medoids){
				if(!(point.equals(medoid))){
					double distance = this.distanceMatrix.getDistance(point, medoid);
					if(distance < first){
						second = first;
						first = distance;
					} else if(distance < second){
						second = distance;
					}
					
					DistancePair pair = new DistancePair(first, second);
					this.distanceMap.put(point, pair);
				}
			}
		}
	}
	
	/*
	 * Compute T_ih factor for all pairs in S x U
	 */
	private void initSwapMap(Data data){
		this.swapMap = new HashMap<SwapPair, Double>();
		
		for(Datapoint medoid : this.medoids){
			for(Datapoint swapPoint : data){
				if(!(medoid.equals(swapPoint))){
					double Tih = computeT(medoid, swapPoint, data);
					SwapPair pair = new SwapPair(medoid, swapPoint);
					this.swapMap.put(pair, Tih);
					
					if(Tih < 0){
						this.negativi++;
					} else if(Tih > 0){
						this.positivi++;
					} else {
						this.zeri++;
					}
				}
			}
		}
	}
	
	/*
	 * Compute T factor for a single pair in S x U
	 */
	private double computeT(Datapoint i, Datapoint h, Data data){
		/*
		 * i = medoid
		 * h = swapPoint
		 * j = otherPoint
		 */
		double result = 0d;
		
		for(Datapoint j : data){
			if(!(i.equals(j)) && !(h.equals(j))){
				result += computeK(i, h, j);
			}
		}
		
		return result;
	}
	
	/*
	 * Compute K factor between two points
	 */
	private Double computeK(Datapoint i, Datapoint h, Datapoint j){
		/*
		 * (i, h) in S x U 
		 * j in U - {h}
		 */
		double result = -1d;
		
		DistancePair distance = this.distanceMap.get(j);
		double Dj = distance.first;
		double Ej = distance.second;
		
		if(d(j,i) > Dj){
			result = min( (d(j,h) - Dj) , 0);
		} else if (d(j,i) == Dj) {
			result = min(d(j,h) , Ej) - Dj;
		} else {
			throw new InvalidSwappingFactor();
		}
		
		return result;
	}
		
	/*
	 * Compute the distance between p1 and p2
	 */
	private double d(Datapoint p1, Datapoint p2){
		return this.distanceMatrix.getDistance(p1, p2);
	}
	
	/*
	 * Return min value between two value
	 */
	private double min(double a, double b){
		return a < b ? a : b;
	}
	
	/*
	 * Choose the pair that minimize T factor
	 */
	private Entry<SwapPair,Double> choosePair(){
		Entry<SwapPair,Double> resultPair = null;
		double tMin = Double.MAX_VALUE;
		
		for(Entry<SwapPair, Double> pair : this.swapMap.entrySet()){
			double t = pair.getValue();
			if(t < tMin){
				tMin = t;
				resultPair = pair;
			}
		}
		
		return resultPair;
	}
	
	/*
	 * Swap a selected Object with a unselected Object
	 */
	private void swapping(SwapPair pair){
		Datapoint oldMedoid = pair.medoid;
		Datapoint newMedoid = pair.point;
		
		this.medoids.remove(oldMedoid);
		this.medoids.add(newMedoid);
	}
	
	//----INNER CLASSES
	
	class SwapPair{
		Datapoint medoid;
		Datapoint point;
		
		public SwapPair(Datapoint medoid, Datapoint point) {
			this.medoid = medoid;
			this.point = point;
		}
	}
	
	class DistancePair{
		double first;
		double second;
		
		public DistancePair(double first, double second) {
			this.first = first;
			this.second = second;
		}
	}

}
