package com.catware.eatapp.slack.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class HealthController {

    @GetMapping("/")
    public ResponseEntity<String> handle() throws IOException {
        return ResponseEntity.ok("UP");
    }
}
