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
public class CustomAsyncException extends CustomRuntimeException {

	public CustomAsyncException(String message, HttpStatus httpStatus, int applicationCode, Map<String, Object> params) {
		super(message, httpStatus, applicationCode, params);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CustomAsyncException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

}
