package com.uni.project.Services;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * HashService
 */
@Service
public class HashService {

	private BCryptPasswordEncoder bEncoder;

	public HashService() {
		this.bEncoder = new BCryptPasswordEncoder();
	}

	public String hashString(String string) throws NoSuchAlgorithmException {
		String hashedString = this.bEncoder.encode(string);
		return hashedString;
	}

	public Boolean compareHash(String plain, String hashed) {
		return bEncoder.matches(plain, hashed);
	}
}
