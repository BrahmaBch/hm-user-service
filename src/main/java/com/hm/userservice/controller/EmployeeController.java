package com.hm.userservice.controller;

import com.hm.userservice.entity.PGPerson;
import com.hm.userservice.exception.CustomException;
import com.hm.userservice.exception.EmployeeNotFoundException;
import com.hm.userservice.exception.InvalidEmployeeDataException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.hm.userservice.dto.Result;
import com.hm.userservice.entity.Employee;
import com.hm.userservice.service.EmployeeService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/v2/employee")
public class EmployeeController {
	
	@Autowired
	private EmployeeService employeeService;

	@PostMapping("/save-employee")
	public ResponseEntity<Result> saveEmployee(@RequestBody Employee employee) {
		log.info("<<<<<<<<<<<  start saveEmployee() ");
		try {
			// Attempt to save employee and return the result
			Result result = employeeService.saveEmployee(employee);
			log.info(">>>>>>>>> end saveEmployee() ");
			return new ResponseEntity<>(result, HttpStatus.OK);
		} catch (CustomException e) {
			log.error("Unexpected error: " + e.getMessage());
			return new ResponseEntity<>(new Result("Error", "An unexpected error occurred"), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/update-employee/{empId}")
	public ResponseEntity<Result> updatePerson(@PathVariable("empId") Long empId, @RequestBody Employee employee){
		Result result = employeeService.updateEmployee(empId, employee);
		return new ResponseEntity<Result>(result, HttpStatus.OK);
	}



}
