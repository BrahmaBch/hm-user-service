package com.hm.userservice.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.hm.userservice.dao.PGPersonRepository;
import com.hm.userservice.dto.Result;
import com.hm.userservice.entity.PGPerson;
import com.hm.userservice.service.PGPersonService;

@Service("pgPersonService")
public class PGPersonServiceImpl implements PGPersonService {

	@Autowired
	private PGPersonRepository personDao;

	@Override
	public Result getPGPersonDetails(Long personId) {
		Optional<PGPerson> person = personDao.findById(personId);
		Result result=new Result();
		if(person.isPresent()) {
			result.setData(person);
			result.setStatusCode(HttpStatus.OK.value());
			result.setSuccesMessage("getting person by person id");
			return result;
		} else {
			result.setStatusCode(HttpStatus.NO_CONTENT.value());
			result.setSuccesMessage("person not found by person id");
			return result;
		}
		
	}

}
