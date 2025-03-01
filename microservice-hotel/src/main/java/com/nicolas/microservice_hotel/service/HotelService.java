package com.nicolas.microservice_hotel.service;


import com.nicolas.microservice_hotel.entity.Hotel;
import com.nicolas.microservice_hotel.model.HotelRequest;
import org.springframework.http.ResponseEntity;

import java.util.List;


public interface HotelService {

    ResponseEntity<?> createHotel (HotelRequest hotel);

    ResponseEntity<?> getHotels();

    ResponseEntity<?> updateHotel(HotelRequest hotel, Long id);

    ResponseEntity<?> deleteHotel(Long id);

    ResponseEntity<?> getHotelById(Long id);

}
