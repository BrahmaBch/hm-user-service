package com.hm.userservice.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;

import com.hm.userservice.dao.PeopleRepository;
import com.hm.userservice.entity.Eligibility;
import com.hm.userservice.entity.People;
import com.hm.userservice.service.PeopleService;
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
			List<Eligibility> allEligibility = PeopleService.allEligibilityData;

			// Filter allPeople based on loan amount and eligibility
			List<People> allLoanEligibilPeopleData = allPeople.stream().filter(people -> {
				// Check if there is any eligibility data for the loan amount of the person
				return allEligibility.stream()
						.anyMatch(eligibility -> people.getLoanAmmount().equals(eligibility.getLoanAmmount())
								&& people.getLocation().equalsIgnoreCase(eligibility.getLocation())
								&& people.getLoanEligibity().contentEquals(eligibility.getLoanEligibity())
								&& null != people.getCreditCardEligibility() && people.getCreditCardEligibility() != 1);
			}).collect(Collectors.toList()); // Collect the filtered People into a list

			if (null != allLoanEligibilPeopleData && allLoanEligibilPeopleData.size() > 0) {
				System.out.println("update method called:::::::::::::::::::::::::::::::::::::");
				System.out.println("before update list size ;;;;;; " + allLoanEligibilPeopleData.size());
				updateCardEligibilPeoples(allLoanEligibilPeopleData);
				System.out.println("after update the list size ;;;;;; " + allLoanEligibilPeopleData.size());
				// updateCardEligibility(allLoanEligibilPeopleData);
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

		List<People> peoplesList = new ArrayList<People>();

		
		  for (People people : allLoanEligibilPeopleData) {
		  people.setCreditCardEligibility(1);
		 // System.out.println(" udate card eligibility id : "+ people.getCreditCardEligibility() + " "+ people.getName());
		  peoplesList.add(people); }
		 
		StopWatch timer = new StopWatch();
		timer.start();
		Integer totalObjects = allLoanEligibilPeopleData.size();
		int batchSize = 30;
		System.out.println("Stared updates into database table ::::::::::::::::::::::::::::::::::::::::::::::::");
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
		System.out.println("All Tasks Completed : " + executor.isTerminated() != null ? true: false);
		boolean allTasksCompleted = executor.isTerminated() ? true: false;
		System.out.println("All Tasks Completed : " + allTasksCompleted);
		timer.stop();
		System.out.println("Peoples Records updated Successfully in " + timer.getTotalTimeSeconds() + " Seconds for file ");
	}

	public void updateCardEligibility(List<People> allLoanEligibilPeopleData) {
		// Determine the number of threads to use based on available processors
		int numThreads = Runtime.getRuntime().availableProcessors();
		System.out.println("number of threads : " + numThreads);

		// Create a fixed thread pool with the determined number of threads
		ExecutorService executor = Executors.newFixedThreadPool(numThreads);

		// Create a list to hold the futures for the tasks
		List<Future<Void>> futures;

		try {
			// Submit update tasks to the executor and collect the futures
			futures = executor.invokeAll(createUpdateTasks(allLoanEligibilPeopleData));

			// Wait for all tasks to complete
			for (Future<Void> future : futures) {
				future.get(); // Wait for the task to complete
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// Shutdown the executor after all tasks are completed
			executor.shutdown();
		}
	}

	private List<Callable<Void>> createUpdateTasks(List<People> allLoanEligibilPeopleData) {
		int numThreads = Runtime.getRuntime().availableProcessors();
		int batchSize = allLoanEligibilPeopleData.size() / numThreads;
		System.out.println(" batches size : " + batchSize);
		List<Callable<Void>> tasks = new ArrayList<>();

		// Divide the list into equal parts for each thread
		for (int i = 0; i < numThreads; i++) {
			int startIndex = i * batchSize;
			int endIndex = (i == numThreads - 1) ? allLoanEligibilPeopleData.size() : (i + 1) * batchSize;
			List<People> sublist = allLoanEligibilPeopleData.subList(startIndex, endIndex);
			System.out.println(" sublist size : " + sublist.size());
			tasks.add(new UpdateTask(sublist, batchSize));
		}

		return tasks;
	}

	private class UpdateTask implements Callable<Void> {
		private final List<People> peopleList;
		private Integer batchSize;

		public UpdateTask(List<People> peopleList, Integer batchSize) {
			this.peopleList = peopleList;
			this.batchSize = batchSize;
		}

		@Override
		public Void call() {
			try {
				if (peopleList.size() > 0) {
					peopleBulkService.updateCardEligibilPeoples(peopleList, batchSize);
				}
			} catch (Exception e) {
			}
			return null;
		}
	}

	@Override
	@Transactional
	public void updateCardEligibilPeoples(List<People> peopleList, int batchNumber) {
		try {
			peopleBulkService.updateCardEligibilPeoples(peopleList, batchNumber);
		} catch (Exception e) {
			System.out.println("Error while update Batch " + batchNumber + "!! " + e);
		}
	}
}
