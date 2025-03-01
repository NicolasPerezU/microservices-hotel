package com.nicolas.microservice_hotel.model;

import com.nicolas.microservice_hotel.entity.Hotel;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoomRequest {


    private String roomType;

    private Double price;

    private Long hotelId;
}
