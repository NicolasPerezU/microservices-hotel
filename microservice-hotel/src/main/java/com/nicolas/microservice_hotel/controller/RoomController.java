package com.nicolas.microservice_hotel.controller;

import com.nicolas.microservice_hotel.model.RoomRequest;
import com.nicolas.microservice_hotel.service.RoomService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rooms")
public class RoomController {

    @Autowired
    private RoomService roomService;

    @PostMapping
    public ResponseEntity<?> createRoom (@RequestBody RoomRequest room){
        return roomService.createRoom(room);
    }

    @GetMapping
    public ResponseEntity<?> getRooms (){
        return roomService.getRooms();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateRoom(@PathVariable Long id, @RequestBody RoomRequest room ){
        return roomService.updateRoom(room,id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRoom (@PathVariable Long id){
        return roomService.deleteRoom(id);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getRoomById(@PathVariable Long id){
        return roomService.getRoomById(id);
    }

}
