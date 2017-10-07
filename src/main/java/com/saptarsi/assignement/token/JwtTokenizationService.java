package com.saptarsi.assignement.token;
/**
 * @author saptarsichaurashy
 *
 */
import java.security.Key;
import java.util.Date;
import java.util.Map;

import javax.crypto.spec.SecretKeySpec;

import org.apache.tomcat.util.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.saptarsi.assignement.exception.CustomRuntimeException;
import com.saptarsi.assignement.exception.InvalidParameterException;
import com.saptarsi.assignement.token.exception.InvalidTokenParameterException;
import com.saptarsi.assignement.token.exception.JwtExpiredException;
import com.saptarsi.assignement.token.exception.JwtTamperedException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;

@Component
public class JwtTokenizationService implements TokenizationService {

    
	@Autowired
	private Gson gson;
	
	private final static Logger log = LoggerFactory.getLogger(JwtTokenizationService.class);
    
    private JwtBuilder getBuilder(String input, String key, String algorithm, String issuer) {
        // The JWT signature algorithm we will be using to sign the token
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.valueOf(algorithm);

        byte[] apiKeySecretBytes = key.getBytes();
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

        // set the JWT Claims
        JwtBuilder builder = Jwts.builder().setSubject(input).setIssuer(issuer).signWith(signatureAlgorithm,
                signingKey);
        return builder;
    }

    @Override
    public Object createToken(TokenFactory token) {

        Map<String, Object> params = token.getData();
        try {
            String input = params.get(JwtConstantParams.INPUT).toString();
            String key = params.get(JwtConstantParams.KEY).toString();
            String algorithm = params.get(JwtConstantParams.ALGORITHM).toString();
            String compactJws = "";
            boolean expire = (Boolean) params.get(JwtConstantParams.EXPIRE);
            long ttlMillis = (Long) params.get(JwtConstantParams.TTLMILLIS);
            String issuer = params.get(JwtConstantParams.ISSUER).toString();
            JwtBuilder builder = getBuilder(input, key, algorithm, issuer);
            long nowMillis = System.currentTimeMillis();

            // if it has been specified, let's add the expiration
            if (ttlMillis >= 0 && expire) {
                long expMillis = nowMillis + ttlMillis;
                Date exp = new Date(expMillis);
                builder.setExpiration(exp);
            }
            compactJws = builder.compact();
            log.debug("userIdentity; {}", compactJws);
            return compactJws;
        } catch (NullPointerException ex) {
            throw new InvalidParameterException("INADEQUATE JWT PARAMETERS : " + ex.getMessage());
        } catch (IllegalArgumentException ex) {
            throw new InvalidTokenParameterException("Token Parameters are not valid: " + ex.getMessage());
        }
    }

    @Override
    public Object decodeToken(String token) {
        try {
            String[] parts = token.split("\\.");

            byte[] bytesEncoded = parts[1].getBytes();
            Object payload = new String(Base64.decodeBase64(bytesEncoded));
            log.info("Payload: " + payload);
            JwtTokenModel jwtTokenModel = gson.fromJson(payload.toString(), JwtTokenModel.class);
            return jwtTokenModel.getSub();
        } catch (ArrayIndexOutOfBoundsException | NullPointerException |JsonSyntaxException ex) {
            String params = "JWT NOT VALID : " + ex.getMessage();
            throw new CustomRuntimeException(params);
        }

    }

    @Override
    public Object verifyToken(TokenFactory token) {
        try {
            Map<String, Object> params = token.getData();
            String jwt = params.get(JwtConstantParams.JWT).toString();
            String key = params.get(JwtConstantParams.KEY).toString();
            String algorithm = params.get(JwtConstantParams.ALGORITHM).toString();
            // The JWT signature algorithm we will be using to sign the token
            SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.valueOf(algorithm);
            byte[] apiKeySecretBytes = key.getBytes();
            Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());
            Claims claims = Jwts.parser().setSigningKey(signingKey).parseClaimsJws(jwt).getBody();
            return claims.getSubject();
        } catch (NullPointerException ex) {
            throw new InvalidParameterException("Verify Parameters insufficient : " + ex.getMessage());
        } catch (MalformedJwtException | SignatureException ex) {
            throw new JwtTamperedException("JWT Tampered");
        } catch (ExpiredJwtException ex) {
            throw new JwtExpiredException("JWT Timeout");
        }
    }

}
