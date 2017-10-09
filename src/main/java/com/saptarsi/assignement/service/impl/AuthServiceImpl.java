/**
 * 
 */
package com.saptarsi.assignement.service.impl;

import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.saptarsi.assignement.AssignmentConstant;
import com.saptarsi.assignement.dao.DoctorMappingDao;
import com.saptarsi.assignement.dao.PharmaMappingDao;
import com.saptarsi.assignement.domain.DoctorMapping;
import com.saptarsi.assignement.domain.PharmaMapping;
import com.saptarsi.assignement.model.UserIdentity;
import com.saptarsi.assignement.model.response.ModelAPIResponse;
import com.saptarsi.assignement.service.AuthService;
import com.saptarsi.assignement.utils.Role;
import com.saptarsi.assignement.utils.TokenToIdentityService;
import com.saptarsi.assignement.utils.UserStatus;

/**
 * @author saptarsichaurashy
 *
 */
public class AuthServiceImpl implements AuthService {
	
	private final static Logger log = LoggerFactory.getLogger(AuthServiceImpl.class);

	@Autowired
	private TokenToIdentityService tokenService;

	@Autowired
	private DoctorMappingDao doctorMappingDao;

	@Autowired
	private PharmaMappingDao pharmaMappingDao;

	/* (non-Javadoc)
	 * @see com.saptarsi.assignement.service.AuthService#addAuthorization()
	 */
	@Override
	public ModelAPIResponse addAuthorization(String token, String reqRole, Long id, List<Long> ids) {
		
		UserIdentity identity = tokenService.getIdentityFromToken(token);
		if(Role.fromValue(reqRole) == Role.PHARMACIST){
			PharmaMapping pharmaMapping = pharmaMappingDao.getByUIdAndPId(identity.getId(), id);
			if(pharmaMapping != null){
				log.info("Mapping already present");
				return null;
			}
			pharmaMapping = getPharmaMapping(identity, id, UserStatus.ACTIVE);
			Long count = pharmaMappingDao.countByPId(id);
			if(count >= AssignmentConstant.MAX_PHARMACIST_LIMIT){
				log.info("Remove some mapping. No more mapping possible");
				return null;
			}
			pharmaMappingDao.save(pharmaMapping);
			return null;
		}
		// If requested role is for doctor
		DoctorMapping doctorMapping = doctorMappingDao.getByPIdAndDId(identity.getId(), id);
		if(doctorMapping == null){
			doctorMapping = getDoctorMapping(identity, id, ids, UserStatus.ACTIVE);
			Long count = doctorMappingDao.countByPId(id);
			if(count >= AssignmentConstant.MAX_DOCTOR_LIMIT){
				log.info("Remove some mapping. No more mapping possible");
				return null;
			}
			doctorMappingDao.save(doctorMapping);
			return null;
		}
		List<Long> docIds = new LinkedList<Long>();
		docIds = doctorMapping.getAuthDId();
		if(docIds.size() + ids.size() >= AssignmentConstant.MAX_SIZE){
			log.info("Delete some ids and try again");
		}
		List<Long> idsNew = new LinkedList<Long>();
		for(Long idm : ids){
			if(docIds.contains(idm) == false)
				idsNew.add(idm);
		}
		docIds.addAll(idsNew);
		doctorMapping.setAuthDId(docIds);
		doctorMappingDao.save(doctorMapping);
		return null;
	}

	/* (non-Javadoc)
	 * @see com.saptarsi.assignement.service.AuthService#removeAuthorization()
	 */
	@Override
	public ModelAPIResponse removeAuthorization(String token, String reqRole, Long id, List<Long> ids) {
		
		UserIdentity identity = tokenService.getIdentityFromToken(token);
		if(Role.fromValue(reqRole) == Role.PHARMACIST){
			PharmaMapping pharmaMapping = pharmaMappingDao.getByUIdAndPId(identity.getId(), id);
			if(pharmaMapping == null){
				log.info("Mapping absent");
				return null;
			}
			pharmaMappingDao.delete(pharmaMapping);
			return null;
		}
		// If role is of doctor
		//Remove an entire list
		DoctorMapping doctorMapping = doctorMappingDao.getByPIdAndDId(identity.getId(), id);
		if(doctorMapping == null){
			log.info("Mapping absent");
			return null;
		}		
		if(ids == null || ids.size() == 0){
			doctorMappingDao.delete(doctorMapping);
		}
		List<Long> authList = doctorMapping.getAuthDId();
		for(Long idm : ids){
			authList.remove(idm);
		}
		doctorMapping.setAuthDId(authList);
		doctorMappingDao.save(doctorMapping);
		return null;
	}

	/* (non-Javadoc)
	 * @see com.saptarsi.assignement.service.AuthService#removeFullAuthorization()
	 */
	@Override
	public ModelAPIResponse removeFullAuthorization(String token, String reqRole, Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	private PharmaMapping getPharmaMapping(UserIdentity identity, Long id, UserStatus userStatus){
		
		PharmaMapping pharmaMapping = new PharmaMapping();
		pharmaMapping.setUId(identity.getId());
		pharmaMapping.setPId(id);
		pharmaMapping.setStatus(userStatus);
		return pharmaMapping;
	}
	
	private DoctorMapping getDoctorMapping(UserIdentity identity, Long id, List<Long> ids, UserStatus userStatus){
		
		DoctorMapping doctorMapping =new DoctorMapping();
		doctorMapping.setAuthDId(ids);
		doctorMapping.setPId(identity.getId());
		doctorMapping.setDId(id);
		doctorMapping.setStatus(userStatus);
		return doctorMapping;
	}
}

