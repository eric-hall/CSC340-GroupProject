package com.restready.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

@SpringBootApplication
@RestController
@RequestMapping("/api")
public class TestApiHost {

    public static void main(String[] args) {
        SpringApplication.run(TestApiHost.class, args);
    }

    // Sample GET request
    @GetMapping("/get")
    public String getEndpoint() {
        System.out.println("Get endpoint touched!");
        return "GET request received!";
    }

    // Sample POST request
    @PostMapping("/post")
    public String postEndpoint(@RequestBody String data) {
        System.out.println("Post endpoint touched! " + data);
        return "POST request received with data: " + data;
    }

    // Sample PUT request
    @PutMapping("/put")
    public String putEndpoint(@RequestBody String data) {
        System.out.println("Put endpoint touched! " + data);
        return "PUT request received with data: " + data;
    }

    // Sample DELETE request
    @DeleteMapping("/delete")
    public String deleteEndpoint() {
        System.out.println("Delete endpoint touched!");
        return "DELETE request received!";
    }
}
