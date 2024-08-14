package com.hm.userservice.service.impl;

import com.hm.userservice.entity.PGPerson;
import com.hm.userservice.mail.service.SendNotificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.hm.userservice.dao.EmployeeRepository;
import com.hm.userservice.dto.Result;
import com.hm.userservice.entity.Employee;
import com.hm.userservice.service.EmployeeService;

@Service("employeeService") @Slf4j
public class EmployeeServiceImpl implements EmployeeService{
	
	@Autowired
	private EmployeeRepository employeeRepository;
	@Autowired
	private SendNotificationService sendNotificationService;

	@Override
	public Result saveEmployee(Employee employee) {
		Employee emp = employeeRepository.save(employee);
		Result result = new Result();
		result.setData(emp);
		result.setStatusCode(HttpStatus.OK.value());
		result.setSuccesMessage("Employee saved successfully");
		return result;
	}

	@Override
	public Result updateEmployee(Long empId, Employee employee) {
		Result result = new Result();
		try {
			Employee existEmployee = employeeRepository.findById(empId).orElseThrow();
			if (null != existEmployee && null != employee) {
				existEmployee.setCompany(employee.getCompany());
				existEmployee.setDesignation(employee.getDesignation());
				existEmployee.setExperiance(employee.getExperiance());
				Employee updatedEmployee = employeeRepository.save(existEmployee);
				log.info("Sending Email : ");
				if(updatedEmployee != null) {
					sendNotificationService.sendOrderStatusNotificationEmail(1);
				}
				result.setData(existEmployee);
				result.setStatusCode(HttpStatus.OK.value());
				result.setSuccesMessage("Employee updated successfully");
			} else {
				result.setStatusCode(HttpStatus.NOT_FOUND.value());
				result.setSuccesMessage("Employee not found in table");
			}
		} catch (Exception e) {
			System.out.println(e);
			result.setStatusCode(HttpStatus.BAD_REQUEST.value());
			result.setErrorMessage("Employee not updated");
		}
		return result;
	}

}
