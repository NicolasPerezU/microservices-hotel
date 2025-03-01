package com.nicolas.microservice_hotel.service;

import com.nicolas.microservice_hotel.entity.Hotel;
import com.nicolas.microservice_hotel.model.HotelRequest;
import com.nicolas.microservice_hotel.model.HotelResponse;
import com.nicolas.microservice_hotel.model.RoomResponse;
import com.nicolas.microservice_hotel.repository.HotelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HotelServiceIMP implements HotelService{

    @Autowired
    private HotelRepository hotelRepository;

    @Override
    public ResponseEntity<?> createHotel(HotelRequest hotel) {
        String nameHotel = hotel.getName();

        if (hotelRepository.existsByName(nameHotel)) {
            return ResponseEntity.badRequest().body("Hotel already exists");
        }

        Hotel newHotel = Hotel.builder()
                .name(hotel.getName())
                .city(hotel.getCity())
                .build();

        hotelRepository.save(newHotel);

        return convertToHotelResponse(newHotel);

    }


    @Override
    public ResponseEntity<?> getHotels() {
        List<Hotel> hotels = hotelRepository.findAll();

        List<HotelResponse> response = hotels.stream().map(hotel ->
                new HotelResponse(
                        hotel.getId(),
                        hotel.getName(),
                        hotel.getCity(),
                        hotel.getRooms().stream().map(room ->
                                new RoomResponse(
                                        room.getId(),
                                        room.getRoomType(),
                                        room.getPrice(),
                                        room.getHotel().getId(),
                                        room.getHotel().getName(),
                                        room.getHotel().getCity()
                                )
                        ).toList(
                        )
                )).toList();

        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<?> getHotelById(Long id) {
        Optional<Hotel> hotel = hotelRepository.findById(id);
        if (hotel.isPresent()){
            HotelResponse hotelResponse = new HotelResponse();
            hotelResponse.setId(hotel.get().getId());
            hotelResponse.setName(hotel.get().getName());
            hotelResponse.setCity(hotel.get().getCity());
            hotelResponse.setRooms(hotel.get().getRooms().stream().map(room ->
                    new RoomResponse(
                            room.getId(),
                            room.getRoomType(),
                            room.getPrice(),
                            room.getHotel().getId(),
                            room.getHotel().getName(),
                            room.getHotel().getCity()
                    )
            ).toList());
            return ResponseEntity.ok(hotelResponse);
        }
        return ResponseEntity.badRequest().body("Hotel not found");
    }

    @Override
    public ResponseEntity<?> updateHotel(HotelRequest hotel, Long id) {
        if (hotelRepository.existsById(id)){
            Hotel hotelUpdate = new Hotel();
            hotelUpdate.setId(id);
            hotelUpdate.setCity(hotel.getCity());
            hotelUpdate.setName(hotel.getName());
            hotelRepository.save(hotelUpdate);

            return convertToHotelResponse(hotelUpdate);
        }
        return ResponseEntity.badRequest().body("Hotel not found");


    }

    @Override
    public ResponseEntity<?> deleteHotel(Long id) {
        if (hotelRepository.existsById(id)){
            hotelRepository.deleteById(id);
            return ResponseEntity.ok("Hotel deleted");
        }
        return ResponseEntity.badRequest().body("Hotel not found");
    }



    private static ResponseEntity<HotelResponse> convertToHotelResponse(Hotel newHotel) {
        HotelResponse hotelResponse = new HotelResponse();

        hotelResponse.setId(newHotel.getId());
        hotelResponse.setName(newHotel.getName());
        hotelResponse.setCity(newHotel.getCity());

        return ResponseEntity.ok(hotelResponse);
    }


}
