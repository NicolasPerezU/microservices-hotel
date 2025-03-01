package com.nicolas.microservice_user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReservationResponse {

    private Long id;
    private Long userId;
    private String userName;
    private Long hotelId;
    private String hotelName;
    private String hotelCity;
    private Long roomId;
    private String roomType;
    private Double roomPrice;

}
