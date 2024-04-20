package com.hm.userservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hm.userservice.entity.Room;
import com.hm.userservice.service.RoomService;

@RestController
@RequestMapping("/api/v2/room")
public class RoomController {
	
	@Autowired
	private RoomService roomService;

	@PostMapping("/save-room")
	public Room saveRoom(@RequestBody Room room) {
		return roomService.addRoom(room);
	}

	@GetMapping("/getAllRooms")
	public ResponseEntity<?> getRooms() {
		List<Room> result = roomService.getAllRooms();
		return new ResponseEntity<List<Room>>(result, HttpStatus.OK);
	}
	
	

}
