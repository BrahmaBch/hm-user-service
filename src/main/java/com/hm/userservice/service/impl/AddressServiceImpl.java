package com.hm.userservice.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hm.userservice.dao.AddressRepository;
import com.hm.userservice.dto.AddressDTO;
import com.hm.userservice.entity.Address;
import com.hm.userservice.service.AddressService;

@Service("addressService")
public class AddressServiceImpl implements AddressService{
	
	@Autowired
	private AddressRepository addressDao;

	@Override
	public Address addAddress(AddressDTO addressDTO) {
		
		Address address = new Address();
		address.setAddressId(addressDTO.getAddressId());
		address.setCity(addressDTO.getCity());
		address.setStreet(addressDTO.getStreet());
		
		return addressDao.save(address);
	}

}
