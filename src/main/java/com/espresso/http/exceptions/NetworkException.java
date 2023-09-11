package com.espresso.http.exceptions;

//Represents a network issue between Smart API and the backend Order Management System (OMS).
//Default code is 503.

public class NetworkException extends EspressoAPIException {

	private static final long serialVersionUID = 1L;

	// initialize Smart API Network exception and call Base Exception constructor
    public NetworkException(String message, String code){
        super(message, code);
    }
}
