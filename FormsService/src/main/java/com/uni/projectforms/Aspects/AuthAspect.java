package com.uni.projectforms.Aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.uni.projectforms.Models.User;
import com.uni.projectforms.Responses.ErrorResponse;
import com.uni.projectforms.Services.JWTService;

import jakarta.servlet.http.HttpServletRequest;

/**
 * AuthAspect
 */
@Component
@Aspect
@Order(2)
public class AuthAspect {

	@Autowired
	private HttpServletRequest request;

	@Autowired
	private JWTService jwtService;

	@Pointcut("@annotation(com.uni.projectforms.Annotations.AuthGuarded)")
	public void methodAuthGuarded() {
	};

	@Around("methodAuthGuarded()")
	public Object isAuthorized(ProceedingJoinPoint joinPoint) throws Throwable {
		String token = this.request.getHeader("Authorization");
		if (token == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
					.body(new ErrorResponse("User Must be Authenticated for this action"));
		}
		token = token.replace("Bearer ", "");

		User user;
		try {
			user = this.jwtService.verify(token);
		} catch (JWTVerificationException verificationException) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
					.body(new ErrorResponse(verificationException.getMessage()));
		}
		if (user == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new ErrorResponse("User Not Found, Please Login Again"));
		}
		User.setLoggedInUser(user);

		Object result = joinPoint.proceed();
		User.resetLoggedInUser();
		return result;
	}
}
