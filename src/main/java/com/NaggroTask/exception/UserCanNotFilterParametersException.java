package com.NaggroTask.exception;

 
public class UserCanNotFilterParametersException extends RuntimeException {
	private static final long serialVersionUID = -401380233219982838L;

	public UserCanNotFilterParametersException(String user) {
		super(UserCanNotFilterParametersException.generateMessage(user));
	}

	private static String generateMessage(String entity) {
		return entity + " can not excute filter by any amount or date parameters ";
	} 
}