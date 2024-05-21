package com.hm.userservice.task;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hm.userservice.entity.CreditCardEligibleMembers;
import com.hm.userservice.entity.People;
import com.hm.userservice.service.impl.PeopleServiceImpl;

public class PeopleUpdateTask implements Callable<Result> {
	private static final Logger logger = LoggerFactory.getLogger(PeopleUpdateTask.class);

	private PeopleServiceImpl peopleService;
	private List<People> peopleList;
	private int batchNumber;
	private List<PeopleUpdateTask> failedTasks;

	public PeopleUpdateTask(PeopleServiceImpl peopleService, List<People> peopleList, int batchNumber,
			List<PeopleUpdateTask> failedTasks) {
		this.peopleService = peopleService;
		this.peopleList = peopleList;
		this.batchNumber = batchNumber;
		this.failedTasks = failedTasks;
	}

	@Override
	public com.hm.userservice.task.Result call() throws Exception {
		if (!peopleList.isEmpty()) {
			try {
					peopleService.updateCardEligibilPeoples(peopleList, batchNumber);
					// Perform bulk insert to CreditCardEligibleMembers table
		            List<CreditCardEligibleMembers> creditCardEligibleMemborsList = transformPeopleToCreditCardEligibleMembors(peopleList); // Transform People objects to CreditCardEligibleMembers objects
		            peopleService.bulkInsertCreditCardEligibleMembers(creditCardEligibleMemborsList); 
			} catch (Exception e) {
				failedTasks.add(this);
			}
		}
		return new Result(LocalDateTime.now().toString());

	}

	public List<CreditCardEligibleMembers> transformPeopleToCreditCardEligibleMembors(List<People> peopleList) {
		return peopleList.stream().map(people -> {
			CreditCardEligibleMembers model = new CreditCardEligibleMembers();
			model.setAccountId(people.getAccountId());
			model.setCardNumber(people.getCardNumber());
			model.setLoanAmmount(people.getLoanAmmount());
			model.setName(people.getName());
			return model;
		}).collect(Collectors.toList());
	}
}
