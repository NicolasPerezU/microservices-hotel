package com.nicolas.microservice_opinions.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OpinionRequest {


    private Long reservationId;
    private int rating;
    private String comment;
}
