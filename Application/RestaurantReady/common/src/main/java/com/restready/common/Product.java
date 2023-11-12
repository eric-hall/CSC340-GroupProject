package com.restready.common;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.UUID;

public class Product implements Serializable {

    // TODO: Remove later...
    public static final String[] EXAMPLE_SPACE_THEME_PRODUCT_NAMES = {
            "Cosmic Pizza", "Nebula Noodles", "Star Squid", "Sky Skewers",
            "Meteor Mac", "Apollo Appetizer", "Astro Burger", "Solar Sushi",
            "Space Sliders", "Veggie Voyager", "Black Hole Bites", "Solar Stir-Fry",
            "Alien Toast", "Satellite Salad", "Martian Melt", "Quasar Queso",
            "Galactic Grill", "Rocket Bowl", "Interstellar Pasta", "Comet Croissant"
    };

    @Getter
    private final UUID id;
    @Getter @Setter
    private String name;
    @Getter @Setter
    private String category;
    @Getter @Setter
    private double price;

    public Product() {
        id = UUID.randomUUID();
        name = "";
        category = "";
        price = 0.0d;
    }
}
