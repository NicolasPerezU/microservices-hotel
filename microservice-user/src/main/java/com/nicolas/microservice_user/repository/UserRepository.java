package com.nicolas.microservice_user.repository;

import com.nicolas.microservice_user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {


    boolean existsByPhone(Long phone);
}
