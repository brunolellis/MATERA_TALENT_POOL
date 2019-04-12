package io.github.brunolellis.employee.api.handler;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalApiExceptionHandler.class);

	@ExceptionHandler(EmployeeNotFoundException.class)
	public ResponseEntity<ApiErrorResponse> handleEmployeeNotFound(final EmployeeNotFoundException exception) {
		final ApiErrorResponse response = new ApiErrorResponse(exception.getMessage());
		return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(final MethodArgumentNotValidException exception,
			final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
		
		final List<String> messages = exception.getBindingResult().getFieldErrors().stream()
			.map(e -> String.format("Field '%s': %s", e.getField(), e.getDefaultMessage()))
			.collect(Collectors.toList());
		
		final ApiErrorResponse response = new ApiErrorResponse(messages);
		return new ResponseEntity<>(response, headers, status);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ApiErrorResponse> handle(final Exception exception) {
		LOGGER.error("Unexpected exception caught", exception);

		final ApiErrorResponse response = new ApiErrorResponse(exception.getClass().getName() + ": " + exception.getMessage());
		return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}

}