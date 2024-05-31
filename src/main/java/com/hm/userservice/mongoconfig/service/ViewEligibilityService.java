package com.hm.userservice.mongoconfig.service;

import com.hm.userservice.mongoconfig.model.ViewEligibility;

public interface ViewEligibilityService {

	  ViewEligibility getEligibiltyByName(String name) throws Exception;

	void addViewEligibility(ViewEligibility viewEligibility);
}
