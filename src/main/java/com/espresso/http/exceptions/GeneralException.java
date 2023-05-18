package com.espresso.http.exceptions;

//An unclassified, general error. Default code is 500

@SuppressWarnings("serial")
public class GeneralException extends EspressoAPIException {
	
	 public GeneralException(String message, String code){
	        super(message, code);
	    }

}
