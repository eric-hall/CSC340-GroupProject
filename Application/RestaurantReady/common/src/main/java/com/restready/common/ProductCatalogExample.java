package com.restready.common;

// TODO: Remove later...
public class ProductCatalogExample {

    public static final ProductCatalog SPACE_THEME;
    static {

        SPACE_THEME = new ProductCatalog();
        String[] productNames = {
                "Cosmic Pizza", "Nebula Noodles", "Star Squid", "Sky Skewers",
                "Meteor Mac", "Apollo Appetizer", "Astro Burger", "Solar Sushi",
                "Space Sliders", "Veggie Voyager", "Black Hole Bites", "Solar Stir-Fry",
                "Alien Toast", "Satellite Salad", "Martian Melt", "Quasar Queso",
                "Galactic Grill", "Rocket Bowl", "Interstellar Pasta", "Comet Croissant"
        };
        String[] productCategories = {
                "Vorp", "Bogos binted", "Zeeky zoogle", "Beeble meep", "Zeekybooble", "\uD83D\uDC7D"
        };

        for (String productName : productNames) {
            Product product = new Product();
            product.setName(productName);
            product.setCategory(productCategories[(int) (Math.random() * productCategories.length)]);
            product.setPrice(Math.round(Math.random() * 100.0d));
            SPACE_THEME.addProduct(product);
        }
    }
}
