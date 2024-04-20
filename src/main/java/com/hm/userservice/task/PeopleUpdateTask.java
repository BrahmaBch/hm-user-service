package com.hm.userservice.task;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.Callable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hm.userservice.entity.People;
import com.hm.userservice.service.impl.PeopleServiceImpl;

public class PeopleUpdateTask  implements Callable<Result> {
	private static final Logger logger = LoggerFactory.getLogger(PeopleUpdateTask.class);

	private PeopleServiceImpl peopleService;
	private List<People> peopleList;
	private int batchNumber;
	private List<PeopleUpdateTask> failedTasks;
    
    public PeopleUpdateTask(PeopleServiceImpl peopleService, List<People> peopleList, int batchNumber, List<PeopleUpdateTask> failedTasks) {
    	this.peopleService = peopleService;
        this.peopleList = peopleList;
        this.batchNumber = batchNumber;
        this.failedTasks = failedTasks;
    }

	@Override
	public com.hm.userservice.task.Result call() throws Exception {
		try {
			if(peopleList.size() > 0) {
				peopleService.updateCardEligibilPeoples(peopleList, batchNumber);
				logger.debug("size of the list in taks class .............."+ peopleList.size());
			}
		} catch(Exception e) {
			failedTasks.add(this);
		}
		return new Result(LocalDateTime.now().toString());
		
	}
}
