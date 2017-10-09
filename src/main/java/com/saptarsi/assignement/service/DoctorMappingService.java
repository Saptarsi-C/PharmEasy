/**
 * 
 */
package com.saptarsi.assignement.service;

import com.saptarsi.assignement.model.response.ModelAPIResponse;

/**
 * @author saptarsichaurashy
 *
 */
public interface DoctorMappingService {

	public ModelAPIResponse getDoctorDetailsFromId(String token, Boolean pageFlag, String page);
	
}
