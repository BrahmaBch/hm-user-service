package com.hm.userservice.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hm.userservice.entity.Eligibility;

@Repository
public interface EligibilityRepository  extends JpaRepository<Eligibility, Integer>{

}
