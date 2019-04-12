package io.github.brunolellis.employee.api.handler;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import io.github.brunolellis.employee.exception.EmployeeNotFoundException;

@ControllerAdvice
public class GlobalApiExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(EmployeeNotFoundException.class)
	public ResponseEntity<ApiErrorResponse> handleEmployeeNotFound(EmployeeNotFoundException exception) {
		ApiErrorResponse response = new ApiErrorResponse(exception.getMessage());
		return new ResponseEntity<ApiErrorResponse>(response, HttpStatus.NOT_FOUND);
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException exception,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		
		List<String> messages = exception.getBindingResult().getFieldErrors().stream()
			.map(e -> String.format("Field '%s': %s", e.getField(), e.getDefaultMessage()))
			.collect(Collectors.toList());
		
		ApiErrorResponse response = new ApiErrorResponse(messages);
		return new ResponseEntity<>(response, headers, status);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ApiErrorResponse> handle(Exception exception) {
		ApiErrorResponse response = new ApiErrorResponse(exception.getClass().getName() + ": " + exception.getMessage());
		return new ResponseEntity<ApiErrorResponse>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}

}