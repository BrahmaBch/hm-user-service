package com.hm.userservice.service;

import com.hm.userservice.dto.Result;
import com.hm.userservice.entity.Employee;

public interface EmployeeService {

	Result saveEmployee(Employee employee);
}
