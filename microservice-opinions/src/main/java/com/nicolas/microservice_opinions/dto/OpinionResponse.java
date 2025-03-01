package com.nicolas.microservice_opinions.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OpinionResponse {

    private Long id;
    private Long userId;
    private String userName;
    private Long reservationId;
    private Long hotelId;
    private String hotelName;
    private Long RoomId;
    private int rating;
    private String comment;
    private Date date;

}
