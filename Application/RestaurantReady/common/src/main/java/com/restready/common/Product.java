package com.restready.common;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.UUID;

public class Product implements Serializable {

    @Getter
    private final UUID id;
    @Getter @Setter
    private String name;
    @Getter @Setter
    private double price;

    public Product() {
        this("", 0.0d);
    }

    public Product(String name, double price) {
        id = UUID.randomUUID();
        this.name = name;
        this.price = price;
    }
}
