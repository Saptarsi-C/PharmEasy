package com.saptarsi.assignement.exception;

import java.util.Map;

import org.springframework.http.HttpStatus;

import com.saptarsi.assignement.errorcodes.ErrorCodes;

public class InvalidParameterException extends CustomRuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InvalidParameterException(String message, Map<String, Object> params) {
		super(message, HttpStatus.BAD_REQUEST, Integer.parseInt(ErrorCodes.INVALID_PARAMETER_EXP), params);
		// TODO Auto-generated constructor stub
	}
	
    public InvalidParameterException() {
        super("Parameter passed to method does not match data type or is invalid");
    }
    
    public InvalidParameterException(String message){
    	super(message);
    }

}
