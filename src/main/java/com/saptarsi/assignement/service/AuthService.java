/**
 * 
 */
package com.saptarsi.assignement.service;

import java.util.List;

import com.saptarsi.assignement.model.response.ModelAPIResponse;

/**
 * @author saptarsichaurashy
 *
 */
public interface AuthService {

	public ModelAPIResponse addAuthorization(String token, String reqRole, Long id, List<Long> list);
	public ModelAPIResponse removeAuthorization(String token, String reqRole, Long id, List<Long> ids);
	public ModelAPIResponse removeFullAuthorization(String token, String reqRole, Long id);
}
