/**
 * 
 */
package com.saptarsi.assignement.model.request;

import java.io.Serializable;
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
public class RegistrationRequest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Expose
	@NotBlank
	private String token;
	
	@Expose
	@NotBlank
	private String userList;
	
	@Expose
	@NotBlank
	private String userRole;
	
}
