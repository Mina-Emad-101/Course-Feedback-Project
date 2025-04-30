package com.uni.project.Services;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.springframework.stereotype.Service;

/**
 * HashService
 */
@Service
public class HashService {

	public String hashString(String string) throws NoSuchAlgorithmException {
		MessageDigest digest = MessageDigest.getInstance("SHA-256");
		byte[] encodedhash = digest.digest(
				string.getBytes(StandardCharsets.UTF_8));
		String hashedString = new String(encodedhash, StandardCharsets.UTF_8);
		return hashedString;
	}
}
