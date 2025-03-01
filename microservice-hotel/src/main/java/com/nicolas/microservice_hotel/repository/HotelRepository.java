package com.nicolas.microservice_hotel.repository;

import com.nicolas.microservice_hotel.entity.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, Long> {

    boolean existsByName(String name);




}
