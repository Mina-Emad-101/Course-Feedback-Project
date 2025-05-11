package com.uni.projectforms.Aspects;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.uni.projectforms.Responses.ErrorResponse;
import com.uni.projectforms.Responses.ValidationErrorResponse;

@ControllerAdvice
public class GlobalExceptionHandler {
	@ExceptionHandler(Exception.class)
	public ResponseEntity<Object> handleExceptions(Exception e) {
		e.printStackTrace();
		if (e instanceof MethodArgumentNotValidException) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ValidationErrorResponse((MethodArgumentNotValidException) e));
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse(e.getMessage()));
	}
}
