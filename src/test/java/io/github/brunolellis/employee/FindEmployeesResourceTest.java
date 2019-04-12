package io.github.brunolellis.employee;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import io.github.brunolellis.employee.domain.Employee;
import io.github.brunolellis.employee.repository.EmployeeRepository;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
public class FindEmployeesResourceTest extends AbstractTests {
	
    @Autowired
    private MockMvc mockMvc;
    
    @Autowired
    private EmployeeRepository repository;
    
    @Before
    public void before() {
    	repository.deleteAll();
    }
    
    @Test
    public void givenAnEmptyDatabaseShouldReturnEmptyContent() throws Exception {
        this.mockMvc.perform(get("/api/v1/employees"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.length()", is(0)));
    }
    
    @Test
    public void givenAnInvalidEmployeeIdShouldReturn404() throws Exception {
        this.mockMvc.perform(get("/api/v1/employees/98979695949321"))
                .andExpect(status().is(404))
                .andExpect(jsonPath("$.errors.length()", is(1)))
                .andExpect(jsonPath("$.errors[0].message", is("Employee 98979695949321 not found")));
    }
    
    @Test
    public void givenAnEmployeeIdShouldReturn200() throws Exception {
        Employee employee = employeeBuilder().build();
        employee = repository.save(employee);
        
    	this.mockMvc.perform(get("/api/v1/employees/{id}", employee.getId()))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.id", is(employee.getId().intValue())))
                .andExpect(jsonPath("$.firstName", is(employee.getFirstName())))
                .andExpect(jsonPath("$.middleInitial", is(employee.getMiddleInitial())))
                .andExpect(jsonPath("$.lastName", is(employee.getLastName())))
                .andExpect(jsonPath("$.dateOfBirth", is(employee.getDateOfBirth().toString())))
                .andExpect(jsonPath("$.dateOfEmployment", is(employee.getDateOfEmployment().toString())));
    }

    
}
