package com.espresso.http.exceptions;

public class EspressoAPIException extends Throwable{
	
	private static final long serialVersionUID = 1L;
	public String message;
	public String code;
	
	public EspressoAPIException(String message, String code) {
		this.message = message;
		this.code = code;
	}
	
	public EspressoAPIException(String message) {
		this.message = message;
	}
	
	public String toString() {
		return "EspressoApiException [message=" + message + ",code=" + code + "]";
	}
}
