package com.workshop.chapter0;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class Chapter0Application {

    public static void main(String[] args) {
        SpringApplication.run(Chapter0Application.class, args);
    }
}
