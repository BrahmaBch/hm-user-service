package com.hm.userservice.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.hm.userservice.dao.PGPersonRepository;
import com.hm.userservice.dto.Result;
import com.hm.userservice.entity.PGPerson;
import com.hm.userservice.exception.CustomException;
import com.hm.userservice.service.PGPersonService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("pgPersonService")
public class PGPersonServiceImpl implements PGPersonService {

	@Autowired
	private PGPersonRepository personDao;

	@Override
	public Result getPGPersonDetails(Long personId) {
		Optional<PGPerson> person = personDao.findById(personId);
		Result result = new Result();
		try {
			if (person.isPresent()) {
				result.setData(person);
				result.setStatusCode(HttpStatus.OK.value());
				result.setSuccesMessage("getting person");
			} else {
				result.setStatusCode(HttpStatus.NO_CONTENT.value());
				result.setSuccesMessage("person not found");
			}
		} catch (Exception e) {
			log.error("Error in getAllPersons :: ", e);
			throw new CustomException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return result;
	}

	@Override
	public Result addPerson(PGPerson person) throws Exception{
		log.info("<<< Start addPerson().........");
		Result result = new Result();
		personDao.save(person);
		result.setStatusCode(HttpStatus.OK.value());
		result.setSuccesMessage("person saved successfully");
		result.setData(person);
		log.info(">>>End addPerson() ..........");
		return result;
	}

	@Override
	public Result updatePerson(Long personId, PGPerson person) {
		Result result = new Result();
		try {
			PGPerson existPerson = personDao.findById(personId).orElseThrow();
			if (null != existPerson && null != person) {
				existPerson.setFirstName(person.getFirstName());
				existPerson.setAge(person.getAge());
				existPerson.setLastName(person.getLastName());
				existPerson.getAddress().setCity(person.getAddress().getCity());
				existPerson.getAddress().setStreet(person.getAddress().getStreet());
				personDao.save(existPerson);

				result.setData(existPerson);
				result.setStatusCode(HttpStatus.OK.value());
				result.setSuccesMessage("person updated successfully");
			} else {
				result.setStatusCode(HttpStatus.NOT_FOUND.value());
				result.setSuccesMessage("person not found in table");
			}
		} catch (Exception e) {
			System.out.println(e);
			result.setStatusCode(HttpStatus.BAD_REQUEST.value());
			result.setErrorMessage("person not updated");
		}
		return result;
	}

	@Override
	public List<Result> getAllPersons() {
		List<PGPerson> persons = personDao.findAll();
		List<Result> resultList = new ArrayList<Result>();
		if (!persons.isEmpty()) {
			resultList.add(new Result(resultList));
			resultList.get(0).setStatusCode(HttpStatus.OK.value());
			resultList.get(0).setSuccesMessage("getting person by person id");
		} else {
			resultList.get(0).setStatusCode(HttpStatus.NO_CONTENT.value());
			resultList.get(0).setSuccesMessage("person not found by person id");
			
		}
		return resultList;
	}

}
