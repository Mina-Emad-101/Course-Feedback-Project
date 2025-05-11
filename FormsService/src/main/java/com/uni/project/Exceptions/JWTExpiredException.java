package com.uni.project.Exceptions;

/**
 * JWTExpiredException
 */
public class JWTExpiredException extends Exception {

	public JWTExpiredException() {
		super("Token Expired");
	}
}
