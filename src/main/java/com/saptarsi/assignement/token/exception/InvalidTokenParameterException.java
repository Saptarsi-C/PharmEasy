/**
 * 
 */
package com.saptarsi.assignement.token.exception;

import java.util.Map;

import org.springframework.http.HttpStatus;

import com.saptarsi.assignement.exception.CustomRuntimeException;

/**
 * @author saptarsichaurashy
 *
 */
public class InvalidTokenParameterException extends CustomRuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InvalidTokenParameterException(String message, HttpStatus httpStatus, int applicationCode, Map<String, Object> params) {
		super(message, httpStatus, applicationCode, params);
		// TODO Auto-generated constructor stub
	}

	public InvalidTokenParameterException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

}
