package com.hm.userservice.service;

import com.hm.userservice.dto.Result;
import com.hm.userservice.entity.PGPerson;

public interface PGPersonService {

	Result getPGPersonDetails(Long personId);
}
