package com.restready.common;

import com.restready.common.util.Log;
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
        if (products.put(product.getId(), product) != null) {
            Log.warn(this, "Product submitted multiple times: " + product.getName());
        }
    }

    public Product getProduct(UUID productId) {
        return products.get(productId);
    }

    public boolean removeProduct(UUID productId) {
        return products.remove(productId) != null;
    }

    @Override
    public Iterator<Product> iterator() {
        return products.values().iterator();
    }
}
