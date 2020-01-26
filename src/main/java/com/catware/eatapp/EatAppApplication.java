package com.catware.eatapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class EatAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(EatAppApplication.class, args);
    }

}
