/**
 * 
 */
package com.saptarsi.assignement.exception;

import java.util.Map;

import org.springframework.http.HttpStatus;

/**
 * @author saptarsichaurashy
 *
 */
public class PasswordException extends CustomRuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 13456789098765L;

	public PasswordException(String message, HttpStatus httpStatus, int applicationCode, Map<String, Object> params) {
		super(message, httpStatus, applicationCode, params);
		// TODO Auto-generated constructor stub
	}

	public PasswordException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

}
