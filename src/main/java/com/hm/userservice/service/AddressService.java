package com.hm.userservice.service;

import com.hm.userservice.dto.AddressDTO;
import com.hm.userservice.entity.Address;

public interface AddressService {

	Address addAddress(AddressDTO addressDTO);
	
}
