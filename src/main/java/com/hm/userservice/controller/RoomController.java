package com.hm.userservice.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hm.userservice.entity.Room;

@RestController
@RequestMapping("/api/v2/room")
public class RoomController {

	@PostMapping("/save-room")
	public Room saveRoom(Room room) {
		System.out.println(room);
		return room;
	}

}
