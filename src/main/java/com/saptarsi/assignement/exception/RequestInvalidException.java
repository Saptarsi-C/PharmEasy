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
public class RequestInvalidException extends CustomRuntimeException {

	String requestId;
	
	public String getRequestId() {
		return requestId;
	}


	public RequestInvalidException(String message, HttpStatus httpStatus, int applicationCode,
			Map<String, Object> params) {
		super(message, httpStatus, applicationCode, params);
		// TODO Auto-generated constructor stub
	}

	
	public RequestInvalidException(String requestId, String message, HttpStatus httpStatus, int applicationCode,
			Map<String, Object> params) {
		
		super(message, httpStatus, applicationCode, params);
		this.requestId = requestId;
		// TODO Auto-generated constructor stub
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public RequestInvalidException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}
	

}
