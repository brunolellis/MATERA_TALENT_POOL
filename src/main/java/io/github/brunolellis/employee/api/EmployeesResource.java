package io.github.brunolellis.employee.api;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/employees")
public class EmployeesResource {

	@GetMapping("/{id}")
	public ResponseEntity<EmployeeResponse> findById(@PathVariable("id") Long id) {
        EmployeeResponse response = new EmployeeResponse();
		return ResponseEntity.ok(response);
	}

	@GetMapping
	public ResponseEntity<List<EmployeeResponse>> findAll() {
		List<EmployeeResponse> response = new ArrayList<>();
		return ResponseEntity.ok(response);
	}
}
