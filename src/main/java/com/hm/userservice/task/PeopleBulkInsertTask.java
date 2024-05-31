package com.hm.userservice.task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;

import com.hm.userservice.dao.PeopleRepository;
import com.hm.userservice.entity.Eligibility;
import com.hm.userservice.entity.People;
import com.hm.userservice.mongoconfig.model.ViewEligibility;
import com.hm.userservice.mongoconfig.service.ViewEligibilityService;

public class PeopleBulkInsertTask implements Callable<PeopleBulkInsertResultTask> {
    private final PeopleRepository dao;
    private final List<People> peoplesList;
    private final ViewEligibilityService eligibilityService; // Add this line
    private final List<Eligibility> allEligibilityData; // Add this line

    public PeopleBulkInsertTask(PeopleRepository dao, List<People> peoplesList, ViewEligibilityService eligibilityService, List<Eligibility> allEligibilityData) { // Modify this line
        this.dao = dao;
        this.peoplesList = peoplesList;
        this.eligibilityService = eligibilityService; // Initialize eligibilityService
        this.allEligibilityData = allEligibilityData; // Initialize allEligibilityData
    }

    @Override
    public PeopleBulkInsertResultTask call() throws Exception {
        // Fetching all People from repository
        List<People> allPeople = peoplesList;
        Map<Long, List<People>> peoplesWithAccountIdMap = new HashMap<>();
        List<People> updatedPeopleList = new ArrayList<>();
        Map<Integer, Long> accountIdPeopleIdMap = new HashMap<>();
        Integer finalEligibilityId;

        // Grouping People by accountId
        Map<Long, List<People>> groupedPeoplesByAccountIdsMap = allPeople.stream()
                .collect(Collectors.groupingBy(People::getAccountId));

        // Updating CreditCardEligibility for matching People
        groupedPeoplesByAccountIdsMap.forEach((accountId, peopleList) -> {
            List<People> modifiedPeopleList = peopleList.stream()
                    .map(people -> {
                        try {
                            Integer eligibilityId = null;
                            ViewEligibility eligibility = eligibilityService.getEligibiltyByName(people.getName());
                            if (eligibility != null) {
                                eligibilityId = allEligibilityData.stream() // Changed to use allEligibilityData
                                        .filter(aaa -> eligibility.getLocation().equals(aaa.getLocation()) && eligibility.getLoanAmount() == aaa.getLoanAmmount())
                                        .map(Eligibility::getEligibilityId)
                                        .findFirst()
                                        .orElse(null);
                                return people;
                            }
                            return people;
                        } catch (Exception e) {
                            // Handle any exceptions gracefully
                            e.printStackTrace(); // You might want to log the exception instead
                        }
                        return null; // Return null if no match is found or an exception occurs
                    })
                    .filter(people -> people != null) // Filter out null entries
                    .collect(Collectors.toList());

            if (modifiedPeopleList != null) {
            	modifiedPeopleList.forEach(pe -> {
                    if(pe.getCreditCardEligibility() == 0) {
                        pe.setCreditCardEligibility(1);
                        updatedPeopleList.add(pe); // Add the updated person to the new list
                        // Add the updated person to the map
                        peoplesWithAccountIdMap.computeIfAbsent(pe.getAccountId(), k -> new ArrayList<>()).add(pe);
                        accountIdPeopleIdMap.put(pe.getPeopleId(), accountId);
                    }
                });
                System.out.println("accountIdPeopleIdMap Size: "+accountIdPeopleIdMap.size());
            } else {
                updatedPeopleList.addAll(peopleList); // Add unmodified people to the new list
            }
        });

        allPeople = updatedPeopleList;

        return new PeopleBulkInsertResultTask(allPeople, peoplesWithAccountIdMap);
    }
}
