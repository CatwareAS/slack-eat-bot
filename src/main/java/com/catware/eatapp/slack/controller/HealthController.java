package com.catware.eatapp.slack.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
public class HealthController {

    @GetMapping("/")
    public ResponseEntity<String> handle() {
        return ResponseEntity.ok("UP :" + LocalDateTime.now());
    }
}
