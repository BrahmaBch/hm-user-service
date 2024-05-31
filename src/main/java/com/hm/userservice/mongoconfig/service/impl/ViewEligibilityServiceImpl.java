package com.hm.userservice.mongoconfig.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.hm.userservice.mongoconfig.model.ViewEligibility;
import com.hm.userservice.mongoconfig.service.ViewEligibilityService;


@Service
public class ViewEligibilityServiceImpl implements ViewEligibilityService {
	
	@Autowired
	MongoTemplate mongoTemplate;
	
	@Override
	public void addViewEligibility(ViewEligibility viewEligibility) {
		
        mongoTemplate.save(viewEligibility);
    }

	@Override
	public ViewEligibility getEligibiltyByName(String name) throws Exception {
		// Create query to find a single document by name
        Query query = new Query(Criteria.where("name").is(name));
        
        // Execute query to find one matching document
        return mongoTemplate.findOne(query, ViewEligibility.class);
	}
	
}
