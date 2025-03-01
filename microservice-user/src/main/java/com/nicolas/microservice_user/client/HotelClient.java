package com.nicolas.microservice_user.client;

import com.nicolas.microservice_user.model.HotelResponse;
import com.nicolas.microservice_user.model.RoomResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "microservice-hotel", url = "http://localhost:8001")
public interface HotelClient {

    @GetMapping("/hotels/{id}")
    HotelResponse getHotelById(@PathVariable Long id);

    @GetMapping("/rooms/{id}")
    RoomResponse getRoomById(@PathVariable Long id);
}
