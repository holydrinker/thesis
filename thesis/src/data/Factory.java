package data;

import java.util.List;

public interface Factory {

	public Object getInstance(String request, List<Object> params);
	
}
