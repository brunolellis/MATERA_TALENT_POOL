package io.github.brunolellis.employee.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import io.github.brunolellis.employee.domain.Employee;
import io.github.brunolellis.employee.domain.EmployeeStatus;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

	Page<Employee> findAllByStatus(EmployeeStatus status, Pageable pagination);

	Optional<Employee> findByIdAndStatus(Long id, EmployeeStatus active);

}
