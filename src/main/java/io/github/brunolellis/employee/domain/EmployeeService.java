package io.github.brunolellis.employee.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.brunolellis.employee.exception.EmployeeNotFoundException;
import io.github.brunolellis.employee.repository.EmployeeRepository;

@Service
@Transactional(readOnly = true)
public class EmployeeService {

	private EmployeeRepository repository;

	public EmployeeService(EmployeeRepository repository) {
		this.repository = repository;
	}

	/**
	 * retrieves the specified employee's id. note: the employee should be active.
	 * 
	 * @param id
	 * @return
	 * @throws EmployeeNotFoundException in case it does not exist (or maybe because it was previously disabled) 
	 */
	public Employee findById(Long id) throws EmployeeNotFoundException {
		return repository.findByIdAndStatus(id, EmployeeStatus.ACTIVE)
				.orElseThrow(() -> new EmployeeNotFoundException(id));
	}

	public Page<Employee> findAll(Pageable pagination) {
		return repository.findAllByStatus(EmployeeStatus.ACTIVE, pagination);
	}

	/**
	 * deletes (in fact, disables) an existing employee id.
	 * 
	 * @param id
	 * @throws EmployeeNotFoundException
	 */
	@Transactional(readOnly = false)
	public void delete(Long id) throws EmployeeNotFoundException {
		Employee employee = findById(id);

		employee.disable();
		repository.save(employee);
	}

	/**
	 * creates an active employee
	 * 
	 * @param employee
	 * @return
	 */
	@Transactional(readOnly = false)
	public Employee create(Employee employee) {
		employee.enable();
		return repository.save(employee);
	}

	/**
	 * updates an existing employee. the EmployeeStatus is **not** going to be
	 * updated.
	 * @param id 
	 * 
	 * @param employee
	 * @throws EmployeeNotFoundException
	 */
	@Transactional(readOnly = false)
	public void update(Long id, Employee employee) throws EmployeeNotFoundException {
		Employee updatingEmployee = findById(id);
		
		employee.setId(updatingEmployee.getId());
		employee.enable();
		repository.save(employee);
	}

}
