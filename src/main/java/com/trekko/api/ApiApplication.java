package com.trekko.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ApiApplication {
    private static final String DATABASE = "";

    public static void main(final String[] args) {
        SpringApplication.run(ApiApplication.class, args);
    }

}
