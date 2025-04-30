package com.uni.project.Aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.uni.project.Responses.ErrorResponse;

/**
 * ExceptionsAspect
 */
@Component
@Aspect
@Order(1)
public class ExceptionsAspect {

	@Around("within(@org.springframework.web.bind.annotation.RestController *)")
	public Object handleException(ProceedingJoinPoint joinPoint){
		try {
			return joinPoint.proceed();
		} catch (Throwable e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse(e.getMessage()));
		}
	}
}
