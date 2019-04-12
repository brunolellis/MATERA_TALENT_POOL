package io.github.brunolellis.employee.api;

import java.time.LocalDate;

public class EmployeeResponse {

	private Long id;
	private String firstName;
	private String middleInitial;
	private String lastName;
	private LocalDate dateOfBirth;
	private LocalDate dateOfEmployment;
	private StatusEnum status;

	public enum StatusEnum {
		ACTIVE, INACTIVE;

		private String value;

		public static StatusEnum fromValue(final String text) {
			for (final StatusEnum b : StatusEnum.values()) {
				if (b.value.equals(text)) {
					return b;
				}
			}
			return null;
		}
	}

	public Long getId() {
		return id;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getMiddleInitial() {
		return middleInitial;
	}

	public String getLastName() {
		return lastName;
	}

	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}

	public LocalDate getDateOfEmployment() {
		return dateOfEmployment;
	}

	public StatusEnum getStatus() {
		return status;
	}

	@Override
	public String toString() {
		return "EmployeeResponse [id=" + id + ", firstName=" + firstName + ", middleInitial=" + middleInitial
				+ ", lastName=" + lastName + ", dateOfBirth=" + dateOfBirth + ", dateOfEmployment=" + dateOfEmployment
				+ ", status=" + status + "]";
	}

	public static class Builder {
		private Long id;
		private String firstName;
		private String middleInitial;
		private String lastName;
		private LocalDate dateOfBirth;
		private LocalDate dateOfEmployment;
		private StatusEnum status;

		public Builder id(final Long id) {
			this.id = id;
			return this;
		}

		public Builder firstName(final String firstName) {
			this.firstName = firstName;
			return this;
		}

		public Builder middleInitial(final String middleInitial) {
			this.middleInitial = middleInitial;
			return this;
		}

		public Builder lastName(final String lastName) {
			this.lastName = lastName;
			return this;
		}

		public Builder dateOfBirth(final LocalDate dateOfBirth) {
			this.dateOfBirth = dateOfBirth;
			return this;
		}

		public Builder dateOfEmployment(final LocalDate dateOfEmployment) {
			this.dateOfEmployment = dateOfEmployment;
			return this;
		}

		public Builder status(final StatusEnum status) {
			this.status = status;
			return this;
		}
		
		public Builder status(final String status) {
			this.status = StatusEnum.fromValue(status);
			return this;
		}

		public EmployeeResponse build() {
			final EmployeeResponse employeeResponse = new EmployeeResponse();
			employeeResponse.id = id;
			employeeResponse.firstName = firstName;
			employeeResponse.middleInitial = middleInitial;
			employeeResponse.lastName = lastName;
			employeeResponse.dateOfBirth = dateOfBirth;
			employeeResponse.dateOfEmployment = dateOfEmployment;
			employeeResponse.status = status;
			return employeeResponse;
		}

	}
}
