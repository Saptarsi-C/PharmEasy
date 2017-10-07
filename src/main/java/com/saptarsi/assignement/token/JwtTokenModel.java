/**
 * 
 */
package com.saptarsi.assignement.token;

import com.google.gson.annotations.Expose;

import lombok.Data;

/**
 * @author saptarsichaurashy
 *
 */
@Data
public class JwtTokenModel {

	@Expose
	private String sub;
	
	@Expose
	private String iss;
	
	@Expose
	private String expiry;

	public String getSub() {
		return sub;
	}

	public void setSub(String sub) {
		this.sub = sub;
	}

	public String getIss() {
		return iss;
	}

	public void setIss(String iss) {
		this.iss = iss;
	}

	public String getExpiry() {
		return expiry;
	}

	public void setExpiry(String expiry) {
		this.expiry = expiry;
	}
	
	
}
