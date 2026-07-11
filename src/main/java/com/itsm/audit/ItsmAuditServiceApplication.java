package com.itsm.audit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication
@EnableKafka
public class ItsmAuditServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(ItsmAuditServiceApplication.class, args);
    }
}
