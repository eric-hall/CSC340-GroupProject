package com.restready.api;

import com.restaurantready.common.test.CommonModuleAccessTest;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication @RestController
public class RestaurantReadyAPI {

    public static void main(String[] args) {
        SpringApplication.run(RestaurantReadyAPI.class, args);
    }

    @GetMapping("/hello")
    public String helloWorldPage() {
        CommonModuleAccessTest.echo("RestaurantReadyAPI: helloWorldPage() called!");
        return "From RestaurantReadyAPI: Hello world!";
    }
}
