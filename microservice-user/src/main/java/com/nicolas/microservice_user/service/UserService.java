package com.nicolas.microservice_user.service;

import com.nicolas.microservice_user.dto.UserRequest;
import org.springframework.http.ResponseEntity;

public interface UserService {

    ResponseEntity<?> createUser(UserRequest request);

    ResponseEntity<?> getUsers();

    ResponseEntity<?> getUserById(Long id);

    ResponseEntity<?> updateUser(UserRequest request, Long id);

    ResponseEntity<?> deleteUser(Long id);




}
