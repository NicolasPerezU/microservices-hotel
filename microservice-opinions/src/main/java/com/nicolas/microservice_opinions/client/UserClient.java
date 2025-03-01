package com.nicolas.microservice_opinions.client;

import com.nicolas.microservice_opinions.dto.ReservationResponse;
import com.nicolas.microservice_opinions.dto.UserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "microservice-users", url = "localhost:8002")
public interface UserClient {

    @GetMapping("/reservations/{id}")
    ReservationResponse getReservationById(@PathVariable Long id);

}
