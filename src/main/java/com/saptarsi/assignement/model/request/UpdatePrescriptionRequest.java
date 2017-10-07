/**
 * 
 */
package com.saptarsi.assignement.model.request;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import com.google.gson.annotations.Expose;

import lombok.Data;

/**
 * @author saptarsichaurashy
 *
 */
@Data
public class UpdatePrescriptionRequest implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Expose
	@NotNull
	private Serializable data;
	
	@Expose 
	@NotNull
	private String token;
	

}
