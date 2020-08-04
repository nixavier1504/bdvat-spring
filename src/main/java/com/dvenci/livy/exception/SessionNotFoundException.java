package com.dvenci.livy.exception;

public class SessionNotFoundException extends Exception implements LivyException {

	private static final long serialVersionUID = 1L;

	public SessionNotFoundException(String arg0) {
		super(arg0);
	}
	
	public SessionNotFoundException() {
		super();
	}
}
