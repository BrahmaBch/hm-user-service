package com.hm.userservice.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.hm.userservice.dao.EmployeeRepository;
import com.hm.userservice.dto.Result;
import com.hm.userservice.entity.Employee;
import com.hm.userservice.service.EmployeeService;

@Service("employeeService")
public class EmployeeServiceImpl implements EmployeeService{
	
	@Autowired
	private EmployeeRepository employeeRepository;

	@Override
	public Result saveEmployee(Employee employee) {
		Employee emp = employeeRepository.save(employee);
		Result result = new Result();
		result.setData(emp);
		result.setStatusCode(HttpStatus.OK.value());
		result.setSuccesMessage("Employee saved successfully");
		return result;
	}

}
