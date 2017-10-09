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
import org.springframework.stereotype.Service;

import com.saptarsi.assignement.dao.UserDao;
import com.saptarsi.assignement.domain.User;
import com.saptarsi.assignement.errorcodes.ErrorCodes;
import com.saptarsi.assignement.model.UserIdentity;
import com.saptarsi.assignement.model.request.SignupRequest;
import com.saptarsi.assignement.model.response.ModelAPIResponse;
import com.saptarsi.assignement.response.BadResponse;
import com.saptarsi.assignement.response.SuccessResponse;
import com.saptarsi.assignement.service.SignupService;
import com.saptarsi.assignement.utils.EncryptionService;
import com.saptarsi.assignement.utils.MyTokenService;
import com.saptarsi.assignement.utils.Role;

/**
 * @author saptarsichaurashy
 *
 */
@Service
public class SignupserviceImpl implements SignupService {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.saptarsi.assignement.service.SignupService#getSignupResponse(com.
	 * saptarsi.assignement.model.request.SignupRequest)
	 */
	private final static Logger log = LoggerFactory.getLogger(SignupserviceImpl.class);

	@Autowired
	private UserDao userDao;
	
	@Autowired
	private EncryptionService encryptionService;
	
	@Autowired
	private MyTokenService myTokenService;

	@Override
	public ModelAPIResponse getSignupResponse(SignupRequest signupRequest) {

		String userName = signupRequest.getUserData().getUserName();
		ModelAPIResponse modelAPIResponse;
		List<User> users = userDao.findByUserName(userName);
		for(User user : users){
			if(user.getRole() == Role.fromValue(signupRequest.getRole())){
				log.info("User present");
				modelAPIResponse = new BadResponse("User Already Exists", ErrorCodes.OK);
				return modelAPIResponse;
			}
		}
		User user = createUserFromRequest(signupRequest);
		user = userDao.save(user);
		UserIdentity userIdentity = new UserIdentity(user);
		String token = myTokenService.createJwtToken(userIdentity);
		Map<String, Object> params = new HashMap<String, Object>(2);
		params.put("userIdentity", token);
		modelAPIResponse = new SuccessResponse(params, "Signup Successful", ErrorCodes.OK);
		return modelAPIResponse;
	}

	private User createUserFromRequest(SignupRequest signupRequest) {
		User user = new User();
		user.setUserName(signupRequest.getUserData().getUserName());
		user.setPassword(encryptionService.encrypt(signupRequest.getUserData().getPasword()));
		user.setFname(signupRequest.getFname());
		user.setLname(signupRequest.getLname());
		user.setRole(Role.fromValue(signupRequest.getRole()));
		return user;
	}
}
