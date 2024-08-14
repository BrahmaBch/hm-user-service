package com.hm.userservice.examples.service.impl;

import com.hm.userservice.examples.dao.StudentDAO;
import com.hm.userservice.examples.model.StudentModel;
import com.hm.userservice.examples.service.StudentService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentDAO studentDAO;

    public StudentModel saveStudent(StudentModel studentModel) {
        return studentDAO.save(studentModel);
    }


    @Override
    public Long getStudentsCount() {
        Long response = null;
        for (int i = 0; i < 10; i++) {
            try {
                response = performService();
                if(response != null) {
                    System.out.println("Service call response: " + response);
                    return response;
                }
                break;
            } catch (Exception e) {
                System.out.println("Exception occurred: " + e.getMessage());
            }
        }
       // List<StudentModel> studentModelList = studentDAO.findAll();
        return response;
    }

    @CircuitBreaker(name = "studentServiceImpl", fallbackMethod = "fallbackMethod")
    @Retry(name = "studentServiceImpl")
    public Long performService() {
        // Simulate a service call that might fail
        System.out.println("Math.random()   : " + Math.random());
        if (Math.random() < 15) {
            throw new RuntimeException("Service call failed");
        } else {
            return studentDAO.count();
        }
    }

    public String fallbackMethod(Exception ex) {
        // Fallback logic
        return "Fallback response due to: " + ex.getMessage();
    }
}
