package com.firstbase.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.firstbase.exception.ResourceNotFoundException;
import com.firstbase.model.Employee;
import com.firstbase.model.Name;
import com.firstbase.model.Picture;
import com.firstbase.service.EmployeeService;

@RestController
public class EmployeeController {
//autowire the EmployeeService class  
	@Autowired
	EmployeeService employeeService;

//creating a get mapping that retrieves all the employee detail from the database   
	@GetMapping("/employee")
	private ResponseEntity<List<Employee>> getAllEmployees(@RequestParam(defaultValue = "0") Integer pageNo,
			@RequestParam(defaultValue = "5") Integer pageSize,
			@RequestParam(defaultValue = "employeetitle") String sortBy) {
		List<Employee> list = employeeService.getAllEmployees(pageNo, pageSize, sortBy);
		return new ResponseEntity<List<Employee>>(list, HttpStatus.OK);
	}

//creating a get mapping that retrieves the detail of a specific employee  
	@GetMapping("/employee/{employeeid}")
	private Employee getEmplpoyees(@PathVariable("employeeid") int employeeid) {
		return employeeService.getEmployeeById(employeeid);
	}

//creating a delete mapping that deletes a specified employee  
	@DeleteMapping("/employee/{employeeid}")
	private void deleteEmployee(@PathVariable("employeeid") int employeeid) {
		employeeService.delete(employeeid);
	}

//creating post mapping that post the employee detail in the database  
	@PostMapping("/employee")
	private int saveEmployee(@RequestBody(required = false) Employee employee) throws JSONException, IOException {
		createEmployee(createRequest("https://randomuser.me/api/"), employee);
		employeeService.saveOrUpdate(employee);
		return employee.getId();
	}

	private Employee createEmployee(JSONObject jsonObject, Employee employee) {
		JSONArray emparr = jsonObject.getJSONArray("results");
		for (int i = 0; i < emparr.length(); i++) {

			Name name = new Name();
			name.setTitle(emparr.getJSONObject(i).getJSONObject("name").getString("title"));
			name.setFname(emparr.getJSONObject(i).getJSONObject("name").getString("first"));
			name.setLname(emparr.getJSONObject(i).getJSONObject("name").getString("last"));
			employee.setEmployeename(name);
			Picture picture = new Picture();
			picture.setLarge(emparr.getJSONObject(i).getJSONObject("picture").getString("large"));
			picture.setMedium(emparr.getJSONObject(i).getJSONObject("picture").getString("medium"));
			picture.setThumbnail(emparr.getJSONObject(i).getJSONObject("picture").getString("thumbnail"));
			employee.setPicture(picture);
		}
		return employee;
	}

	private JSONObject createRequest(String url) throws IOException, JSONException {

		InputStream is = new URL(url).openStream();
		try {
			BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
			String jsonText = readAll(rd);
			JSONObject json = new JSONObject(jsonText);
			return json;
		} finally {
			is.close();
		}
	}

	private static String readAll(Reader rd) throws IOException {
		StringBuilder sb = new StringBuilder();
		int cp;
		while ((cp = rd.read()) != -1) {
			sb.append((char) cp);
		}
		return sb.toString();
	}

//creating put mapping that updates the employee detail   
	@PutMapping("/employee/{employeeid}")
	private ResponseEntity<Employee> update(@RequestBody Employee employee, @PathVariable int employeeid)
			throws ResourceNotFoundException {

		ResponseEntity<Employee> updatedEmployee = employeeService.update(employee, employeeid);
		return updatedEmployee;
	}
}
