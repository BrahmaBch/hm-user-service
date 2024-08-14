package com.hm.userservice.mongoconfig.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hm.userservice.mongoconfig.model.ViewEligibility;
import com.hm.userservice.mongoconfig.service.ViewEligibilityService;

@RestController
@RequestMapping("/mongoconfig/view-eligibility/api")
public class ViewEligibilityController {

	@Autowired
	ViewEligibilityService eligibilityService;
	
	@PostMapping("/add")
	public ResponseEntity<String> addViewEligibility(@RequestBody ViewEligibility viewEligibility) {
	    // Create a new ViewEligibility object and set its properties from the request body
	    ViewEligibility newViewEligibility = new ViewEligibility();
	    newViewEligibility.setViewEligibilityId(viewEligibility.getViewEligibilityId());
	    newViewEligibility.setName(viewEligibility.getName());
	    newViewEligibility.setLoanAmount(viewEligibility.getLoanAmount());
	    newViewEligibility.setLoanEligibility(viewEligibility.getLoanEligibility());
	    newViewEligibility.setLocation(viewEligibility.getLocation());

	    // Call the service to add the new view eligibility
	    eligibilityService.addViewEligibility(newViewEligibility);

	    return new ResponseEntity<>("View eligibility added successfully", HttpStatus.CREATED);
	}

}
