package com.hm.userservice.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hm.userservice.entity.Room;


@Repository
public interface RoomDao extends JpaRepository<Room, Long>{

}
