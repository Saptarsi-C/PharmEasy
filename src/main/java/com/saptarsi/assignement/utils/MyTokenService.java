/**
 * 
 */
package com.saptarsi.assignement.utils;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.saptarsi.assignement.exception.InvalidParameterException;
import com.saptarsi.assignement.model.UserIdentity;
import com.saptarsi.assignement.token.JwtConstantParams;
import com.saptarsi.assignement.token.TokenFactory;
import com.saptarsi.assignement.token.TokenizationService;

/**
 * @author saptarsichaurashy
 *
 */
@Service
public class MyTokenService implements TokenizationService {
	
	private final static Logger log = LoggerFactory.getLogger(MyTokenService.class);
	
	@Autowired
	private Gson gson;
	
	@Autowired
	private TokenizationService tokenizationService;
	
	@Value("${com.hotstar.token.algo}")
	private String ALGORITHM;
	
	@Value("${com.hotstar.token.key}")
	private String KEY ;
	
	@Value("${com.hotstar.token.ttlmillis}")
	private String tokenTtlMillis;
	
	public String createJwtToken(UserIdentity userIdentity){
		
		TokenFactory token = new TokenFactory();
		token.setTokenType("JWT");
		Map<String,Object> params = new HashMap<String, Object>(8);
		Long TTLMILLIS = Long.parseLong(tokenTtlMillis);
		try{
			params.put(JwtConstantParams.ALGORITHM, ALGORITHM);
			params.put(JwtConstantParams.KEY, KEY);
			params.put(JwtConstantParams.EXPIRE, MyTokenConstant.EXPIRE);
			params.put(JwtConstantParams.TTLMILLIS, TTLMILLIS);
			params.put(JwtConstantParams.ISSUER, MyTokenConstant.ISSUER);
			params.put(JwtConstantParams.INPUT, gson.toJson(userIdentity));
			token.setData(params);
			log.debug(token.toString());
			return tokenizationService.createToken(token).toString();
		}catch(NullPointerException ex){
			throw new InvalidParameterException(ex.getMessage());
		}
	}

	@Override
	public Object verifyToken(TokenFactory tokenfactory) {
		return tokenizationService.verifyToken(tokenfactory);
	}

	@Override
	public Object decodeToken(String token) {
		return tokenizationService.decodeToken(token);
	}
	
}
