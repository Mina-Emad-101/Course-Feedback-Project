package com.uni.project.Aspects;

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
import com.uni.project.Exceptions.JWTExpiredException;
import com.uni.project.Models.User;
import com.uni.project.Repositories.UserRepository;
import com.uni.project.Responses.ErrorResponse;
import com.uni.project.Services.JWTService;

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

	@Autowired
	private UserRepository userRepository;

	@Pointcut("@annotation(com.uni.project.Annotations.AuthGuarded)")
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

		Long id;
		try {
			id = this.jwtService.verify(token);
		} catch (JWTExpiredException expiredException) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
					.body(new ErrorResponse(expiredException.getMessage()));
		} catch (JWTVerificationException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("Couldn't Verify Token"));
		}
		User user = this.userRepository.findById(id).orElse(null);
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
