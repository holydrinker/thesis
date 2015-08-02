package data;


import autocorrelation.AutocorrelationI;
import io.DataTO;
import io.FeatureVectorTO;

public class AutoDataset extends Data {
	/*
	 * La classe è stata completamente riscritta dopo il ricevimento del 31 luglio.
	 * Non vengono più generati nuovi transfer object come avevo fatto io, pensi viene creata una matrice nuova, 
	 * delle stesse dimensioni del dataset, vuota. Viene popolata generando nuovi datapoint con l'autocorrelazione.
	 * 
	 * Cosa aggiunte:
	 * - Costruttore nella classe data, perchè qui ho bisogno di generare un Dataset passando soloil feature vector.
	 */
	
	public AutoDataset(FeatureVectorTO fvTO, DataTO stream, AutocorrelationI ac, short radius) {
		super(fvTO, stream);
		final int MAX_X = this.getWidth();
		final int MAX_Y = this.getHeight();
		
		//Creo e popolo una nuova matrice che rimpiazzerà il dataset appena costruita.
		Datapoint[][] acValues = new Datapoint[MAX_X][MAX_Y];
		for(int x = 0; x < MAX_X; x++){
			for(int y = 0; y < MAX_Y; y++){
				Datapoint acDatapoint = ac.compute(this, (short)x, (short)y, (short)radius);
				acValues[x][y] = acDatapoint;
			}
		}
		
		//Sostituisco la matrice con i valori spettrali con la matrice appena popolata con le autocorrelazioni
		this.datapoints = acValues;
		
		//Scalo i valori della matrice, che ora sono i valori di autocorrelazione
		//super.scaling();
	}
	
}
