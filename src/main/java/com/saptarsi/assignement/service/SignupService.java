/**
 * 
 */
package com.saptarsi.assignement.service;

import com.saptarsi.assignement.model.request.SignupRequest;
import com.saptarsi.assignement.model.response.ModelAPIResponse;

/**
 * @author saptarsichaurashy
 *
 */
public interface SignupService {

	public ModelAPIResponse getSignupResponse(SignupRequest signupRequest);
}
