package com.uni.project.Services;

import com.uni.project.Exceptions.JWTExpiredException;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

import jakarta.annotation.PostConstruct;

/**
 * JWTService
 */
@Service
public class JWTService {

	@Value("${secret}")
	private String secret;

	@Value("${secret-expiration-minutes}")
	private String secretExpirationMinutes;

	private Algorithm algorithm;

    @PostConstruct
    public void init() {
        this.algorithm = Algorithm.HMAC256(this.secret);
    }

	public String create(Long id) {
		String token = JWT.create().withIssuedAt(Instant.now()).withIssuer("auth0").withClaim("id", id).sign(this.algorithm);
		return token;
	}

	public Long verify(String token) throws JWTVerificationException, JWTExpiredException {
		JWTVerifier verifier = JWT.require(this.algorithm).withIssuer("auth0").withClaimPresence("iat").withClaimPresence("id").build();
		DecodedJWT decodedJWT = verifier.verify(token);
		Date issuedAt = decodedJWT.getIssuedAt();
		Boolean expired = Instant.now().isAfter(issuedAt.toInstant().plus(Long.parseLong(this.secretExpirationMinutes), ChronoUnit.MINUTES));
		if (expired) {
			throw new JWTExpiredException();
		}
		return decodedJWT.getClaim("id").asLong();
	}
}
