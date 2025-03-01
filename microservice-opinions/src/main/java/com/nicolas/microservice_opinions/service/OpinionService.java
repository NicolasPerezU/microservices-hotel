package com.nicolas.microservice_opinions.service;

import com.nicolas.microservice_opinions.dto.OpinionRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface OpinionService {
    ResponseEntity<?> createOpinion(OpinionRequest opinionRequest);

    ResponseEntity<?> getOpinions();

    ResponseEntity<?> updateOpinion(Long id, OpinionRequest opinionRequest);

    ResponseEntity<?> deleteOpinion(Long id);
}
