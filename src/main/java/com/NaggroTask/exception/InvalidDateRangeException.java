package com.NaggroTask.exception;

public class InvalidDateRangeException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	public InvalidDateRangeException(String user) {
		super(InvalidDateRangeException.generateMessage(user));
		
		
	}

	private static String generateMessage(String msg) {
	 
		return msg;
	}

}
