package com.firstbase.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.mockito.ArgumentMatchers.*;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.firstbase.exception.ResourceNotFoundException;
import com.firstbase.model.Employee;
import com.firstbase.model.Name;
import com.firstbase.model.Picture;
import com.firstbase.repository.EmployeeRepository;

import ch.qos.logback.core.status.Status;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {

	@Mock
	private EmployeeRepository mockEmployeeRepository;

	private EmployeeService employeeService;

	@BeforeEach
	void setUp() throws Exception {
		employeeService = new EmployeeService(mockEmployeeRepository);
	}

	@Test
	void getEmployeeByIdTest() {
		when(mockEmployeeRepository.findById(anyInt())).thenReturn(Optional.of(getTestData()));

		Employee emp = employeeService.getEmployeeById(1);

		Assert.assertEquals("bob", emp.getEmployeename().getFname());

	}

	/*
	 * @Test void getAllEmployeesTest() { // Pageable mockPageable =
	 * mock(Pageable.class); Pageable pageable = PageRequest.of(1, 1,
	 * Sort.by(getTestData().getEmployeetitle())); // mockStatic(Utils.class);
	 * 
	 * @SuppressWarnings("unchecked") Page<Employee> emp = (Page<Employee>)
	 * getTestData();
	 * 
	 * when(mockEmployeeRepository.findAll(pageable)).thenReturn(emp);
	 * 
	 * List<Employee> empList = employeeService.getAllEmployees(1, 1,
	 * getTestData().getEmployeetitle()); Assert.assertEquals("bob",
	 * empList.get(0).getEmployeename().getFname()); }
	 */

	@Test
	void updateTest() throws ResourceNotFoundException {
		when(mockEmployeeRepository.findById(anyInt())).thenReturn(Optional.of(getTestData()));
		employeeService.update(getTestData(),1);
		Mockito.verify(mockEmployeeRepository, times(1)).save(any());
	}
	
	@Test
	void testExpectedExceptionFail() {
	  assertThrows(ResourceNotFoundException.class, () -> {
		  employeeService.update(null, 0);
	  });
	 
	}
	
	@Test
	void deleteTest() {
		employeeService.delete(1);
		Mockito.verify(mockEmployeeRepository, times(1)).deleteById(anyInt());
	} 
	
	@Test
	void saveOrUpdateTest() {
		employeeService.saveOrUpdate(getTestData());
		Mockito.verify(mockEmployeeRepository, times(1)).save(any());
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
