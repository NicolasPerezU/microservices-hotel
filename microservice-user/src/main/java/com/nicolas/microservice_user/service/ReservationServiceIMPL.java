package com.nicolas.microservice_user.service;

import com.nicolas.microservice_user.client.HotelClient;
import com.nicolas.microservice_user.entity.Reservation;
import com.nicolas.microservice_user.dto.ReservationRequest;
import com.nicolas.microservice_user.dto.ReservationResponse;
import com.nicolas.microservice_user.model.HotelResponse;
import com.nicolas.microservice_user.model.RoomResponse;
import com.nicolas.microservice_user.repository.ReservationRepository;
import com.nicolas.microservice_user.repository.UserRepository;
import feign.FeignException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReservationServiceIMPL implements ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private UserRepository userRepository;


    private final HotelClient hotelClient;

    public ReservationServiceIMPL(HotelClient hotelClient) {
        this.hotelClient = hotelClient;
    }


    @Override
    public ResponseEntity<?> createReservation(ReservationRequest request) {

        //valida si el usuario existe, si no existe retorna un mensaje de error
        ResponseEntity<String> user = userExist(request);
        if (user != null) return user;

        //valida si el hotel existe, si no existe retorna un mensaje de error
        HotelResponse hotelResponse = getHotelByIdWithCircuitBreaker(request.getHotelId());
        ResponseEntity<String> hotel = hotelExist(hotelResponse);
        if (hotel != null) return hotel;

        //valida si la habitación existe, si no existe retorna un mensaje de error
        RoomResponse roomResponse = getRoomByIdWithCircuitBreaker(request.getRoomId());
        ResponseEntity<String> room = roomExist(roomResponse);
        if (room != null) return room;

        //crea la reserva
        Reservation reservation = Reservation.builder()
                .hotelId(request.getHotelId())
                .roomId(request.getRoomId())
                .user(userRepository.findById(request.getUserId()).get())
                .build();

        reservationRepository.save(reservation);

        ReservationResponse response = convertToReservationResponse(reservation, hotelResponse, roomResponse);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);


    }



    @Override
    public ResponseEntity<?> getReservations() {
        List<Reservation> reservations = reservationRepository.findAll();

        List<ReservationResponse> response = reservations.stream().map(reservation -> {

            HotelResponse hotel = getHotelByIdWithCircuitBreaker(reservation.getHotelId());


            RoomResponse room = getRoomByIdWithCircuitBreaker(reservation.getRoomId());

            return new ReservationResponse(
                    reservation.getId(),
                    reservation.getUser().getId(),
                    reservation.getUser().getName(),
                    hotel.getId(),
                    hotel.getName(),
                    hotel.getCity(),
                    room.getId(),
                    room.getRoomType(),
                    room.getPrice()
            );
        }).collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<?> getReservationById(Long id) {

        if (!reservationRepository.existsById(id)){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Reservation not found");
        }

        Reservation reservation = reservationRepository.findById(id).get();

        HotelResponse hotel = getHotelByIdWithCircuitBreaker(reservation.getHotelId());

        RoomResponse room = getRoomByIdWithCircuitBreaker(reservation.getRoomId());

        ReservationResponse response = convertToReservationResponse(reservation, hotel, room);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Override
    public ResponseEntity<?> updateReservation(ReservationRequest request, Long id) {

        if (!reservationRepository.existsById(id)){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Reservation not found");
        }
        //valida si el usuario existe, si no existe retorna un mensaje de error
        ResponseEntity<String> user = userExist(request);
        if (user != null) return user;

        //valida si el hotel existe, si no existe retorna un mensaje de error
        HotelResponse hotelResponse = getHotelByIdWithCircuitBreaker(request.getHotelId());
        ResponseEntity<String> hotel = hotelExist(hotelResponse);
        if (hotel != null) return hotel;

        //valida si la habitación existe, si no existe retorna un mensaje de error
        RoomResponse roomResponse = getRoomByIdWithCircuitBreaker(request.getRoomId());
        ResponseEntity<String> room = roomExist(roomResponse);
        if (room != null) return room;

        //actualiza la reserva
        Reservation reservation = reservationRepository.findById(id).get();
        reservation.setId(id);
        reservation.setHotelId(request.getHotelId());
        reservation.setUser(userRepository.findById(request.getUserId()).get());

        reservationRepository.save(reservation);


        ReservationResponse response = convertToReservationResponse(reservation, hotelResponse, roomResponse);

        response.setId(reservation.getId());
        response.setHotelId(reservation.getHotelId());
        response.setUserId(reservation.getUser().getId());
        response.setRoomId(reservation.getRoomId());

        return ResponseEntity.status(HttpStatus.OK).body(response);


    }

    @Override
    public ResponseEntity<?> deleteReservation(Long id) {

        if (!reservationRepository.existsById(id)){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Reservation not found");
        }

        reservationRepository.deleteById(id);

        return ResponseEntity.status(HttpStatus.OK).body("Reservation deleted");
    }


    //valida si el usuario existe, si no existe retorna un mensaje de error
    private ResponseEntity<String> userExist(ReservationRequest request) {
        if (!userRepository.existsById(request.getUserId())){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
        return null;
    }

    private static final Logger logger = LoggerFactory.getLogger(ReservationService.class);

    @CircuitBreaker(name = "microservice-hotel", fallbackMethod = "fallbackHotelById")
    public HotelResponse getHotelByIdWithCircuitBreaker(Long hotelId) {
        return hotelClient.getHotelById(hotelId);
    }

    public HotelResponse fallbackHotelById(Long hotelId, Throwable ex) {
        logger.error("Error al obtener hotel con ID {}: {}", hotelId, ex.getMessage());
        return new HotelResponse(hotelId, "Hotel no disponible", "Ciudad no disponible", null);
    }

    @CircuitBreaker(name = "microservice-hotel", fallbackMethod = "fallbackRoomById")
    public RoomResponse getRoomByIdWithCircuitBreaker(Long roomId) {
        return hotelClient.getRoomById(roomId);
    }

    public RoomResponse fallbackRoomById(Long roomId, Throwable ex) {
        logger.error("Error al obtener habitación con ID {}: {}", roomId, ex.getMessage());
        return new RoomResponse(roomId, "Habitación no disponible", 0.0, 0L, "Hotel no disponible", "Ciudad no disponible");
    }
    //valida si el hotel existe, si no existe retorna un mensaje de error
    private static ResponseEntity<String> hotelExist(HotelResponse hotelResponse) {
        if (hotelResponse == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Hotel not found");
        }
        return null;
    }
    //valida si la habitación existe, si no existe retorna un mensaje de error
    private static ResponseEntity<String> roomExist(RoomResponse roomResponse) {
        if (roomResponse == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Room not found");
        }
        return null;
    }

    //convierte la reserva en un objeto ReservationResponse
    private static ReservationResponse convertToReservationResponse(Reservation reservation, HotelResponse hotelResponse, RoomResponse roomResponse) {
        ReservationResponse response = new ReservationResponse(
                reservation.getId(),
                reservation.getUser().getId(),
                reservation.getUser().getName(),
                hotelResponse.getId(),
                hotelResponse.getName(),
                hotelResponse.getCity(),
                roomResponse.getId(),
                roomResponse.getRoomType(),
                roomResponse.getPrice()
        );
        return response;
    }


}



