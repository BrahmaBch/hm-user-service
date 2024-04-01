package com.hm.userservice.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hm.userservice.entity.Address;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long>{

}
