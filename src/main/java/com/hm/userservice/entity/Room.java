package com.hm.userservice.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;


@Data
@Entity
@Table(name = "room", schema = "demo")
public class Room {


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long roomId;

	private Integer roomNumber;

	private Integer bookedBeds;

	private Integer totalBeds;

	private String roomStatus;



}
