package com.saptarsi.assignement.exception;

import java.util.Map;

import org.apache.commons.lang.text.StrSubstitutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import com.google.gson.Gson;

public class CustomRuntimeException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 10987654321L;
	@Autowired
	Gson gson;

	protected String message;
	protected HttpStatus httpStatus;
	protected int applicationCode;

	protected Map<String, Object> params;

	public CustomRuntimeException(String message, HttpStatus httpStatus, int applicationCode,
			Map<String, Object> params) {
		this.message = message;
		this.httpStatus = httpStatus;
		this.applicationCode = applicationCode;
		this.params = params;
	}

	public CustomRuntimeException(String message) {
		this.message = message;
	}

	public HttpStatus getHttpStatus() {
		return httpStatus;
	}

	public int getApplicationCode() {
		return applicationCode;
	}

	public String getExceptionMessage() {
		return message;
	}

	@Override
	public String getMessage() {
		StrSubstitutor substitute = new StrSubstitutor(params);
		return substitute.replace(message);
	}

	@Override
	public String toString() {
		return super.toString();
	}

}
