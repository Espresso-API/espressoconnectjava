package com.espresso.http.exceptions;

// Exceptions raised when invalid data is returned.

@SuppressWarnings("serial")
public class DataException extends EspressoAPIException{

	 public DataException(String message, String code){
	        super(message, code);
	    }
}
