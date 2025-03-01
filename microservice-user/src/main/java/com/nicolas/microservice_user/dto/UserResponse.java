package com.nicolas.microservice_user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {


    private Long id;
    private String name;
    private Long phone;
    private String status;
    private List<ReservationResponse> reservations;
}
