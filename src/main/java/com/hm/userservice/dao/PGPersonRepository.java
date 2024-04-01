package com.hm.userservice.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hm.userservice.entity.PGPerson;

@Repository
public interface PGPersonRepository extends JpaRepository<PGPerson, Long> {

}
