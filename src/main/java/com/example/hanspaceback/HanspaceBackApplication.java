package com.example.hanspaceback;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class HanspaceBackApplication {

    public static void main(String[] args) {
        SpringApplication.run(HanspaceBackApplication.class, args);
    }

}
