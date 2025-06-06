package com.agosto.chapter01;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@SpringBootApplication
public class TacoCloudApplication {

    @GetMapping("/")
    public static void main(String[] args) {

        SpringApplication.run(TacoCloudApplication.class, args);

    }
}
