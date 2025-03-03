package com.nicolas.microservice_user.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.nicolas.microservice_user.dto.ReservationRequest;
import com.nicolas.microservice_user.service.ReservationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    @Autowired
    private ReservationService reservationService;



    @PostMapping
    public ResponseEntity<?> createReservation(@Valid @RequestBody ReservationRequest request){
        return reservationService.createReservation(request);
    }

    @GetMapping
    public ResponseEntity<?> getReservations(){
        return reservationService.getReservations();
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> getReservationById(@PathVariable Long id){
        return reservationService.getReservationById(id);
    }





    @PutMapping("/{id}")
    public ResponseEntity<?> updateReservation(@Valid @RequestBody ReservationRequest request,@PathVariable Long id){
        return reservationService.updateReservation(request,id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteReservation(@PathVariable Long id){
        return reservationService.deleteReservation(id);
    }


}
