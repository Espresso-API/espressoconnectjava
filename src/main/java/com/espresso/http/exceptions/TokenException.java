package com.espresso.http.exceptions;

// Denotes session is expired

public class TokenException extends EspressoAPIException{
	
	private static final long serialVersionUID = 1L;

	public TokenException(String message, String code) {
        super(message, code);
    }

}
