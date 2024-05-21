package com.hm.userservice.task;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import com.hm.userservice.entity.CreditCardEligiblePeoples;
import com.hm.userservice.entity.People;
import com.hm.userservice.service.impl.PeopleBulkServiceImpl;

public class CardEligiblePeopleInsertTask implements Callable<Void> {
    private final Map<Long, List<People>> peoplesWithAccountIdMap;
    private final PeopleBulkServiceImpl peopleBulkService;

    public CardEligiblePeopleInsertTask(Map<Long, List<People>> peoplesWithAccountIdMap, PeopleBulkServiceImpl peopleBulkService) {
        this.peoplesWithAccountIdMap = peoplesWithAccountIdMap;
        this.peopleBulkService = peopleBulkService;
    }

    @Override
    public Void call() throws Exception {
        try {
            List<CreditCardEligiblePeoples> creditCardEligiblePeopleList = new ArrayList<>();

            // Iterate over the map entries
            for (Map.Entry<Long, List<People>> entry : peoplesWithAccountIdMap.entrySet()) {
                Long accountId = entry.getKey();
                List<People> peopleList = entry.getValue();

                // Insert each person along with the account ID into the list
                for (People person : peopleList) {
                    CreditCardEligiblePeoples creditCardEligiblePerson = new CreditCardEligiblePeoples();
                    creditCardEligiblePerson.setAccountId(accountId); // Set account_id
                    creditCardEligiblePerson.setPeopleId(person.getPeopleId()); // Set people_id
                    creditCardEligiblePerson.setCreditCardEligibility(person.getCreditCardEligibility());
                    creditCardEligiblePerson.setName(person.getName());
                    creditCardEligiblePeopleList.add(creditCardEligiblePerson);
                }
            }

            // Call the service method to perform bulk insert
            bulkInsertCreditCardEligiblePeoples(creditCardEligiblePeopleList);
        } catch (Exception e) {
            e.printStackTrace(); // Handle exceptions properly in your application
        }
        return null;
    }

    // Method to perform bulk insert of CreditCardEligiblePeoples
    private void bulkInsertCreditCardEligiblePeoples(List<CreditCardEligiblePeoples> creditCardEligiblePeopleList) {
        if (!creditCardEligiblePeopleList.isEmpty()) {
            try {
                peopleBulkService.bulkInsertCreditCardEligiblePeopls(creditCardEligiblePeopleList);
            } catch (Exception e) {
                System.out.println("Error while inserting CreditCardEligiblePeoples: " + e);
            }
        }
    }
}
