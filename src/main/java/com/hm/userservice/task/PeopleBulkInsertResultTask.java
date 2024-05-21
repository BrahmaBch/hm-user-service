package com.hm.userservice.task;

import java.util.List;
import java.util.Map;

import com.hm.userservice.entity.People;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PeopleBulkInsertResultTask {
	private final List<People> peopleList;
	private final Map<Long, List<People>> peoplesWithAccountIdMap;

	public PeopleBulkInsertResultTask(List<People> peopleList, Map<Long, List<People>> peoplesWithAccountIdMap) {
		this.peopleList = peopleList;
		this.peoplesWithAccountIdMap = peoplesWithAccountIdMap;
	}
}
