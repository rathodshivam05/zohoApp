package com.clayfin.exception;

public class AttendanceException extends Exception {

	private static final long serialVersionUID = 6959376221635013737L;

	public AttendanceException() {
	}
	
	public AttendanceException(String msg) {
		super(msg);
	}
}
