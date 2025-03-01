package com.nicolas.microservice_user.service;

import com.nicolas.microservice_user.dto.ReservationRequest;
import org.springframework.http.ResponseEntity;

public interface ReservationService {

    public ResponseEntity<?> createReservation(ReservationRequest request);

    public ResponseEntity<?> getReservations();

    public ResponseEntity<?> getReservationById(Long id);

    public ResponseEntity<?> updateReservation(ReservationRequest request, Long id);

    public ResponseEntity<?> deleteReservation(Long id);



}
