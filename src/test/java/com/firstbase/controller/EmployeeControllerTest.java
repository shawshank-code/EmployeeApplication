package com.firstbase.controller;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.aop.target.AbstractPoolingTargetSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.firstbase.EmployeeApplication;
import com.firstbase.model.Employee;
import com.firstbase.model.Name;
import com.firstbase.model.Picture;
import com.firstbase.repository.EmployeeRepository;
import com.firstbase.service.EmployeeService;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@WebMvcTest(EmployeeController.class)
//@SpringBootTest(classes = EmployeeController.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@AutoConfigureMockMvc
//@TestPropertySource(
//		  locations = "classpath:application-integrationtest.properties")
class EmployeeControllerTest {

	@Autowired
    private MockMvc mvc;
	
	@MockBean
	EmployeeService employeeService;
	
	@Autowired 
	private ObjectMapper mapper;
	

	@Test
	public void givenEmployees_whenGetAllEmployees_thenStatus200()
	  throws Exception {
		List<Employee> empList = Arrays.asList(getTestData());
		when(employeeService.getAllEmployees(anyInt(), anyInt(), any())).thenReturn(empList);

		mvc.perform(get("/employee")
			      .contentType(MediaType.APPLICATION_JSON))
			      .andExpect(status().isOk())
			      .andExpect(MockMvcResultMatchers.jsonPath("$.[0].employeename.fname").value("bob"));
	}
	
	@Test
	public void givenEmployees_whenGetEmployeeByIdTest()
	  throws Exception {
		when(employeeService.getEmployeeById(anyInt())).thenReturn(getTestData());

		mvc.perform(get("/employee/1")
			      .contentType(MediaType.APPLICATION_JSON))
			      .andExpect(status().isOk())
			      .andExpect(MockMvcResultMatchers.jsonPath("$.employeename.fname").value("bob"))
			      .andExpect(MockMvcResultMatchers.jsonPath("$.employeename.lname").value("Earl"));
	}
	
	@Test
	public void deleteEmployeeTest()
	  throws Exception {

		mvc.perform(delete("/employee/1")
			      .contentType(MediaType.APPLICATION_JSON))
			      .andExpect(status().isOk());
		
		Mockito.verify(employeeService, times(1)).delete(anyInt());
	}
	
	@Test
	public void saveEmployeeTest()
	  throws Exception {

		String json = mapper.writeValueAsString(getTestData());
		
		mvc.perform(post("/employee").content(json).accept(MediaType.APPLICATION_JSON)
			      .contentType(MediaType.APPLICATION_JSON))
				 			      .andExpect(status().isOk());
		
		Mockito.verify(employeeService, times(1)).saveOrUpdate(any());
	}
	
	@Test
	public void updateEmployeeTest()
	  throws Exception {

		String json = mapper.writeValueAsString(getTestData());
		
		mvc.perform(put("/employee/1").content(json).accept(MediaType.APPLICATION_JSON)
			      .contentType(MediaType.APPLICATION_JSON))
			      .andExpect(status().isOk());
		
		Mockito.verify(employeeService, times(1)).update(any(),anyInt());
	}

	
	public Employee getTestData() {
		Employee e = new Employee();
		e.setEmployeetitle("Dev");
		Name n = new Name();
		n.setId(1L);
		n.setFname("bob");
		n.setLname("Earl");
		n.setTitle("Mr");
		n.setEmployee(e);

		e.setEmployeename(n);

		Picture p = new Picture();
		p.setLarge("large");
		p.setMedium("medium");
		p.setThumbnail("thumbnail");
		p.setEmployee(e);

		e.setPicture(p);
		return e;
	}


}
