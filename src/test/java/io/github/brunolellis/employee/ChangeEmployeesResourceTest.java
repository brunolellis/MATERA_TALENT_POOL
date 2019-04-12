package io.github.brunolellis.employee;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import io.github.brunolellis.employee.domain.Employee;
import io.github.brunolellis.employee.domain.EmployeeStatus;
import io.github.brunolellis.employee.repository.EmployeeRepository;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
public class ChangeEmployeesResourceTest extends AbstractTests {
	
    @Autowired
    private MockMvc mockMvc;
    
    @Autowired
    private EmployeeRepository repository;
    
    @Before
    public void before() {
    	repository.deleteAll();
    }
    
    @Test
    public void givenAValidEmployeeShouldReturn201() throws Exception {
        final Employee employee = employeeBuilder()
        		.build();
        
        mockMvc.perform(
                post("/api/v1/employees")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(asJsonString(employee)))
                .andExpect(status().is(201))
                .andExpect(jsonPath("$.id", greaterThan(0)))
                .andExpect(jsonPath("$.firstName", is("John")))
                .andExpect(jsonPath("$.middleInitial", is("M")))
                .andExpect(jsonPath("$.lastName", is("Doe")))
                .andExpect(jsonPath("$.dateOfBirth", is("2000-01-01")))
                .andExpect(jsonPath("$.dateOfEmployment", is("2010-10-10")))
                .andExpect(jsonPath("$.status").doesNotExist());
    }

    @Test
    public void givenAnInvalidMiddleInitialNameFieldsShouldReturn400() throws Exception {
        final Employee employee = employeeBuilder()
        		.middleInitial("INVALID MIDDLE INITIAL")
        		.build();
        
        mockMvc.perform(
                post("/api/v1/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(employee)))
                .andExpect(status().is(400))
                .andExpect(jsonPath("$.errors.length()", is(1)))
                .andExpect(jsonPath("$.errors[0].message", is("Field 'middleInitial': size must be between 0 and 1")));
    }
	
    @Test
    public void givenAnInvalidEmployeeWithMissingRequiredFieldsShouldReturn400() throws Exception {
        final Employee employee = employeeBuilder()
        		.dateOfBirth(null)
        		.build();
        
        mockMvc.perform(
                post("/api/v1/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(employee)))
                .andExpect(status().is(400))
                .andExpect(jsonPath("$.errors.length()", is(1)))
                .andExpect(jsonPath("$.errors[0].message", is("Field 'dateOfBirth': must not be null")));
    }
    
    @Test
    public void givenANewNameShouldReturn204() throws Exception {
        Employee employee = employeeBuilder().build();
        employee = repository.save(employee);
        
        Employee jane = employeeBuilder()
        		.firstName("Jane")
        		.build();
        
        mockMvc.perform(
                put("/api/v1/employees/{id}", employee.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(jane)))
                .andExpect(status().is(204))
                .andExpect(jsonPath("$").doesNotExist());
        
        
        jane = repository.findById(employee.getId()).get();
        assertEquals("Jane", jane.getFirstName());
    }
    
    @Test
    @WithMockUser("matera")
    public void givenAnInactiveEmployeeThenAnUpdateAttemptShouldReturn404() throws Exception {
        // given
    	Employee employee = employeeBuilder().build();
        employee = repository.save(employee);
        
        mockMvc.perform(
                delete("/api/v1/employees/{id}", employee.getId()))
                .andExpect(status().is(204))
                .andExpect(jsonPath("$").doesNotExist());
        
        employee = repository.findById(employee.getId()).get();
        assertEquals(EmployeeStatus.INACTIVE, employee.getStatus());
        
        // then
        final Employee jane = employeeBuilder()
        		.firstName("Jane")
        		.build();
        
        mockMvc.perform(
                put("/api/v1/employees/{id}", employee.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(jane)))
                .andExpect(status().is(404))
                .andExpect(jsonPath("$.errors.length()", is(1)))
                .andExpect(jsonPath("$.errors[0].message", is("Employee " + employee.getId() + " not found")));
    }
    
    @Test
    @WithMockUser("matera")
    public void givenADeletedEmployeeShouldDisableIt() throws Exception {
        Employee employee = employeeBuilder().build();
        employee = repository.save(employee);
        
        mockMvc.perform(
                delete("/api/v1/employees/{id}", employee.getId()))
                .andExpect(status().is(204))
                .andExpect(jsonPath("$").doesNotExist());
        
        employee = repository.findById(employee.getId()).get();
        assertEquals(EmployeeStatus.INACTIVE, employee.getStatus());
    }
    
    @Test
    public void givenAnUnauthenticatedRequestShouldReturn401() throws Exception {
        Employee employee = employeeBuilder().build();
        employee = repository.save(employee);
        
        mockMvc.perform(
                delete("/api/v1/employees/{id}", employee.getId()))
                .andExpect(status().is(401))
                .andExpect(jsonPath("$").doesNotExist());
    }
    
}

