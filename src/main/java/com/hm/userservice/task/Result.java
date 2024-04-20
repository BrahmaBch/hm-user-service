package com.hm.userservice.task;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Result {

	public Result(String timestamp) {
		super();
		this.timestamp = timestamp;
	}

	private String timestamp;
}
