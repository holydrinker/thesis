package autocorrelation;

import java.util.List;

import data.Factory;
import distance.EuclideanDistance;
import distance.InverseWeight;

public class AcFactory implements Factory {
	private static final String GO = "GO";
	private static final String NONE = "none";
	
	@Override
	public Object getInstance(String request, List<Object> params) {
		AutocorrelationI result = null;
		short radius = (short) params.get(0);
		byte q = (byte) params.get(1);
		
		switch(request){
			case GO: result = new GetisOrd(new InverseWeight(radius, q));
			break;
				
			case NONE: result = null;
			break;
		}
		
		/*if(result == null)
			throw new InvalidAutocorrelationChoice("arg 3: " + request + " is not a valid choice for autocorrelation setting up.");*/
		return result;
	}

}
