package com.clayfin.exception;

public class ClaimException extends Exception{

	private static final long serialVersionUID = 6959376221635013737L;

	public ClaimException() {
	}
	
	public ClaimException(String msg) {
		super(msg);
	}
}
