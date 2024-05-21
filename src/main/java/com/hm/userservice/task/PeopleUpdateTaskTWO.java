package com.hm.userservice.task;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hm.userservice.entity.CreditCardEligibleMembers;
import com.hm.userservice.entity.People;
import com.hm.userservice.service.impl.PeopleServiceImpl_TWO;

public class PeopleUpdateTaskTWO implements Callable<Void> {

	private static final Logger logger = LoggerFactory.getLogger(PeopleUpdateTask.class);

	private PeopleServiceImpl_TWO peopleServiceTwo;
	private List<People> batchPeopleList;
	private int batchNumber;

	public PeopleUpdateTaskTWO(PeopleServiceImpl_TWO peopleServiceImpl_TWO, List<People> batchPeopleList,
			int batchNumber) {
		this.peopleServiceTwo = peopleServiceImpl_TWO;
		this.batchPeopleList = batchPeopleList;
		this.batchNumber = batchNumber;
	}

	@Override
	public Void call() throws Exception {
		if (!batchPeopleList.isEmpty()) {
			try {
				peopleServiceTwo.updateCardEligibilPeoples(batchPeopleList, batchNumber);
				// Perform bulk insert to CreditCardEligibleMembers table
				List<CreditCardEligibleMembers> creditCardEligibleMemborsList = transformPeopleToCreditCardEligibleMembors(
						batchPeopleList); // Transform People objects to CreditCardEligibleMembers objects
				peopleServiceTwo.bulkInsertCreditCardEligibleMembers(creditCardEligibleMemborsList);
			} catch (Exception e) {
				// failedTasks.add(this);
			}
		}
		return null;
	}

	private List<CreditCardEligibleMembers> transformPeopleToCreditCardEligibleMembors(List<People> batchPeopleList2) {
		return batchPeopleList2.stream().map(people -> {
			CreditCardEligibleMembers model = new CreditCardEligibleMembers();
			model.setAccountId(people.getAccountId());
			model.setCardNumber(people.getCardNumber());
			model.setLoanAmmount(people.getLoanAmmount());
			model.setName(people.getName());
			return model;
		}).collect(Collectors.toList());
	}

}
