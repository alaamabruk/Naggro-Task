package com.NaggroTask.exception;

public class InvalidAmountRangeException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	public InvalidAmountRangeException(String user) {
		super(InvalidAmountRangeException.generateMessage(user));
		
		
	}

	private static String generateMessage(String msg) {
	 
		return msg;
	}


}
