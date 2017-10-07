package com.saptarsi.assignement.token;

/**
 * @author saptarsichaurashy
 *
 */
import java.util.Map;

import org.springframework.stereotype.Component;

import com.google.gson.Gson;

public class TokenFactory {
	
	private Object tokenType;
	private Map<String, Object> data;
	private static Gson gson = new Gson();
	
	public Object getTokenType() {
		return tokenType;
	}
	public void setTokenType(Object tokenType) {
		this.tokenType = tokenType;
	}
	public Map<String, Object> getData() {
		return data;
	}
	public void setData(Map<String, Object> data) {
		this.data = data;
	}
	
	public String toString(){
		return gson.toJson(this);
	}
	
}
