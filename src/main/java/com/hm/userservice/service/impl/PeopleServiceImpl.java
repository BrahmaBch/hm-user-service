package com.hm.userservice.service.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;

import com.hm.userservice.dao.PeopleRepository;
import com.hm.userservice.entity.CreditCardEligibleMembers;
import com.hm.userservice.entity.Eligibility;
import com.hm.userservice.entity.People;
import com.hm.userservice.service.PeopleService;
import com.hm.userservice.task.CardEligiblePeopleInsertTask;
import com.hm.userservice.task.PeopleBulkInsertResultTask;
import com.hm.userservice.task.PeopleBulkInsertTask;
import com.hm.userservice.task.PeopleUpdateTask;

@Service("peopleService")
public class PeopleServiceImpl implements PeopleService {

	@Autowired
	PeopleRepository dao;

	@Autowired
	PeopleBulkServiceImpl peopleBulkService;

	@Override
	public List<People> getAllPeople() {
		try {
			List<People> allPeople = dao.findAll();
			System.out.println("all peoples : "+allPeople.size());
			List<Eligibility> allEligibility = PeopleService.allEligibilityData;
			System.out.println("eligibility List::::::::"+allEligibility.size());

			 List<People> allLoanEligibilPeopleData = new ArrayList<>();

			 Map<Integer, String> peopleMap = new HashMap<>();
		        // Loop through each person
		        for (People people : allPeople) {
		            boolean foundEligibility = false;
		            // Check if there is any eligibility data for the loan amount of the person
		            for (Eligibility eligibility : allEligibility) {
		                if (people.getLoanAmmount().equals(eligibility.getLoanAmmount())
		                        && people.getLocation().equalsIgnoreCase(eligibility.getLocation())
		                        && people.getLoanEligibity().equals(eligibility.getLoanEligibity())
		                        && people.getCreditCardEligibility() != null
		                        && people.getCreditCardEligibility() != 1) {
		                	peopleMap.put(people.getPeopleId(), people.getName());
		                    foundEligibility = true;
		                    break;
		                }
		            }
		            if (foundEligibility) {
		                // Update credit card eligibility
		                people.setCreditCardEligibility(1);
		                allLoanEligibilPeopleData.add(people);
		            }
		        }
		        
		        System.out.println("people map::::::::::   " + peopleMap);
		    
			if (null != allLoanEligibilPeopleData && allLoanEligibilPeopleData.size() > 0) {
				updateCardEligibilPeoples(allLoanEligibilPeopleData);
			} else {
				System.out.println("no records forund...........");
			}
			return allLoanEligibilPeopleData;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public void updateCardEligibilPeoples(List<People> allLoanEligibilPeopleData) {
		 
		StopWatch timer = new StopWatch();
		timer.start();
		Integer totalObjects = allLoanEligibilPeopleData.size();
		int batchSize = 100;
		System.out.println("Stared updates into database table ::::::::::::::::::::::::::::::::::::::::::::::::" + timer.getTotalTimeSeconds());
		ExecutorService executor = Executors.newFixedThreadPool(20);
		int batchNumber = 0;
		List<PeopleUpdateTask> taskList = new ArrayList<PeopleUpdateTask>();
		List<PeopleUpdateTask> failedTasks = new ArrayList<PeopleUpdateTask>();
		for (int i = 0; i < totalObjects; i = i + batchSize) {
			if (i + batchSize > totalObjects) {
				batchNumber++;
				List<People> peoplesList1 = allLoanEligibilPeopleData.subList(i, totalObjects);
				PeopleUpdateTask task = new PeopleUpdateTask(this, peoplesList1, batchNumber, failedTasks);
				taskList.add(task);
				break;
			}
			batchNumber++;
			List<People> peoplesList1 = allLoanEligibilPeopleData.subList(i, i + batchSize);
			PeopleUpdateTask task = new PeopleUpdateTask(this, peoplesList1, batchNumber, failedTasks);
			taskList.add(task);
		}
		try {
			executor.invokeAll(taskList);
		} catch (InterruptedException e) {
			System.out.println("Thread Pool Executor intrupted while update people records to DB for file " + e);
		}
		executor = Executors.newFixedThreadPool(5);

		if (failedTasks.size() > 0) {
			System.out.println(failedTasks.size() + "Batche(s) failed  for File Process Retrying in 10 seconds");
			try {
				Thread.sleep(10000);
				executor.invokeAll(failedTasks);
			} catch (InterruptedException e) {
				System.out.println("Thread Pool Executor for Failed Tasks intrurupted while update people records to DB for file "+ e);
			}
		}
		executor.shutdown();
		//shutdown();
		//boolean allTasksCompleted = executor.isTerminated() ? true: false;
		timer.stop();
		System.out.println("Peoples Records updated Successfully in " + timer.getTotalTimeSeconds() + " Seconds for file ");
	}

	
	// below code for Graceful shutdown
	public void shutdown() {
        ExecutorService executor = null;
		executor.shutdown();
        try {
            if (!executor.awaitTermination(10, TimeUnit.SECONDS)) {
                executor.shutdownNow();
                if (!executor.awaitTermination(10, TimeUnit.SECONDS)) {
                    System.err.println("Executor service did not terminate");
                }
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
            Thread.currentThread().interrupt();
        }
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
	public void bulkInsertCreditCardEligibleMembers(List<CreditCardEligibleMembers> creditCardEligibleMemborsList) {

		if (!creditCardEligibleMemborsList.isEmpty()) {
			try {
				peopleBulkService.bulkInsertCreditCardEligibleMembers(creditCardEligibleMemborsList);
			} catch (Exception e) {
				System.out.println("Error while insert CreditCardEligibleMembers" + e);
			}
		}
	
		
	}

	public List<People> getGroupingPeople() {
        List<People> allPeople = dao.findAll(); // Fetch all people from the repository

        // Create a Callable task for bulk insert
        Callable<PeopleBulkInsertResultTask> resultTask = new PeopleBulkInsertTask(dao, allPeople);

        // Asynchronously execute the task
        try {
            PeopleBulkInsertResultTask result = resultTask.call(); // This will execute the task synchronously, you might want to execute it asynchronously
            
            Map<Long, List<People>> peoplesWithAccountIdMap = result.getPeoplesWithAccountIdMap();
            saveCardEligibilePeoples(peoplesWithAccountIdMap);
            return result.getPeopleList(); // Return the list of people from the result task
        } catch (Exception e) {
            e.printStackTrace(); // Handle exceptions properly in your application
            return null; // Return null in case of any error
        }
    }
	
	public void saveCardEligibilePeoples(Map<Long, List<People>> peoplesWithAccountIdMap) {
		// Create a thread pool
        ExecutorService executor = Executors.newCachedThreadPool();

        // Submit the task to the executor
        try {
            Future<Void> futureResult = executor.submit( new CardEligiblePeopleInsertTask(peoplesWithAccountIdMap,peopleBulkService));
            // Wait for the task to complete
            futureResult.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace(); // Handle exceptions properly in your application
        } finally {
            // Shutdown the executor service after task completion
            executor.shutdown();
        }
    }
}
