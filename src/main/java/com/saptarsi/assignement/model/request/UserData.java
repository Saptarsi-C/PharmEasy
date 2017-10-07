/**
 * 
 */
package com.saptarsi.assignement.model.request;

import java.io.Serializable;

import org.hibernate.validator.constraints.NotBlank;

import com.google.gson.annotations.Expose;

import lombok.Data;

/**
 * @author saptarsichaurashy
 *
 */
@Data
public class UserData implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

    @NotBlank
    @Expose
	private String userName;
	
    @NotBlank
    @Expose
	private String pasword;
}
