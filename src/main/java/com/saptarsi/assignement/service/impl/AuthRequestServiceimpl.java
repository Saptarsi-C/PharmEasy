/**
 * 
 */
package com.saptarsi.assignement.service.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.saptarsi.assignement.dao.UserDao;
import com.saptarsi.assignement.domain.User;
import com.saptarsi.assignement.exception.CustomRuntimeException;
import com.saptarsi.assignement.model.UserIdentity;
import com.saptarsi.assignement.model.response.ModelAPIResponse;
import com.saptarsi.assignement.notification.Email;
import com.saptarsi.assignement.notification.SQSEmailSender;
import com.saptarsi.assignement.service.AuthRequestService;
import com.saptarsi.assignement.utils.MyTokenService;
import com.saptarsi.assignement.utils.Role;
import com.saptarsi.assignement.utils.TokenToIdentityService;

/**
 * @author saptarsichaurashy
 *
 */
@Service
public class AuthRequestServiceimpl implements AuthRequestService {

	private final static Logger log = LoggerFactory.getLogger(AuthRequestServiceimpl.class);
	
	@Autowired
	private TokenToIdentityService tokenService;
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private MyTokenService myTokenService;
	
    @Autowired
    private SQSEmailSender sqsEmailSender;
	
    @Value("${com.saptarsi.password.email.sender}")
    private String sender;

    @Value("${com.saptarsi.doctor.auth.email.subject}")
    private String subjectDoc;
    
    @Value("${com.saptarsi.pharmacist.auth.email.subject}")
    private String subjectPharma;

    @Value("${com.saptarsi.doctor.email.template}")
    private String templateDoc;
    
    @Value("${com.saptarsi.pharmacist.email.template}")
    private String templatePharma;
    
    
	
	/* (non-Javadoc)
	 * @see com.saptarsi.assignement.service.AuthRequestService#getAuthforDoctor(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public ModelAPIResponse getAuthforDoctor(String token, Long docId, String docName, String patientName) {
		
		UserIdentity identity = tokenService.getIdentityFromToken(token);
		log.info("User Identity : {}", identity.toString());
		//This should only be called by a doctor
		if(Role.fromValue(identity.getRole()) != Role.DOCTOR){
			log.info("This can only be called by a doctor");
			return null;
		}
		else if( docId == null){
			User user = userDao.findByUserNameAndRole(docName, Role.DOCTOR);
			if(user == null){
				log.info("No doctor found");
				return null;
			}
			docId = user.getId();
		}
		// Username of the patient is always required
		User user = userDao.findByUserNameAndRole(patientName, Role.PATIENT);
		if(user == null){
			log.info("No patient found");
			return null;
		}
		Long pId = user.getId();
		patientName = user.getUserName();
		UserIdentity iden = new UserIdentity(user);
		String tok = myTokenService.createJwtToken(iden);
		Boolean res = emailSender(identity.getId().toString(), docId.toString(), Role.DOCTOR.toString(), patientName, tok, templateDoc, subjectDoc);
		return null;
	}

	/* (non-Javadoc)
	 * @see com.saptarsi.assignement.service.AuthRequestService#getAuthForPharmacist(java.lang.String, java.lang.String)
	 */
	@Override
	public ModelAPIResponse getAuthForPharmacist(String token, String patientName) {
		UserIdentity identity = tokenService.getIdentityFromToken(token);
		log.info("User Identity : {}", identity.toString());
		// This should be called by a pharmacist only
		if(Role.fromValue(identity.getRole()) != Role.PHARMACIST){
			log.info("This can only be called by a doctor");
			return null;
		}
		// Username of the patient is always required
		User user = userDao.findByUserNameAndRole(patientName, Role.PATIENT);
		if(user == null){
			log.info("No patient found");
			return null;
		}
		Long pId = user.getId();
		patientName = user.getUserName();
		UserIdentity iden = new UserIdentity(user);
		String tok = myTokenService.createJwtToken(iden);
		Boolean res = emailSender(identity.getId().toString(), null, Role.PHARMACIST.toString(), patientName, tok, templatePharma, subjectPharma);
		return null;
	}
	
	private Boolean emailSender(String id, String reqId, String reqRole, String emailed, String token, String temp, String subject){
		
        Boolean emailTracker;
        Map<String, String> emailParams = new HashMap<String, String>();
        emailParams.put("userId", id);
        emailParams.put("token", token);
        emailParams.put("reqId", reqId);
        emailParams.put("requesterRole", reqRole);
        Email email = new Email("email", emailed, sender, subject, temp, emailParams);
        try {
            emailTracker = sqsEmailSender.send(email).get();
            return emailTracker;
        } catch (InterruptedException ex) {
            throw new CustomRuntimeException(ex.getMessage());
        } catch (ExecutionException ex) {
            return null;
        }
	}

}
