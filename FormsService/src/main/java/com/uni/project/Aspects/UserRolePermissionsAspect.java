package com.uni.project.Aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.uni.project.Models.User;
import com.uni.project.Responses.ErrorResponse;

/**
 * UserRolePermissionsAspect
 */
@Component
@Aspect
@Order(3)
public class UserRolePermissionsAspect {

	@Pointcut("@annotation(com.uni.project.Annotations.AdminsOnly)")
	public void methodAdminsOnly() {
	};

	@Around("com.uni.project.Aspects.AuthAspect.methodAuthGuarded() && methodAdminsOnly()")
	public Object isAdmin(ProceedingJoinPoint joinPoint) throws Throwable {
		User loggedInUser = User.getLoggedInUser().orElse(null);
		if (loggedInUser == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
					.body(new ErrorResponse("Must be Authenticated for this action"));
		}

		if (!loggedInUser.isAdmin()) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN)
					.body(new ErrorResponse("User must be Admin for this action"));
		}

		return joinPoint.proceed();
	}
}
