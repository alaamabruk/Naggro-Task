package com.NaggroTask.exception;


public class MissingParamException extends RuntimeException {
	private static final long serialVersionUID = -401380233219982838L;

	public MissingParamException(String user) {
		super(MissingParamException.generateMessage(user));
	}

	private static String generateMessage(String msg) {
		return msg;
	} 
}