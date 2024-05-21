package com.hm.userservice.task;

import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hm.userservice.service.impl.PeopleServiceImpl;

@Component
public class ShutDownHook {

	@Autowired
	private PeopleServiceImpl peopleService;

	@PreDestroy
	public void onDestroy() {
		// Invoke the shutdown method of PeopleServiceImpl
		peopleService.shutdown();
	}
}
