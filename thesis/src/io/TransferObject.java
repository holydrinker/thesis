package io;

import java.util.LinkedList;

public abstract class TransferObject {
	protected LinkedList<Object> param = new LinkedList<Object>();
	
	public TransferObject(LinkedList<Object> param) {
		this.param = param;
	}
	
	public LinkedList<Object> get(){
		return this.param;
	}
}
