package com.evsoft;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class Server {
    public static void main(String[] args) {
        SpringApplication.run(Server.class, args);
    }

    @RestController
    static class HelloController {
        @GetMapping("/")
        public String hello() {
            return "Hello, TODO Manager!";
        }
    }
}