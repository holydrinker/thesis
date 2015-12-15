package data;

import autocorrelation.AutocorrelationI;
import io.DataTO;
import io.FeatureVectorTO;

public class AutoDataset extends Data {
	
	public AutoDataset(FeatureVectorTO fvTO, DataTO stream, AutocorrelationI ac, short radius) {
		super(fvTO, stream);
		
		//Scaling autocorrelation value
		super.scaling();
		
		System.out.println("Computing autocorrelation indexes for " + super.size + " instances...");
		//This matrix is going to replace super.datapoints
		final int MAX_X = this.getHeight();
		final int MAX_Y = this.getWidth();
		Datapoint[][] acData = new Datapoint[MAX_X][MAX_Y];
		
		short x;
		short y;
		int count = 0;
		for(Datapoint dp : this){
			x = dp.getX();
			y = dp.getY();
			acData[x][y] = ac.compute(this, (short)x, (short)y, (short)radius);
			System.out.println(count++);;
		}
		
		/*
		int count = 0;
		for(int x = 0; x < MAX_X; x++){
			for(int y = 0; y < MAX_Y; y++){
				Datapoint acDatapoint = this.getDatapoint((short)x, (short)y);
				if(acDatapoint != null){
					acData[x][y] = ac.compute(this, (short)x, (short)y, (short)radius);
				}
				System.out.println(count++);
			}
		}
		*/
		
		//Switch the old matrix with the new matrix
		this.datapoints = acData;
		
		System.out.println("Done! \n");
	}
	
}
