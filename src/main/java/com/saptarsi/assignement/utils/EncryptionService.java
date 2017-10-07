package com.saptarsi.assignement.utils;

import java.security.Key;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.saptarsi.assignement.exception.PasswordException;

/**
 * @author saptarsichaurashy
 *
 */

@Component
public class EncryptionService {

	@Value("${com.saptarsi.password.encrypt.key}")
	private String PASSWORD_KEY;

	@Value("${com.saptarsi.password.encrypt.algo}")
	private String ALGO;

	public String encrypt(String Data) {
		try {
			Cipher c = Cipher.getInstance(ALGO);
			c.init(Cipher.ENCRYPT_MODE, generateKey());
			byte[] encVal = c.doFinal(Data.getBytes());
			return Base64.getEncoder().encodeToString(encVal);
		} catch (Exception e) {
			throw new PasswordException("Password Encryption Failed");
		}
	}

	public String decrypt(String encryptedData) {
		try {
			byte[] decVal = Base64.getDecoder().decode(encryptedData);
			Cipher c = Cipher.getInstance(ALGO);
			c.init(Cipher.DECRYPT_MODE, generateKey());
			return new String(c.doFinal(decVal));
		} catch (Exception e) {
			throw new PasswordException("Password Decryption Exception");
		}
	}

	private Key generateKey() throws Exception {
		Key key = new SecretKeySpec(PASSWORD_KEY.getBytes(), ALGO);
		return key;
	}
}
