/**
 * 
 */
package com.saptarsi.assignement.service;

import com.saptarsi.assignement.model.request.LoginRequest;
import com.saptarsi.assignement.model.response.ModelAPIResponse;

/**
 * @author saptarsichaurashy
 *
 */
public interface LoginService {

	public ModelAPIResponse getLoginresponse(LoginRequest loginRequest);
}
