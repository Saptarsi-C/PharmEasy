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

import com.saptarsi.assignement.dao.UserDao;
import com.saptarsi.assignement.domain.User;
import com.saptarsi.assignement.errorcodes.ErrorCodes;
import com.saptarsi.assignement.model.UserIdentity;
import com.saptarsi.assignement.model.request.LoginRequest;
import com.saptarsi.assignement.model.response.ModelAPIResponse;
import com.saptarsi.assignement.response.BadResponse;
import com.saptarsi.assignement.response.SuccessResponse;
import com.saptarsi.assignement.service.LoginService;
import com.saptarsi.assignement.utils.EncryptionService;
import com.saptarsi.assignement.utils.MyTokenService;
import com.saptarsi.assignement.utils.Role;

/**
 * @author saptarsichaurashy
 *
 */
public class LoginServiceImpl implements LoginService {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.saptarsi.assignement.service.LoginService#getLoginresponse(com.
	 * saptarsi.assignement.model.request.LoginRequest)
	 */
	private final static Logger log = LoggerFactory.getLogger(LoginServiceImpl.class);

	@Autowired
	private UserDao userDao;

	@Autowired
	private EncryptionService encryptionService;

	@Autowired
	private MyTokenService myTokenService;

	@Override
	public ModelAPIResponse getLoginresponse(LoginRequest loginRequest) {

		String userName = loginRequest.getUserData().getUserName();
		ModelAPIResponse modelAPIResponse;
		List<User> users = userDao.findByUserName(userName);
		User actualUser = null;
		for (User user : users) {
			if (user.getRole() == Role.fromValue(loginRequest.getRole())) {
				log.info("User present");
				actualUser = user;
				break;
			}
		}
		if(actualUser == null){
			log.info("user not found");
			modelAPIResponse = new BadResponse("User not found", ErrorCodes.OK);
			return modelAPIResponse;
		}
		
		if(actualUser.getPassword().equals(encryptionService.encrypt(loginRequest.getUserData().getPasword())) == false){
			log.info("Wrong Password");
			modelAPIResponse = new BadResponse("Wrong Password", ErrorCodes.OK);
			return modelAPIResponse;
		}
		UserIdentity identity = new UserIdentity(actualUser);
		String token = myTokenService.createJwtToken(identity);
		Map<String, Object> params = new HashMap<String, Object>(2);
		params.put("userIdentity", token);
		modelAPIResponse = new SuccessResponse(params, "Signup Successful", ErrorCodes.OK);
		return modelAPIResponse;
	}

}
