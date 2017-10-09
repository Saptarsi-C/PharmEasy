/**
 * 
 */
package com.saptarsi.assignement.utils;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.saptarsi.assignement.model.UserIdentity;
import com.saptarsi.assignement.token.JwtConstantParams;
import com.saptarsi.assignement.token.TokenFactory;
import com.saptarsi.assignement.token.exception.JwtExpiredException;
import com.saptarsi.assignement.token.exception.JwtTamperedException;

/**
 * @author saptarsichaurashy
 *
 */
@Service
public class TokenToIdentityService {
	
	@Value("${com.saptarsi.token.algo}")
	private String ALGORITHM;

	@Value("${com.saptarsi.token.key}")
	private String KEY;
	
	@Autowired
	private MyTokenService myTokenService;
	
	@Autowired
	private Gson gson;

	public UserIdentity getIdentityFromToken(String token){
		
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
		return identity;
	}
	
	private Map<String, Object> createJwtParams(String token) {

		Map<String, Object> params = new HashMap<String, Object>();
		params.put(JwtConstantParams.JWT, token);
		params.put(JwtConstantParams.KEY, KEY);
		params.put(JwtConstantParams.ALGORITHM, ALGORITHM);
		return params;
	}
}
