package com.hm.userservice.service;

import com.hm.userservice.dto.Result;
import com.hm.userservice.entity.Employee;
import org.springframework.web.bind.annotation.RequestBody;

public interface EmployeeService {

	Result saveEmployee(Employee employee);

	Result updateEmployee( Long empId, Employee employee);
}
