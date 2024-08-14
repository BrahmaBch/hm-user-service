package com.hm.userservice.examples.service;

import com.hm.userservice.examples.model.StudentModel;

public interface StudentService {

    Long getStudentsCount();

    StudentModel saveStudent(StudentModel studentModel);
}
