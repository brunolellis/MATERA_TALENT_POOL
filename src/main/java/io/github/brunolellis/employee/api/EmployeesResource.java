package io.github.brunolellis.employee.api;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.brunolellis.employee.api.handler.EmployeesResourceHandler;

@RestController
@RequestMapping("/api/v1/employees")
public class EmployeesResource {

	private EmployeesResourceHandler apiHandler;

	public EmployeesResource(EmployeesResourceHandler apiHandler) {
		this.apiHandler = apiHandler;
	}

	@GetMapping
	public ResponseEntity<Page<EmployeeResponse>> findAll(Pageable pagination) {
		Page<EmployeeResponse> response = apiHandler.findAllEmployees(pagination);
		return ResponseEntity.ok(response);
	}

	@PostMapping
	public ResponseEntity<EmployeeResponse> create(@Valid @RequestBody EmployeeRequest newEmployee) {
		EmployeeResponse response = apiHandler.create(newEmployee);
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(response);
	}

	@GetMapping("/{id}")
	public ResponseEntity<EmployeeResponse> findById(@PathVariable("id") Long id) {
		EmployeeResponse response = apiHandler.findEmployeeById(id);
		return ResponseEntity.ok(response);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Void> update(@PathVariable("id") Long id, @Valid @RequestBody EmployeeRequest employee) {
		apiHandler.update(id, employee);
		return ResponseEntity.noContent().build();
	}

	@DeleteMapping("/{id}")
	@RolesAllowed("ADMIN")
	public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
		apiHandler.delete(id);
		return ResponseEntity.noContent().build();
	}

}
