/**
 * 
 */
package com.saptarsi.assignement.service;

import com.saptarsi.assignement.model.response.ModelAPIResponse;

/**
 * @author saptarsichaurashy
 *
 */
public interface PrescriptionService {

	public ModelAPIResponse getPrescription(String token, String patientName, Long pId, String docName);
	public ModelAPIResponse updatePrescription(String token, Object precription);
}
