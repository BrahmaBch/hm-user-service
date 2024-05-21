package com.hm.userservice.task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;

import com.hm.userservice.dao.PeopleRepository;
import com.hm.userservice.entity.People;

public class PeopleBulkInsertTask implements Callable<PeopleBulkInsertResultTask> {
	private final PeopleRepository dao;
	private final List<People> peoplesList;

	List<People> allPeople222 = null;

	public PeopleBulkInsertTask(PeopleRepository dao, List<People> peoplesList) {
		this.dao = dao;
		this.peoplesList = peoplesList;
	}

	@Override
	public PeopleBulkInsertResultTask call() throws Exception {
		// Fetching all People from repository
		List<People> allPeople = peoplesList;
		Map<Long, List<People>> peoplesWithAccountIdMap = new HashMap<>();
		List<People> updatedPeopleList = new ArrayList<>();
		Map<Integer, Long> accountIdPeopleIdMap = new HashMap<>();
		
		
		// Grouping People by accountId
		Map<Long, List<People>> groupedPeoplesByAccountIdsMap = allPeople.stream()
				.collect(Collectors.groupingBy(People::getAccountId));

		// Updating CreditCardEligibility for matching People
		groupedPeoplesByAccountIdsMap.forEach((accountId, peopleList) -> {
			boolean matched = peopleList.stream().anyMatch(pe -> pe.getLoanAmmount() == 200
					&& "NO".equals(pe.getLoanEligibity()) && pe.getCreditCardEligibility() == 0);

			if (matched) {
				peopleList.forEach(pe -> {
					if(pe.getCreditCardEligibility() == 0) {
						pe.setCreditCardEligibility(1);
						updatedPeopleList.add(pe); // Add the updated person to the new list
					    // Add the updated person to the map
						 peoplesWithAccountIdMap.computeIfAbsent(pe.getAccountId(), k -> new ArrayList<>()).add(pe);
						//accountIdPeopleIdMap.put(pe.getPeopleId(), accountId);
						//accountIdPeopleIdMap.computeIfAbsent(pe.getPeopleId(), k -> accountId);
					}
				});
				System.out.println("accountIdPeopleIdMap Size: "+accountIdPeopleIdMap.size());
			} else {
				updatedPeopleList.addAll(peopleList); // Add unmodified people to the new list
			}
		});
		
		
		// Logging updated People information
		/*
		 * updatedPeopleList.forEach(pp -> { System.out.println("people: " +
		 * pp.getPeopleId() + " CreditCardEligibility " + pp.getCreditCardEligibility()
		 * + " LoanEligibity : " + pp.getLoanEligibity());
		 * 
		 * });
		 */
		//System.out.println("updatedPeopleList List Size :::::: " + updatedPeopleList.size());

		allPeople = updatedPeopleList;
		/*
		 * allPeople.forEach(p -> { // System.out.println("people: " + pp.getPeopleId()
		 * + " CreditCardEligibility " // + pp.getCreditCardEligibility() +
		 * " LoanEligibity : " + // pp.getLoanEligibity());
		 * 
		 * if (p.getLoanAmmount() == 200) {
		 * System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>> " + p); } });
		 */

		// System.out.println("allPeople List Size ::::: "+allPeople.size());
		return new PeopleBulkInsertResultTask(allPeople, peoplesWithAccountIdMap);
	}
}
