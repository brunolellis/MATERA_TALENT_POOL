package io.github.brunolellis.employee.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import io.github.brunolellis.employee.domain.Employee;
import io.github.brunolellis.employee.domain.EmployeeStatus;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

	List<Employee> findAllByStatus(EmployeeStatus status);

	Optional<Employee> findByIdAndStatus(Long id, EmployeeStatus active);

}
