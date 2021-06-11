package com.firstbase.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.firstbase.exception.ResourceNotFoundException;
import com.firstbase.model.Employee;
import com.firstbase.model.Name;
import com.firstbase.model.Picture;
import com.firstbase.repository.EmployeeRepository;

//defining the business logic  
@Service
public class EmployeeService {
	@Autowired
	EmployeeRepository employeeRepository;

	public EmployeeService(final EmployeeRepository employeeRepository) {
		// TODO Auto-generated constructor stub
		this.employeeRepository = employeeRepository;
	}

//getting all employee record by using the method findaAll() of CrudRepository  
	public List<Employee> getAllEmployees(Integer pageNo, Integer pageSize, String sortBy) {
		Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
		Page<Employee> pagedResult = employeeRepository.findAll(paging);

		if (pagedResult.hasContent()) {
			return pagedResult.getContent();
		} else {
			return null;
		}
	}

//getting a specific record by using the method findById() of CrudRepository  
	public Employee getEmployeeById(int id) {
		try {
			return employeeRepository.findById(id).get();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return null;
	}

//saving a specific record by using the method save() of CrudRepository  
	public void saveOrUpdate(Employee employees) {
		Picture picture = new Picture();
		picture.setEmployee(employees);
		picture.setLarge(employees.getPicture().getLarge());
		picture.setMedium(employees.getPicture().getMedium());
		picture.setThumbnail(employees.getPicture().getThumbnail());
		employees.setPicture(picture);
		Name name = new Name();
		name.setEmployee(employees);
		name.setTitle(employees.getEmployeename().getTitle());
		name.setFname(employees.getEmployeename().getFname());
		name.setLname(employees.getEmployeename().getLname());
		employees.setEmployeename(name);
		employeeRepository.save(employees);
	}

//deleting a specific record by using the method deleteById() of CrudRepository  
	public void delete(int id) {
		employeeRepository.deleteById(id);
	}

//updating a record  
	public ResponseEntity<Employee> update(Employee employees, int employeeid) throws ResourceNotFoundException {
		Employee employee = employeeRepository.findById(employeeid)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: " + employeeid));
		updateEmployee(employee, employees);
		final Employee updatedEmployee = employeeRepository.save(employee);
		return ResponseEntity.ok(updatedEmployee);
	}

	private void updateEmployee(Employee employee, Employee employees) {
		Optional<Employee> optionalEmp = Optional.of(employees);
		Optional<Name> optionalName = Optional.of(employees.getEmployeename());
		Optional<Picture> optionalPicture = Optional.of(employees.getPicture());
		employee.getEmployeename().setFname(employees.getEmployeename().getFname());
		employee.getEmployeename().setLname(employees.getEmployeename().getLname());
		employee.getEmployeename().setTitle(employees.getEmployeename().getTitle());
		employee.setEmployeetitle(employees.getEmployeetitle());
		employee.getPicture().setLarge(employees.getPicture().getLarge());
		employee.getPicture().setMedium(employees.getPicture().getMedium());
		employee.getPicture().setThumbnail(employees.getPicture().getThumbnail());
	}

}