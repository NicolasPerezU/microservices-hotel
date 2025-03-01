package com.nicolas.microservice_hotel.controller;

import com.nicolas.microservice_hotel.entity.Hotel;
import com.nicolas.microservice_hotel.model.HotelRequest;
import com.nicolas.microservice_hotel.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/hotels")
public class HotelController {


    @Autowired
    private HotelService hotelService;

    @PostMapping
    public ResponseEntity<?> createHotel(@RequestBody HotelRequest hotel){
        return hotelService.createHotel(hotel);
    }

    @GetMapping
    public ResponseEntity<?> getHotels(){
        return hotelService.getHotels();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateHotel(@RequestBody HotelRequest hotel, @PathVariable Long id){
        return hotelService.updateHotel(hotel, id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteHotel(@PathVariable Long id){
        return hotelService.deleteHotel(id);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getHotelById(@PathVariable Long id){
        return hotelService.getHotelById(id);
    }




}
