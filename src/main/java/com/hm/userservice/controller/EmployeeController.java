package com.hm.userservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
		Result result = employeeService.saveEmployee(employee);
		log.info(">>>>>>>>> end saveEmployee() ");
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	

}
