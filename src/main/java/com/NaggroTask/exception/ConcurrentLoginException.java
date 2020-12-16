package com.NaggroTask.exception;


public class ConcurrentLoginException extends RuntimeException {
	
	
	private static final long serialVersionUID = 1L;

	public ConcurrentLoginException(String user) {
		super(ConcurrentLoginException.generateMessage(user));
		
		
	}

	private static String generateMessage(String entity) {
	 
		return entity + " can not excute concurrent logins ";
	}
}