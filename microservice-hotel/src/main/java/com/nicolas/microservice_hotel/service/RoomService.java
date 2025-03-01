package com.nicolas.microservice_hotel.service;

import com.nicolas.microservice_hotel.entity.Room;
import com.nicolas.microservice_hotel.model.RoomRequest;
import org.springframework.http.ResponseEntity;

public interface RoomService {

    ResponseEntity<?> createRoom(RoomRequest room);

    ResponseEntity<?> getRooms ();

    ResponseEntity<?> updateRoom( RoomRequest room, Long id);

    ResponseEntity<?> deleteRoom (Long id);

    ResponseEntity<?> getRoomById(Long id);



}
