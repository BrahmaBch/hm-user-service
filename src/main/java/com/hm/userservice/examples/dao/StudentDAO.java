package com.hm.userservice.examples.dao;


import com.hm.userservice.examples.model.StudentModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentDAO extends JpaRepository<StudentModel, Long> {
}
