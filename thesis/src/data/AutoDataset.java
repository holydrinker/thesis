package data;


import autocorrelation.AutocorrelationI;
import io.DataTO;
import io.FeatureVectorTO;

public class AutoDataset extends Data {
	/*
	 * La classe � stata completamente riscritta dopo il ricevimento del 31 luglio.
	 * Non vengono pi� generati nuovi transfer object come avevo fatto io, bens� viene creata una matrice nuova, 
	 * delle stesse dimensioni del dataset, vuota. Viene popolata generando nuovi datapoint con l'autocorrelazione.
	 * 
	 * Cosa aggiunte:
	 * - Costruttore nella classe data, perch� qui ho bisogno di generare un Dataset passando solo il feature vector.
	 */
	
	public AutoDataset(FeatureVectorTO fvTO, DataTO stream, AutocorrelationI ac, short radius) {
		super(fvTO, stream);
		final int MAX_X = this.getHeight();
		final int MAX_Y = this.getWidth();
		
		//Creo e popolo una nuova matrice che rimpiazzer� il dataset appena costruita.
		System.out.println("Computing autocorrelation indexes...");
		Datapoint[][] acDatapoints = new Datapoint[MAX_X][MAX_Y];
		for(int x = 0; x < MAX_X; x++){
			for(int y = 0; y < MAX_Y; y++){
				//Iterator return only not null cells. Why I need this contorl? :\
				Datapoint acDatapoint = this.getDatapoint((short)x, (short)y);
				if(acDatapoint != null){
					ac.compute(this, (short)x, (short)y, (short)radius);			
					acDatapoints[x][y] = acDatapoint;
				}
			}
		}
		
		//Switch old matrix with autocorrlated values' matrix
		this.datapoints = acDatapoints;
		
		//Scaling autocorrelation value
		super.scaling();
		
		System.out.println("Done!\n");
	}
	
}
