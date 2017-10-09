/**
 * 
 */
package com.saptarsi.assignement.service.impl;

import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.saptarsi.assignement.AssignmentConstant;
import com.saptarsi.assignement.dao.DoctorMappingDao;
import com.saptarsi.assignement.dao.PharmaMappingDao;
import com.saptarsi.assignement.domain.DoctorMapping;
import com.saptarsi.assignement.domain.PharmaMapping;
import com.saptarsi.assignement.model.UserIdentity;
import com.saptarsi.assignement.model.response.ModelAPIResponse;
import com.saptarsi.assignement.service.RegisteredListService;
import com.saptarsi.assignement.utils.Role;
import com.saptarsi.assignement.utils.TokenToIdentityService;

/**
 * @author saptarsichaurashy
 *
 */
public class RegisteredListServiceImpl implements RegisteredListService {
	
	private final static Logger log = LoggerFactory.getLogger(RegisteredListServiceImpl.class);
	
	@Autowired
	private DoctorMappingDao doctorMappingDao;
	
	@Autowired
	private PharmaMappingDao pharmaMappingDao;
	
	@Autowired
	private TokenToIdentityService tokenService;
	
	/* (non-Javadoc)
	 * @see com.saptarsi.assignement.service.RegisteredListService#getRegisteredDoctorList()
	 */
	@Override
	public ModelAPIResponse getRegisteredDoctorList(String token, Boolean pageFlag, String pageNo) {
		
		Pageable pageable;
		UserIdentity identity = tokenService.getIdentityFromToken(token);
		//Ensure that this is called only by a patient
		if(Role.fromValue(identity.getRole()) != Role.PATIENT){
			log.info("It should be called by a patient only");
			return null;
		}
		if(pageFlag){
			pageable = new PageRequest(Integer.parseInt(pageNo), AssignmentConstant.PAGE_SIZE);
		}
		else{
			pageable = new PageRequest(0, AssignmentConstant.PAGE_SIZE);
		}
		List<DoctorMapping> doctorMappings = doctorMappingDao.getByPId(identity.getId(), pageable);
		if(doctorMappings == null){
			log.info("No patient exists");
			return null;
		}
		List<Long> ids = new LinkedList<Long>();
		for(DoctorMapping doc : doctorMappings){
			ids.add(doc.getDId());
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see com.saptarsi.assignement.service.RegisteredListService#getRegisteredPharmacistList()
	 */
	@Override
	public ModelAPIResponse getRegisteredPharmacistList(String token, Boolean pageFlag, String pageNo) {
		
		Pageable pageable;
		UserIdentity identity = tokenService.getIdentityFromToken(token);
		//Ensure that this is called only by a patient
		if(Role.fromValue(identity.getRole()) != Role.PATIENT){
			log.info("It should be called by a patient only");
			return null;
		}
		if(pageFlag){
			pageable = new PageRequest(Integer.parseInt(pageNo), AssignmentConstant.PAGE_SIZE);
		}
		else{
			pageable = new PageRequest(0, AssignmentConstant.PAGE_SIZE);
		}
		List<PharmaMapping> pharmaMappings = pharmaMappingDao.getByUId(identity.getId(), pageable);
		if(pharmaMappings == null){
			log.info("No patient exists");
			return null;
		}
		List<Long> ids = new LinkedList<Long>();
		for(PharmaMapping doc : pharmaMappings){
			ids.add(doc.getPId());
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see com.saptarsi.assignement.service.RegisteredListService#getDoctorRegisterdList()
	 * This will give me the list of doctors whose prescription the current doctor is authorized to see 
	 */
	@Override
	public ModelAPIResponse getDoctorRegisterdList(String token, Long docId) {
		
		UserIdentity identity = tokenService.getIdentityFromToken(token);
		//Ensure that this is called only by a patient
		if(Role.fromValue(identity.getRole()) != Role.PATIENT){
			log.info("It should be called by a patient only");
			return null;
		}
		DoctorMapping doc = doctorMappingDao.getByPIdAndDId(identity.getId(), docId);
		if(doc == null){
			log.info("No mapping exists for the patient and the doctor");
			return null;
		}
		List<Long> authIds = doc.getAuthDId();
		return null;
	}

}
