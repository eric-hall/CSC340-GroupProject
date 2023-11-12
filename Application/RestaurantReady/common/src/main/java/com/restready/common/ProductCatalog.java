package com.restready.common;

import lombok.Getter;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.UUID;

@Getter
public class ProductCatalog implements Serializable, Iterable<Product> {

    private final HashMap<UUID, Product> products;

    public ProductCatalog() {
        products = new HashMap<>();
    }

    public void addProduct(Product product) {
        products.put(product.getId(), product);
    }

    public Product getProduct(UUID productId) {
        return products.get(productId);
    }

    public boolean removeProduct(UUID id) {
        return products.remove(id) != null;
    }

    @Override
    public Iterator<Product> iterator() {
        return products.values().iterator();
    }
}
