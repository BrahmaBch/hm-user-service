package com.hm.userservice.service;

import java.util.List;

import com.hm.userservice.entity.Room;

public interface RoomService {

	List<Room> getAllRooms();
	
	Room addRoom(Room room);
}
