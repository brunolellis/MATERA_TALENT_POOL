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

	private EmployeeService service;

	public EmployeesResourceHandler(EmployeeService service) {
		this.service = service;
	}

	public EmployeeResponse findEmployeeById(Long id) {
		Employee employee = service.findById(id);
		EmployeeResponse response = convertToResponse(employee);
		return response;
	}

	public Page<EmployeeResponse> findAllEmployees(Pageable pagination) {
		Page<Employee> employees = service.findAll(pagination);
		List<EmployeeResponse> response = employees.stream()
				.map(this::convertToResponse)
				.collect(Collectors.toList());
		
		return new PageImpl<EmployeeResponse>(response, pagination, employees.getTotalElements());
	}
	
	public EmployeeResponse create(EmployeeRequest request) {
		Employee employee = convertToEmployee(request);
		Employee newEmployee = service.create(employee);
		return convertToResponse(newEmployee);
	}

	public void update(Long id, @Valid EmployeeRequest request) {
		Employee employee = convertToEmployee(request);
		service.update(id, employee);
	}

	public void delete(Long id) {
		service.delete(id);
	}
	
	private Employee convertToEmployee(EmployeeRequest request) {
		return new Employee.Builder()
				.dateOfBirth(request.getDateOfBirth())
				.dateOfEmployment(request.getDateOfEmployment())
				.firstName(request.getFirstName())
				.lastName(request.getLastName())
				.middleInitial(request.getMiddleInitial())
				.build();
	}
	
	private EmployeeResponse convertToResponse(Employee e) {
		return new EmployeeResponse.Builder()
				.dateOfBirth(e.getDateOfBirth())
				.dateOfEmployment(e.getDateOfEmployment())
				.firstName(e.getFirstName())
				.id(e.getId())
				.lastName(e.getLastName())
				.middleInitial(e.getMiddleInitial())
				.status(e.getStatus().toString())
				.build();
	}

}
