package org.ordermanagement.ordermanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = "org.ordermanagement")
public class PocOrderManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(PocOrderManagementApplication.class, args);
    }
}
