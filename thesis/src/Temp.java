import java.util.HashMap;
import java.util.LinkedList;

public class Temp {
	
	public static void main(String[] args) {
		LinkedList<Object> list = new LinkedList<Object>();
		list.add(1);
		list.add(2);
		list.add(3);
		
		Object o = list.removeLast();
		
		System.out.println("last: " + o);
		for(Object s : list)
			System.out.println(s.toString());
	}

}
