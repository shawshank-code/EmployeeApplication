package com.firstbase.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.firstbase.model.Employee;

//repository that extends CrudRepository  
public interface EmployeeRepository extends PagingAndSortingRepository<Employee, Integer> {
}
