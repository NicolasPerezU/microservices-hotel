package com.nicolas.microservice_opinions.service;

import com.nicolas.microservice_opinions.client.UserClient;
import com.nicolas.microservice_opinions.dto.OpinionRequest;
import com.nicolas.microservice_opinions.dto.OpinionResponse;
import com.nicolas.microservice_opinions.dto.ReservationResponse;
import com.nicolas.microservice_opinions.dto.UserResponse;
import com.nicolas.microservice_opinions.entity.Opinion;
import com.nicolas.microservice_opinions.repository.OpinionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class OpinionServiceIMP implements OpinionService {

    @Autowired
    private OpinionRepository opinionRepository;

    @Autowired
    private UserClient userClient;

    @Override
    public ResponseEntity<?> createOpinion(OpinionRequest opinion) {

        ReservationResponse reservationResponse = userClient.getReservationById(opinion.getReservationId());
        if (reservationResponse == null){
            return ResponseEntity.badRequest().body("Reservation not found");
        }

        Opinion newOpinion = new Opinion();
        newOpinion.setReservationId(opinion.getReservationId());
        newOpinion.setUserId(reservationResponse.getUserId());
        newOpinion.setRating(opinion.getRating());
        newOpinion.setComment(opinion.getComment());
        newOpinion.setDate(new Date());

        opinionRepository.save(newOpinion);

        OpinionResponse opinionResponse = new OpinionResponse();
        opinionResponse.setId(newOpinion.getId());
        opinionResponse.setUserId(newOpinion.getUserId());
        opinionResponse.setUserName(reservationResponse.getUserName());
        opinionResponse.setReservationId(newOpinion.getReservationId());
        opinionResponse.setHotelId(reservationResponse.getHotelId());
        opinionResponse.setHotelName(reservationResponse.getHotelName());
        opinionResponse.setRoomId(reservationResponse.getRoomId());
        opinionResponse.setRating(newOpinion.getRating());
        opinionResponse.setComment(newOpinion.getComment());
        opinionResponse.setDate(newOpinion.getDate());


        return ResponseEntity.ok(opinionResponse);

    }

    @Override
    public ResponseEntity<?> getOpinions() {
        List<Opinion> opinions = opinionRepository.findAll();

        List<OpinionResponse> opinionResponses = opinions.stream().map(opinion -> {
            ReservationResponse reservationResponse = userClient.getReservationById(opinion.getReservationId());
            OpinionResponse opinionResponse = new OpinionResponse();
            opinionResponse.setId(opinion.getId());
            opinionResponse.setUserId(opinion.getUserId());
            opinionResponse.setUserName(reservationResponse.getUserName());
            opinionResponse.setReservationId(opinion.getReservationId());
            opinionResponse.setHotelId(reservationResponse.getHotelId());
            opinionResponse.setHotelName(reservationResponse.getHotelName());
            opinionResponse.setRoomId(reservationResponse.getRoomId());
            opinionResponse.setRating(opinion.getRating());
            opinionResponse.setComment(opinion.getComment());
            opinionResponse.setDate(opinion.getDate());
            return opinionResponse;
        }).collect(java.util.stream.Collectors.toList());

        return ResponseEntity.ok(opinionResponses);





    }


    @Override
    public ResponseEntity<?> updateOpinion(Long id, OpinionRequest opinionRequest) {
        if (opinionRepository.existsById(id)){
            Opinion opinion = opinionRepository.findById(id).get();
            opinion.setId(id);
            opinion.setDate(new Date());
            opinion.setRating(opinionRequest.getRating());
            opinion.setComment(opinionRequest.getComment());
            opinion.setReservationId(opinionRequest.getReservationId());
            opinion.setUserId(userClient.getReservationById(opinionRequest.getReservationId()).getUserId());
            opinionRepository.save(opinion);

            OpinionResponse opinionResponse = new OpinionResponse();

            opinionResponse.setId(opinion.getId());
            opinionResponse.setUserId(opinion.getUserId());
            opinionResponse.setUserName(userClient.getReservationById(opinion.getReservationId()).getUserName());
            opinionResponse.setReservationId(opinion.getReservationId());
            opinionResponse.setHotelId(userClient.getReservationById(opinion.getReservationId()).getHotelId());
            opinionResponse.setHotelName(userClient.getReservationById(opinionRequest.getReservationId()).getHotelName());
            opinionResponse.setRoomId(userClient.getReservationById(opinionRequest.getReservationId()).getRoomId());
            opinionResponse.setRating(opinion.getRating());
            opinionResponse.setComment(opinion.getComment());
            opinionResponse.setDate(opinion.getDate());

            return ResponseEntity.ok(opinionResponse);
        }

        return ResponseEntity.notFound().build();
    }


    @Override
    public ResponseEntity<?> deleteOpinion(Long id) {
        if (opinionRepository.existsById(id)){
            opinionRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }


}
