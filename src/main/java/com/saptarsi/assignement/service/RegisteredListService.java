/**
 * 
 */
package com.saptarsi.assignement.service;

import com.saptarsi.assignement.model.response.ModelAPIResponse;

/**
 * @author saptarsichaurashy
 *
 */
public interface RegisteredListService {

	public ModelAPIResponse getRegisteredDoctorList(String token, Boolean pageFlag, String pageNo);
	public ModelAPIResponse getRegisteredPharmacistList(String token, Boolean pageFlag, String pageNo);
	public ModelAPIResponse getDoctorRegisterdList(String token, Long docId);
}
