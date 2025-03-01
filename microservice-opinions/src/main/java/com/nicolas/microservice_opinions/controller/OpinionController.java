package com.nicolas.microservice_opinions.controller;

import com.nicolas.microservice_opinions.dto.OpinionRequest;
import com.nicolas.microservice_opinions.service.OpinionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/opinions")
public class OpinionController {

    @Autowired
    private OpinionService opinionService;

    @PostMapping
    public ResponseEntity<?> createOpinion(@RequestBody OpinionRequest opinionRequest) {
        return opinionService.createOpinion(opinionRequest);
    }

    @GetMapping
    public ResponseEntity<?> getOpinions() {
        return opinionService.getOpinions();
    }



    @PutMapping("/{id}")
    public ResponseEntity<?> updateOpinion(@PathVariable Long id, @RequestBody OpinionRequest opinionRequest) {
        return opinionService.updateOpinion(id, opinionRequest);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOpinion(@PathVariable Long id) {
        return opinionService.deleteOpinion(id);
    }




}
