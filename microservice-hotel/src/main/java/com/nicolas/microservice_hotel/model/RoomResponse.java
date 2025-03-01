package com.nicolas.microservice_hotel.model;

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

    private String nameHotel;

    private String cityHotel;


}
