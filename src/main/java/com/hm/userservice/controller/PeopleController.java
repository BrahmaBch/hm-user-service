package com.hm.userservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hm.userservice.entity.People;
import com.hm.userservice.entity.Room;
import com.hm.userservice.service.PeopleService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/v2/people")
public class PeopleController {

	@Autowired
	PeopleService peopleService;
	
	@GetMapping("/getAllPeople")
	public ResponseEntity<?> getPeople() {
		List<People> result = peopleService.getAllPeople();
		return new ResponseEntity<List<People>>(result, HttpStatus.OK);
	}}
