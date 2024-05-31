package com.hm.userservice.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hm.userservice.dao.EligibilityRepository;
import com.hm.userservice.entity.Eligibility;
import com.hm.userservice.service.PeopleService;
import com.hm.userservice.service.StartUpService;

@Service
public class StartUpServiceImpl implements StartUpService {

	@Autowired
	EligibilityRepository eligibilityDao;
	
	@Autowired
	static PeopleService peopleService;
	
	@Override
	public void getAllEligibiltyData() {
		List<Eligibility> eligibiltyData = eligibilityDao.findAll();
		peopleService.allEligibilityData.addAll(eligibiltyData);
		System.out.println("all eligibility data fetched::::::::::::::"+ eligibiltyData.size());
	}

}
