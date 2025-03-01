package com.nicolas.microservice_hotel.service;

import com.nicolas.microservice_hotel.entity.Hotel;
import com.nicolas.microservice_hotel.entity.Room;
import com.nicolas.microservice_hotel.model.RoomRequest;
import com.nicolas.microservice_hotel.model.RoomResponse;
import com.nicolas.microservice_hotel.repository.HotelRepository;
import com.nicolas.microservice_hotel.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoomServiceIMP implements RoomService{

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private HotelRepository hotelRepository;

    @Override
    public ResponseEntity<?> createRoom(RoomRequest room) {

        // Check if the hotel exists
        ResponseEntity<String> hotelExist = existHotel(room);
        if (hotelExist != null) return hotelExist;

        Hotel hotel = hotelRepository.findById(room.getHotelId()).get();

        Room newRoom = new Room();

        newRoom.setHotel(hotel);
        newRoom.setRoomType(room.getRoomType());
        newRoom.setPrice(room.getPrice());

        roomRepository.save(newRoom);

        return RoomResponseCreate(newRoom);

    }




    @Override
    public ResponseEntity<?> getRooms() {
        List<Room> rooms = roomRepository.findAll();

        List<RoomResponse> response = rooms.stream().map(room ->
                new RoomResponse(
                        room.getId(),
                        room.getRoomType(),
                        room.getPrice(),
                        room.getHotel().getId(),
                        room.getHotel().getName(),
                        room.getHotel().getCity()
                )
        ).toList();

        return ResponseEntity.ok(response);




    }

    @Override
    public ResponseEntity<?> updateRoom(RoomRequest room, Long id) {
        // Check if the hotel exists
        ResponseEntity<String> hotelExist = existHotel(room);
        if (hotelExist != null) return hotelExist;

        Hotel hotel =hotelRepository.findById(room.getHotelId()).get();

        // Check if the room exists
        ResponseEntity<String> roomExist = existRoom(id);
        if (roomExist != null) return roomExist;

        Room newRoom = roomRepository.findById(id).get();
        newRoom.setId(id);
        newRoom.setRoomType(room.getRoomType());
        newRoom.setHotel(hotel);
        newRoom.setPrice(room.getPrice());

        roomRepository.save(newRoom);

        return RoomResponseCreate(newRoom);

    }

    @Override
    public ResponseEntity<?> deleteRoom(Long id) {

        ResponseEntity<String> room_Not_Found = existRoom(id);
        if (room_Not_Found != null) return room_Not_Found;

        roomRepository.deleteById(id);

        return ResponseEntity.status(HttpStatus.OK).body("Room deleted");


    }

    @Override
    public ResponseEntity<?> getRoomById(Long id) {
        Optional<Room> room = roomRepository.findById(id);
        if (room.isPresent()) {
            Room r = room.get();
            RoomResponse roomResponse = new RoomResponse(
                    r.getId(),
                    r.getRoomType(),
                    r.getPrice(),
                    r.getHotel().getId(),
                    r.getHotel().getName(),
                    r.getHotel().getCity()
            );
            return ResponseEntity.ok(roomResponse);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Room not found");
    }


    private ResponseEntity<String> existHotel(RoomRequest room) {
        if (!hotelRepository.existsById(room.getHotelId())){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Hotel not found");
        }
        return null;
    }

    private ResponseEntity<String> existRoom (Long id){
        if (!roomRepository.existsById(id)){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Room not found");
        }
        return null;
    }

    private static ResponseEntity<RoomResponse> RoomResponseCreate(Room newRoom) {
        RoomResponse response = new RoomResponse();
        response.setId(newRoom.getId());
        response.setRoomType(newRoom.getRoomType());
        response.setPrice(newRoom.getPrice());
        response.setHotelId(newRoom.getHotel().getId());
        response.setNameHotel(newRoom.getHotel().getName());
        response.setCityHotel(newRoom.getHotel().getCity());

        return ResponseEntity.ok(response);
    }


}
