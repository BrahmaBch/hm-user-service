package com.hm.userservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hm.userservice.dto.AddressDTO;
import com.hm.userservice.dto.PGPersonDTO;
import com.hm.userservice.dto.Result;
import com.hm.userservice.entity.PGPerson;
import com.hm.userservice.service.PGPersonService;
import com.sun.istack.logging.Logger;


@RestController
@RequestMapping("/api/v2/")
public class PGPersonController {
	
	private static final Logger logger = Logger.getLogger(PGPersonController.class);

	@Autowired
	private PGPersonService PGPersonService;

	@GetMapping("/getPersonDetails/{personId}")
	public ResponseEntity<Result> getPGPersonDetailsById(@PathVariable("personId") Long personId) {
		logger.info(">>>>>>>> get person details by person_id: "+ personId);
		Result result = PGPersonService.getPGPersonDetails(personId);
		return new ResponseEntity<Result>(result, HttpStatus.OK);
	}
	
	@PostMapping("/save-person")
	public ResponseEntity<Result> addPerson(@RequestBody PGPersonDTO personDto) {
		logger.info("person :"+ personDto);
		return null;
	}

}
