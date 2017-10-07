package com.saptarsi.assignement.token.exception;

import java.util.Map;

import org.springframework.http.HttpStatus;

import com.saptarsi.assignement.exception.CustomRuntimeException;

public class JwtTamperedException extends CustomRuntimeException{

	private static final long serialVersionUID = 10987654123L;

	public JwtTamperedException(String message, HttpStatus httpStatus, int applicationCode, Map<String, Object> params) {
		super(message, httpStatus, applicationCode, params);
	}
	
	public JwtTamperedException(String message){
		super(message);
	}

}
