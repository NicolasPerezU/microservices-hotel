package com.nicolas.microservice_user.repository;

import com.nicolas.microservice_user.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
}
