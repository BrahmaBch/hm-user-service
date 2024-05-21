package com.hm.userservice.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hm.userservice.dao.PeopleRepository;
import com.hm.userservice.entity.CreditCardEligibleMembers;
import com.hm.userservice.entity.Eligibility;
import com.hm.userservice.entity.People;
import com.hm.userservice.service.PeopleService;
import com.hm.userservice.task.PeopleUpdateTaskTWO;

@Service("peopleServiceTwo")
public class PeopleServiceImpl_TWO implements PeopleService {

	@Autowired
	private PeopleRepository dao;

	@Autowired
	private PeopleBulkServiceImpl peopleBulkService;

	@Override
	public List<People> getAllPeople() {
		try {
			List<People> allPeople = dao.findAll();
			List<Eligibility> allEligibility = PeopleService.allEligibilityData;
			List<People> allLoanEligibilPeopleData = new ArrayList<>();
			// Filter allPeople based on loan amount and eligibility
			allLoanEligibilPeopleData = allPeople.stream().filter(people -> allEligibility.stream()
					.anyMatch(eligibility -> people.getLoanAmmount().equals(eligibility.getLoanAmmount())
							&& people.getLocation().equalsIgnoreCase(eligibility.getLocation())
							&& people.getLoanEligibity().contentEquals(eligibility.getLoanEligibity())
							&& people.getCreditCardEligibility() != null && people.getCreditCardEligibility() != 1))
					.peek(people -> people.setCreditCardEligibility(1)) // Set credit card eligibility
					.collect(Collectors.toList()); // Collect the filtered People into a list

			if (!allLoanEligibilPeopleData.isEmpty()) {
				System.out.println("peopleServiceTwo :: updateCardEligibiltyPeople() called >>>>>>>");
				updateCardEligibilPeoples(allLoanEligibilPeopleData);
			} else {
				System.out.println("No records found.");
			}

			return allLoanEligibilPeopleData;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public void updateCardEligibilPeoples(List<People> allLoanEligibilPeopleData) {
		int batchSize = 30;
		AtomicInteger batchNumber = new AtomicInteger(0);
		List<List<People>> batches = partitionList(allLoanEligibilPeopleData, batchSize);
		// System.out.println("total batches|||||||||||- "+ batches);
		ExecutorService executor = Executors.newFixedThreadPool(20);

		batches.forEach(peopleBatch -> {
			PeopleUpdateTaskTWO task = new PeopleUpdateTaskTWO(this, peopleBatch, batchNumber.incrementAndGet());
			executor.submit(task);
		});
		System.out.println("PeopleUpdateTaskTWO completed >>>>>>>>> ");
		executor.shutdown();

		try {
			if (!executor.awaitTermination(1, TimeUnit.SECONDS)) {
				executor.shutdownNow();
			}
		} catch (InterruptedException e) {
			executor.shutdownNow();
			Thread.currentThread().interrupt();
		}

	}

	private List<List<People>> partitionList(List<People> list, int size) {
		return IntStream.iterate(0, i -> i + size).limit((list.size() + size - 1) / size)
				.mapToObj(i -> list.subList(i, Math.min(i + size, list.size()))).collect(Collectors.toList());
	}

	@Override
	@Transactional
	public void updateCardEligibilPeoples(List<People> peopleList, int batchNumber) {
		if (!peopleList.isEmpty()) {
			try {
				peopleBulkService.updateCardEligibilPeoples(peopleList, batchNumber);
			} catch (Exception e) {
				System.out.println("Error while update Batch " + batchNumber + "!! " + e);
			}
		}
	}

	@Override
	@Transactional
	public void bulkInsertCreditCardEligibleMembers(List<CreditCardEligibleMembers> creditCardEligibleMembersList) {
		if (!creditCardEligibleMembersList.isEmpty()) {
			try {
				peopleBulkService.bulkInsertCreditCardEligibleMembers(creditCardEligibleMembersList);
			} catch (Exception e) {
				System.out.println("Error while inserting CreditCardEligibleMembers: " + e);
			}
		}
	}

	@Override
	public List<People> getGroupingPeople() {
		// TODO Auto-generated method stub
		return null;
	}

}
