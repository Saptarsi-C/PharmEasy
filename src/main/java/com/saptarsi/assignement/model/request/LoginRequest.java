/**
 * 
 */
package com.saptarsi.assignement.model.request;

import java.io.Serializable;

import javax.validation.Valid;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.gson.annotations.Expose;

import lombok.Data;

/**
 * @author saptarsichaurashy
 *
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class LoginRequest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Valid
	@Expose
	private UserData userData;
}
