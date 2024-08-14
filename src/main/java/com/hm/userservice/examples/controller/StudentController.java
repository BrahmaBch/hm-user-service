package com.hm.userservice.examples.controller;


import com.hm.userservice.examples.model.StudentModel;
import com.hm.userservice.examples.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/student")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @PostMapping("/save")
    public ResponseEntity<StudentModel> saveStudent(@RequestBody StudentModel studentModel) {
        StudentModel savedStudent = studentService.saveStudent(studentModel);
        return new ResponseEntity<>(savedStudent, HttpStatus.CREATED);
    }

    @GetMapping("/count")
    public long getStudentCount() {
        return studentService.getStudentsCount();
    }
}
