package com.NaggroTask.exception;

public class BadCredentialsException  extends RuntimeException{
	private static final long serialVersionUID = 1L;

	public BadCredentialsException(String user) {
		super(BadCredentialsException.generateMessage(user));
		
		
	}

	private static String generateMessage(String entity) {
	 
		return entity + " Invalid Credential. Authentication is required ";
	}

}
