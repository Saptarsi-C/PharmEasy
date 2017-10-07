/**
 * 
 */
package com.saptarsi.assignement.model.request;

import java.io.Serializable;

import javax.validation.Valid;

import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.gson.annotations.Expose;

import lombok.Data;

/**
 * @author saptarsichaurashy
 *
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class SignupRequest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Valid
	@Expose
	private UserData userData;
	
	@Expose
	private String fname;
	@Expose
	private String lname;
	
	@NotBlank
	@Expose
	private String role;
	
}
