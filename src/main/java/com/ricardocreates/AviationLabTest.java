package com.ricardocreates;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class AviationLabTest {

    public static void main(String[] args) {
        SpringApplication.run(AviationLabTest.class, args);
    }
}
