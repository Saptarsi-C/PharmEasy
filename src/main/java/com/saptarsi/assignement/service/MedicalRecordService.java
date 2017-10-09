/**
 * 
 */
package com.saptarsi.assignement.service;

import com.saptarsi.assignement.model.response.ModelAPIResponse;

/**
 * @author saptarsichaurashy
 *
 */
public interface MedicalRecordService {
	public ModelAPIResponse getMedicalRecord(String token, String userName, Long pId);
	public ModelAPIResponse updateMedicalRecord(String token, String userName, Long pId, Object medicalRecord);
}
