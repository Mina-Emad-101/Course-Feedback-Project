package com.uni.projectforms.Aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.uni.projectforms.Models.User;
import com.uni.projectforms.Responses.ErrorResponse;

/**
 * UserRolePermissionsAspect
 */
@Component
@Aspect
@Order(3)
public class UserRolePermissionsAspect {

	@Pointcut("@annotation(com.uni.projectforms.Annotations.AdminsOnly)")
	public void methodAdminsOnly() {
	};

	@Pointcut("@annotation(com.uni.projectforms.Annotations.InstructorsOnly)")
	public void methodInstructorsOnly() {
	};

	@Pointcut("@annotation(com.uni.projectforms.Annotations.StudentsOnly)")
	public void methodStudentsOnly() {
	};

	@Around("com.uni.projectforms.Aspects.AuthAspect.methodAuthGuarded() && methodAdminsOnly()")
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

	@Around("com.uni.projectforms.Aspects.AuthAspect.methodAuthGuarded() && methodInstructorsOnly()")
	public Object isInstructor(ProceedingJoinPoint joinPoint) throws Throwable {
		User loggedInUser = User.getLoggedInUser().orElse(null);
		if (loggedInUser == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
					.body(new ErrorResponse("Must be Authenticated for this action"));
		}

		if (!loggedInUser.isInstructor()) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN)
					.body(new ErrorResponse("User must be Instructor for this action"));
		}

		return joinPoint.proceed();
	}

	@Around("com.uni.projectforms.Aspects.AuthAspect.methodAuthGuarded() && methodStudentsOnly()")
	public Object isStudent(ProceedingJoinPoint joinPoint) throws Throwable {
		User loggedInUser = User.getLoggedInUser().orElse(null);
		if (loggedInUser == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
					.body(new ErrorResponse("Must be Authenticated for this action"));
		}

		if (!loggedInUser.isStudent()) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN)
					.body(new ErrorResponse("User must be Student for this action"));
		}

		return joinPoint.proceed();
	}
}
