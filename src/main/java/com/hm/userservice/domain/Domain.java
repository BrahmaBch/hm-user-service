package com.hm.userservice.domain;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hm.userservice.service.StartUpService;

@Component
public class Domain {

	@Autowired
	StartUpService startUpService;

	@PostConstruct
	public void init() {
		// Initialization logic
		System.out.println("Bean initialized");
		startUpService.getAllEligibiltyData();
	}
}
