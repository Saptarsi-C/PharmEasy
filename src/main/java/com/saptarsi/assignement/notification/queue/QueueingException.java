/**
 * 
 */
package com.saptarsi.assignement.notification.queue;

import java.util.Map;

import org.springframework.http.HttpStatus;

import com.saptarsi.assignement.exception.CustomRuntimeException;

/**
 * @author saptarsichaurashy
 *
 */
public class QueueingException extends CustomRuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public QueueingException(String message, HttpStatus httpStatus, int applicationCode, Map<String, Object> params) {
		super(message, httpStatus, applicationCode, params);
		// TODO Auto-generated constructor stub
	}

	public QueueingException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

}
