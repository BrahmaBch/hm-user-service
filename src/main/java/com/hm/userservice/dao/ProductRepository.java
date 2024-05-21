package com.hm.userservice.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hm.userservice.entity.Product;


@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

}
