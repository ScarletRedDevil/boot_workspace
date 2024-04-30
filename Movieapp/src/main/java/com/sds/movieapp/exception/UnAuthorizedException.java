package com.sds.movieapp.exception;

public class UnAuthorizedException extends RuntimeException {
	
	public UnAuthorizedException(String msg) {
		super(msg);
	}
	
	public UnAuthorizedException(String msg,Throwable e) {
		super(msg,e);
	}
	
	public UnAuthorizedException(Throwable e) {
		super(e);
	}

}
