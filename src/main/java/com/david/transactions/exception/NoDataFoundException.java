package com.david.transactions.exception;

public class NoDataFoundException extends RuntimeException {


	private static final long serialVersionUID = 1L;

	
	public NoDataFoundException() {
	}
	
	public NoDataFoundException(String msg) {
		
		super(msg);
	}
}
