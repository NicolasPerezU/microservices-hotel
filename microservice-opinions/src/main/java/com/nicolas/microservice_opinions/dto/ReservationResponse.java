package com.nicolas.microservice_opinions.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReservationResponse {

    private Long reservationId;

    private Long userId;

    private String userName;

    private Long hotelId;

    private String hotelName;

    private Long RoomId;




}
