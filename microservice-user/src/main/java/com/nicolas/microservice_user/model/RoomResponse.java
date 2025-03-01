package com.nicolas.microservice_user.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoomResponse {

    private Long id;
    private String roomType;
    private Double price;
    private Long hotelId;
    private String hotelName;
    private String hotelCity;
}
