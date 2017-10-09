/**
 * 
 */
package com.saptarsi.assignement.service;

import com.saptarsi.assignement.model.response.ModelAPIResponse;

/**
 * @author saptarsichaurashy
 *
 */
public interface AuthRequestService {

	public ModelAPIResponse getAuthforDoctor(String token, Long docId, String docName, String patientName);
	public ModelAPIResponse getAuthForPharmacist(String token, String patientName);
}
