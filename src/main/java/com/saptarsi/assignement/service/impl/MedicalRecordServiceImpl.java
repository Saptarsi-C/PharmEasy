/**
 * 
 */
package com.saptarsi.assignement.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.saptarsi.assignement.AssignmentConstant;
import com.saptarsi.assignement.dao.CacheEntry;
import com.saptarsi.assignement.dao.DoctorMappingDao;
import com.saptarsi.assignement.dao.KeyValueRepository;
import com.saptarsi.assignement.dao.UserDao;
import com.saptarsi.assignement.domain.DoctorMapping;
import com.saptarsi.assignement.domain.User;
import com.saptarsi.assignement.model.UserIdentity;
import com.saptarsi.assignement.model.response.ModelAPIResponse;
import com.saptarsi.assignement.service.MedicalRecordService;
import com.saptarsi.assignement.utils.Role;
import com.saptarsi.assignement.utils.TokenToIdentityService;

/**
 * @author saptarsichaurashy
 *
 */
@Service
public class MedicalRecordServiceImpl implements MedicalRecordService {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.saptarsi.assignement.service.MedicalRecordService#getMedicalRecord()
	 */
	private final static Logger log = LoggerFactory.getLogger(MedicalRecordServiceImpl.class);

	@Autowired
	private DoctorMappingDao doctorMappingDao;

	@Autowired
	private UserDao userDao;

	@Autowired
	private KeyValueRepository kvRepository;

	@Autowired
	private TokenToIdentityService tokenService;
	
	private static final CacheEntry cacheEntry = new CacheEntry("PrescriptionStore", String.class);

	@Override
	public ModelAPIResponse getMedicalRecord(String token, String userName, Long pId) {
		
		UserIdentity identity = tokenService.getIdentityFromToken(token);
		// Pid can be null when doctor wants to see the details or patient himself wants to see his details
		if(pId == null && Role.fromValue(identity.getRole()) == Role.DOCTOR ){
			User user = userDao.findByUserNameAndRole(userName, Role.PATIENT);
			if(user == null){
				log.info("No user found by the user name");
				return null;
			}
			pId = user.getId();
		}
		//if a patient wants to see his own medical record
		if(Role.fromValue(identity.getRole()) == Role.PATIENT){
			Object medicalRecord = kvRepository.get(cacheEntry, pId.toString(), AssignmentConstant.MEDICAL_RECORD_BIN);
			if(medicalRecord == null){
				log.info("No such prescription exists for the doctor");
				return null;
			}
			log.info(medicalRecord.toString());
			return null;
		}
		DoctorMapping doctorMapping = doctorMappingDao.getByPIdAndDId(pId, identity.getId());
		if(doctorMapping == null){
			log.info("Doctor is not allowed to see the patient details");
			return null;
		}
		Object medicalRecord = kvRepository.get(cacheEntry, pId.toString(), AssignmentConstant.MEDICAL_RECORD_BIN);
		if(medicalRecord == null){
			log.info("No such prescription exists for the doctor");
			return null;
		}
		log.info(medicalRecord.toString());		
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.saptarsi.assignement.service.MedicalRecordService#updateMedicalRecord
	 * ()
	 */
	@Override
	public ModelAPIResponse updateMedicalRecord(String token, String userName, Long pId, Object medicalRecord) {
		
		UserIdentity identity = tokenService.getIdentityFromToken(token);
		//A doctor can only update the medical record of a patient
		if(Role.fromValue(identity.getRole()) != Role.DOCTOR){
			log.info("Only doctor is allowed to change the medical record");
			return null;
		}
		if(pId == null){
			User user = userDao.findByUserNameAndRole(userName, Role.PATIENT);
			if(user == null){
				log.info("No user found by the user name");
				return null;
			}
			pId = user.getId();
		}
		Map<String, Object> val = new HashMap<String, Object>();
		val.put(AssignmentConstant.MEDICAL_RECORD_BIN, medicalRecord);
		kvRepository.put(cacheEntry, pId.toString(), val);
		log.info(medicalRecord.toString());
		return null;
	}

}
