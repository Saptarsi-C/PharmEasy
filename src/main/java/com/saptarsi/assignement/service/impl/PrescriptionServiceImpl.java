/**
 * 
 */
package com.saptarsi.assignement.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.saptarsi.assignement.dao.CacheEntry;
import com.saptarsi.assignement.dao.DoctorMappingDao;
import com.saptarsi.assignement.dao.KeyValueRepository;
import com.saptarsi.assignement.dao.PharmaMappingDao;
import com.saptarsi.assignement.dao.UserDao;
import com.saptarsi.assignement.domain.DoctorMapping;
import com.saptarsi.assignement.domain.PharmaMapping;
import com.saptarsi.assignement.domain.User;
import com.saptarsi.assignement.model.UserIdentity;
import com.saptarsi.assignement.model.response.ModelAPIResponse;
import com.saptarsi.assignement.service.PrescriptionService;
import com.saptarsi.assignement.token.JwtConstantParams;
import com.saptarsi.assignement.token.TokenFactory;
import com.saptarsi.assignement.token.exception.JwtExpiredException;
import com.saptarsi.assignement.token.exception.JwtTamperedException;
import com.saptarsi.assignement.utils.MyTokenService;
import com.saptarsi.assignement.utils.Role;
import com.saptarsi.assignement.utils.UserStatus;

/**
 * @author saptarsichaurashy
 *
 */
@Service
public class PrescriptionServiceImpl implements PrescriptionService {

	private final static Logger log = LoggerFactory.getLogger(PrescriptionServiceImpl.class);

	@Value("${com.saptarsi.token.algo}")
	private String ALGORITHM;

	@Value("${com.saptarsi.token.key}")
	private String KEY;

	@Autowired
	private MyTokenService myTokenService;

	@Autowired
	private Gson gson;

	@Autowired
	private UserDao userDao;

	@Autowired
	private DoctorMappingDao doctorMappingDao;

	@Autowired
	private PharmaMappingDao pharmaMappingDao;

	@Autowired
	private KeyValueRepository kvRepository;

	private static final CacheEntry cacheEntry = new CacheEntry("PrescriptionStore", String.class);

	@Override
	public ModelAPIResponse getPrescription(String token, String userName, Long pId, String docName) {

		Object verifyResponse;
		TokenFactory tokenFactory = new TokenFactory();
		tokenFactory.setTokenType("JWT");
		tokenFactory.setData(createJwtParams(token));
		try {
			verifyResponse = myTokenService.verifyToken(tokenFactory);
		} catch (JwtTamperedException ex) {
			return null;
		} catch (JwtExpiredException ex) {
			return null;
		}
		UserIdentity identity = gson.fromJson(verifyResponse.toString(), UserIdentity.class);
		log.info("User Identity : {}", identity.toString());
		// When patient id is not provided by the doctor or pharmacist
		if (pId == null) {
			User user = userDao.findByUserNameAndRole(userName, Role.PATIENT);
			if (user == null) {
				log.info("No patient found for the user");
				return null;
			}
			pId = user.getId();
		}
		// When a person wants to see his own prescription
		if (Role.fromValue(identity.getRole()) == Role.PATIENT) {
			if (pId == identity.getId()) {
				log.info("User wants to get his own prescription");
				return null;
			} else {
				log.info("One patient trying to see others prescription. Not Allowed");
			}
		}
		// When a pharmacist tries to see the prescription. By default if a
		// pharmacist is allowed, he is allowed to see all prescriptions of the
		// patient

		if (Role.fromValue(identity.getRole()) == Role.PHARMACIST) {
			PharmaMapping pharmaMapping = pharmaMappingDao.getByUIdAndPId(pId, identity.getId());
			if (pharmaMapping == null || pharmaMapping.getStatus() == UserStatus.BLOCKED) {
				log.info("Pharmacist is not allowed to see any prescription");
			} else {
				User doctor = userDao.findByUserNameAndRole(docName, Role.DOCTOR);
				if (doctor == null) {
					log.info("No doctor found");
					return null;
				}
				Long dId = doctor.getId();
				Object prescription = kvRepository.get(cacheEntry, pId.toString(), dId.toString());
				if(prescription == null){
					log.info("No such prescription exists for the doctor");
					return null;
				}
				log.info(prescription.toString());
				return null;
			}
		}
		// When role is doctor
		// If the doctor wants to see the prescription of another doctor
		if(docName != null){
			User doctor = userDao.findByUserNameAndRole(docName, Role.DOCTOR);
			if(doctor == null){
				log.info("No doctor found by the given doctor name");
				return null;
			}
			Long dId = identity.getId();
			Long reqId = doctor.getId();
			DoctorMapping doctorMapping = doctorMappingDao.getByPIdAndDId(pId, dId);
			List<Long> ids = doctorMapping.getAuthDId();
			boolean searchFlag = false;
			for(Long id : ids){
				if(id == reqId){
					searchFlag = true;
					break;
				}
			}
			if(searchFlag == false){
				log.info(identity.getUserName() + " is not authorised to view prescription of "+ docName);
				return null;
			}
			Object prescription = kvRepository.get(cacheEntry, pId.toString(), reqId.toString());
			if(prescription == null){
				log.info("No such prescription exists for the doctor");
				return null;
			}
			log.info(prescription.toString());
			return null;
		}
		// By default a doctor can his his own prescription of the patient. He
		// needs permission from the patient to see other doctors prescription
		Object prescription = kvRepository.get(cacheEntry, pId.toString(), identity.getId().toString());
		if(prescription == null){
			log.info("No such prescription exists for the doctor");
			return null;
		}
		log.info(prescription.toString());
		return null;
	}

	@Override
	public ModelAPIResponse updatePrescription(String token, String patientName, Long pId, Object prescription) {
		Object verifyResponse;
		TokenFactory tokenFactory = new TokenFactory();
		tokenFactory.setTokenType("JWT");
		tokenFactory.setData(createJwtParams(token));
		try {
			verifyResponse = myTokenService.verifyToken(tokenFactory);
		} catch (JwtTamperedException ex) {
			return null;
		} catch (JwtExpiredException ex) {
			return null;
		}
		UserIdentity identity = gson.fromJson(verifyResponse.toString(), UserIdentity.class);
		log.info("User Identity : {}", identity.toString());
		if(pId == null){
			User patient = userDao.findByUserNameAndRole(patientName, Role.PATIENT);
			if(patient == null){
				log.info("No patient found by the username");
			}
			pId = patient.getId();
		}
		Map<String, Object> val = new HashMap<String, Object>(2);
		val.put(identity.getId().toString(), prescription);
		kvRepository.put(cacheEntry, pId.toString(), val);
		return null;
	}

	private Map<String, Object> createJwtParams(String token) {

		Map<String, Object> params = new HashMap<String, Object>();
		params.put(JwtConstantParams.JWT, token);
		params.put(JwtConstantParams.KEY, KEY);
		params.put(JwtConstantParams.ALGORITHM, ALGORITHM);
		return params;
	}
}
