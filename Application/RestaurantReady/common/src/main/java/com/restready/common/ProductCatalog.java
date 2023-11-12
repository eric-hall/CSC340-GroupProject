package com.restready.common;

import lombok.Getter;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;

@Getter
public class ProductCatalog implements Serializable, Iterable<Product> {

    private final HashMap<Integer, Product> products;

    public ProductCatalog() {
        products = new HashMap<>();
    }

    public void addProduct(Product product) {
        products.put(product.getId(), product);
    }

    public Product getProduct(int productId) {
        return products.get(productId);
    }

    public boolean removeProduct(int id) {
        return products.remove(id) != null;
    }

    @Override
    public Iterator<Product> iterator() {
        return products.values().iterator();
    }
}
