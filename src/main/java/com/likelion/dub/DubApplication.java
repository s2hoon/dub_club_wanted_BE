package com.likelion.dub;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;


@EnableJpaAuditing
@SpringBootApplication
public class DubApplication {

    public static void main(String[] args) {
        SpringApplication.run(DubApplication.class, args);
    }
    
}
