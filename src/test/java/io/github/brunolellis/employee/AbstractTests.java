package io.github.brunolellis.employee;

import org.springframework.beans.factory.annotation.Autowired;
import com.fasterxml.jackson.databind.ObjectMapper;

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
	
}
