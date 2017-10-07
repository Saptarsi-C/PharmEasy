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
public class JwtExpiredException extends CustomRuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8047142500224571648L;
	
	public JwtExpiredException(String message, HttpStatus httpStatus, int applicationCode, Map<String, Object> params) {
		super(message, httpStatus, applicationCode, params);
		// TODO Auto-generated constructor stub
	}


	public JwtExpiredException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}
	
}
