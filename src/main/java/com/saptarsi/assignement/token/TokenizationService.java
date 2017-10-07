package com.saptarsi.assignement.token;
/**
 * @author saptarsichaurashy
 *
 */
public interface TokenizationService {
	
	default Object createToken(TokenFactory token){
		return null;
	};
	
	public Object verifyToken(TokenFactory token);
	
	public Object decodeToken(String token);
	
}
