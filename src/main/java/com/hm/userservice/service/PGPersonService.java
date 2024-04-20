package com.hm.userservice.service;

import java.util.List;

import com.hm.userservice.dto.Result;
import com.hm.userservice.entity.PGPerson;

public interface PGPersonService {

	Result getPGPersonDetails(Long personId);
	
	List<Result> getAllPersons();
	
	Result addPerson(PGPerson pgPerson) throws Exception;
	
	Result updatePerson(Long personId, PGPerson person);
}
