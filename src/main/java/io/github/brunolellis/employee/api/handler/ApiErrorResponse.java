package io.github.brunolellis.employee.api.handler;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * error structure
 *
 */
public class ApiErrorResponse {

	private List<ApiErrorDetail> errors = new ArrayList<>();

	public ApiErrorResponse(String... messages) {
		this.errors = Stream.of(messages)
				.map(msg -> new ApiErrorDetail(msg))
				.collect(Collectors.toList());
	}
	
	public ApiErrorResponse(List<String> messages) {
		this.errors = messages.stream()
				.map(msg -> new ApiErrorDetail(msg))
				.collect(Collectors.toList());
	}

	public List<ApiErrorDetail> getErrors() {
		return errors;
	}

	public static class ApiErrorDetail {

		private String message;

		public ApiErrorDetail(String message) {
			this.message = message;
		}

		public String getMessage() {
			return message;
		}

		@Override
		public String toString() {
			return "ApiErrorDetail [message=" + message + "]";
		}
	}

	@Override
	public String toString() {
		return "ApiErrorResponse [errors=" + errors + "]";
	}

}
