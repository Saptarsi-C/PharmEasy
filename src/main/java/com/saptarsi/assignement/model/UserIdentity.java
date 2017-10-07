/**
 * 
 */
package com.saptarsi.assignement.model;

import com.google.gson.annotations.Expose;
import com.saptarsi.assignement.domain.User;

import lombok.Data;

/**
 * @author saptarsichaurashy
 *
 */
@Data
public class UserIdentity {

	@Expose
	private String fname;
	
	@Expose
	private String lname;
	
	@Expose
	private String userName;
	
	@Expose
	private String role;
	
	public UserIdentity(User user){
		
		userName = (user.getUserName());
		fname = user.getFname();
		lname = user.getLname();
		role = user.getRole().toString();
	}
}
