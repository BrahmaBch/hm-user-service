package com.hm.userservice.service;

import java.util.ArrayList;
import java.util.List;

import com.hm.userservice.entity.CreditCardEligibleMembers;
import com.hm.userservice.entity.Eligibility;
import com.hm.userservice.entity.People;

public interface PeopleService {
	
	public static List<Eligibility> allEligibilityData = new ArrayList<>();

	List<People> getAllPeople();
	
	void updateCardEligibilPeoples(List<People> peopleList, int batchNumber);
	
	public void bulkInsertCreditCardEligibleMembers(List<CreditCardEligibleMembers> creditCardEligibleMemborsList);

	// second method for demo..........
	void updateCardEligibilPeoples(List<People> allLoanEligibilPeopleData);
	
	List<People> getGroupingPeople();
}
