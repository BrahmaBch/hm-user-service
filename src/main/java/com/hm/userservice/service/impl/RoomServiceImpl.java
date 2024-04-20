package com.hm.userservice.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery.FetchableFluentQuery;
import org.springframework.stereotype.Service;

import com.hm.userservice.dao.RoomDao;
import com.hm.userservice.entity.Room;
import com.hm.userservice.service.RoomService;

@Service
public class RoomServiceImpl implements RoomService {
	
	@Autowired
	private RoomDao roomDao;

	@Override
	public List<Room> getAllRooms() {
		return roomDao.findAll();
	}

	@Override
	public Room addRoom(Room room) {
		return roomDao.save(room);
	}

	
}
