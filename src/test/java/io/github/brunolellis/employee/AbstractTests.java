package io.github.brunolellis.employee;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.github.brunolellis.employee.domain.Employee;
import io.github.brunolellis.employee.domain.Employee.Builder;

public abstract class AbstractTests {

	@Autowired
	protected ObjectMapper objectMapper;
	
	public String asJsonString(final Object obj) {
	    try {
	        return objectMapper.writeValueAsString(obj);
	    } catch (final Exception e) {
	        throw new RuntimeException(e);
	    }
	}
	
	protected Builder employeeBuilder() {
		return new Employee.Builder()
        		.dateOfBirth(LocalDate.parse("2000-01-01"))
        		.dateOfEmployment(LocalDate.parse("2010-10-10"))
        		.firstName("John")
        		.lastName("Doe")
        		.middleInitial("M");
	}
	
}
