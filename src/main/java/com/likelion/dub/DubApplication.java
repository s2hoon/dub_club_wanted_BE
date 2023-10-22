package com.likelion.dub;

import java.util.Optional;
import java.util.UUID;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;


@EnableJpaAuditing //(modifyOnCreate =false)를 넣으면 저장시점에 저장데이터만 입력
@SpringBootApplication
public class DubApplication {

    public static void main(String[] args) {
        SpringApplication.run(DubApplication.class, args);
    }

    @Bean
    public AuditorAware<String> auditorProvider() {
        return () -> Optional.of(UUID.randomUUID().toString());
    }


}
