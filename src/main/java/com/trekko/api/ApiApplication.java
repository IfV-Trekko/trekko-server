package com.trekko.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * The main class of the application.
 */
@SpringBootApplication
public class ApiApplication {
    /**
     * The application entry point. Starts the Spring Boot application.
     * 
     * @param args The command line arguments. None are expected.
     */
    public static void main(final String[] args) {
        if (args.length != 0)
            throw new IllegalArgumentException("No command line arguments are expected.");

        SpringApplication.run(ApiApplication.class, args);
    }
}
