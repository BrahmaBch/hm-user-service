package com.hm.userservice.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hm.userservice.entity.People;

@Repository
public interface PeopleRepository extends JpaRepository<People, Integer>{

}
