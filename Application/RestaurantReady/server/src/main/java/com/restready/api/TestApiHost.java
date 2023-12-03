package com.restready.api;

import com.restready.common.util.Log;
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
    @GetMapping
    public String getEndpoint() {
        Log.info(this, "Get endpoint touched!");
        return "GET request received!";
    }

    // Sample POST request
    @PostMapping
    public String postEndpoint(@RequestBody String data) {
        Log.info(this, "Post endpoint touched! " + data);
        return "POST request received with data: " + data;
    }

    // Sample PUT request
    @PutMapping
    public String putEndpoint(@RequestBody String data) {
        Log.info(this, "Put endpoint touched! " + data);
        return "PUT request received with data: " + data;
    }

    // Sample DELETE request
    @DeleteMapping
    public String deleteEndpoint() {
        Log.info(this, "Delete endpoint touched!");
        return "DELETE request received!";
    }
}
