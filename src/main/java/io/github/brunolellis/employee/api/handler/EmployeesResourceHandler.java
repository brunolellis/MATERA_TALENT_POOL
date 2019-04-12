package io.github.brunolellis.employee.api.handler;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import io.github.brunolellis.employee.api.EmployeeRequest;
import io.github.brunolellis.employee.api.EmployeeResponse;
import io.github.brunolellis.employee.domain.Employee;
import io.github.brunolellis.employee.domain.EmployeeService;

/**
 * Handles communication between the api and the backend service, converting objects requests <-> model <-> responses.
 */
@Service
public class EmployeesResourceHandler {

	private final EmployeeService service;

	public EmployeesResourceHandler(final EmployeeService service) {
		this.service = service;
	}

	public EmployeeResponse findEmployeeById(final Long id) {
		final Employee employee = service.findById(id);
		return convertToResponse(employee);
	}

	public Page<EmployeeResponse> findAllEmployees(final Pageable pagination) {
		final Page<Employee> employees = service.findAll(pagination);
		final List<EmployeeResponse> response = employees.stream()
				.map(this::convertToResponse)
				.collect(Collectors.toList());
		
		return new PageImpl<>(response, pagination, employees.getTotalElements());
	}
	
	public EmployeeResponse create(final EmployeeRequest request) {
		final Employee employee = convertToEmployee(request);
		final Employee newEmployee = service.create(employee);
		return convertToResponse(newEmployee);
	}

	public void update(final Long id, @Valid final EmployeeRequest request) {
		final Employee employee = convertToEmployee(request);
		service.update(id, employee);
	}

	public void delete(final Long id) {
		service.delete(id);
	}
	
	private Employee convertToEmployee(final EmployeeRequest request) {
		return new Employee.Builder()
				.dateOfBirth(request.getDateOfBirth())
				.dateOfEmployment(request.getDateOfEmployment())
				.firstName(request.getFirstName())
				.lastName(request.getLastName())
				.middleInitial(request.getMiddleInitial())
				.build();
	}
	
	private EmployeeResponse convertToResponse(final Employee e) {
		return new EmployeeResponse.Builder()
				.dateOfBirth(e.getDateOfBirth())
				.dateOfEmployment(e.getDateOfEmployment())
				.firstName(e.getFirstName())
				.id(e.getId())
				.lastName(e.getLastName())
				.middleInitial(e.getMiddleInitial())
				.build();
	}

}
