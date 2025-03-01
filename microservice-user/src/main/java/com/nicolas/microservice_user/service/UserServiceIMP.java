package com.nicolas.microservice_user.service;

import com.nicolas.microservice_user.dto.ReservationResponse;
import com.nicolas.microservice_user.dto.UserResponse;
import com.nicolas.microservice_user.entity.User;
import com.nicolas.microservice_user.dto.UserRequest;
import com.nicolas.microservice_user.repository.UserRepository;
import jdk.dynalink.linker.LinkerServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceIMP implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ReservationService reservationService;



    @Override
    public ResponseEntity<?> createUser(UserRequest request) {

        if (userRepository.existsByPhone(request.getPhone())) {
            return ResponseEntity.badRequest().body("Phone number already exists");
        }
       User user = new User();

        methodCreateUser(request, user);

        return ResponseEntity.ok(user);

    }

    @Override
    public ResponseEntity<?> getUsers() {
        List<User> users = userRepository.findAll();


        List<ReservationResponse> allReservations = (List<ReservationResponse>) reservationService.getReservations().getBody();


        List<UserResponse> userResponses = users.stream().map(user -> {

            List<ReservationResponse> userReservations = allReservations.stream()
                    .filter(res -> res.getUserId().equals(user.getId()))
                    .collect(Collectors.toList());

            return UserResponse.builder()
                    .id(user.getId())
                    .name(user.getName())
                    .phone(user.getPhone())
                    .status(user.getStatus())
                    .reservations(userReservations)
                    .build();
        }).collect(Collectors.toList());

        return ResponseEntity.ok(userResponses);

    }

    @Override
    public ResponseEntity<?> getUserById(Long id) {

        if (!userRepository.existsById(id)){
            return ResponseEntity.badRequest().body("User not found");
        }

        User user = userRepository.findById(id).get();

        List<ReservationResponse> userReservations = (List<ReservationResponse>) reservationService.getReservations().getBody();

        List<ReservationResponse> reservations = userReservations.stream()
                .filter(res -> res.getUserId().equals(user.getId()))
                .collect(Collectors.toList());

        UserResponse userResponse = UserResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .phone(user.getPhone())
                .status(user.getStatus())
                .reservations(reservations)
                .build();

        return ResponseEntity.ok(userResponse);
    }


    @Override
    public ResponseEntity<?> updateUser(UserRequest request, Long id) {
        if (!userRepository.existsById(id)){
            return ResponseEntity.badRequest().body("User not found");
        }

        User user = userRepository.findById(id).get();

        methodCreateUser(request, user);

        return ResponseEntity.ok(user);

    }

    @Override
    public ResponseEntity<?> deleteUser(Long id) {
                if (!userRepository.existsById(id)){
            return ResponseEntity.badRequest().body("User not found");
        }

        User user = userRepository.findById(id).get();

        user.setStatus("INACTIVE");

        userRepository.save(user);

        return ResponseEntity.ok(user);

    }





    private void methodCreateUser(UserRequest request, User user) {

        user.setName(request.getName());
        user.setPhone(request.getPhone());
        user.setStatus("ACTIVE");
        userRepository.save(user);
    }


}

