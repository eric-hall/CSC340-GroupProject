package com.restready.common;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.UUID;

@Getter @Setter @ToString
public class Product implements Serializable {

    private final UUID id;
    private String name;
    private String category;
    private double price;

    public Product() {
        id = UUID.randomUUID();
        name = "";
        category = "";
        price = 0.0d;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Product other && id.equals(other.id);
    }
}
