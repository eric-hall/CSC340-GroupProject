package com.restready.common;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.UUID;

@Getter @Setter
public class CustomerOrderItem implements Serializable {

    private final UUID id;
    private final CustomerOrder order;
    private final Product product;
    private String label;

    public CustomerOrderItem(CustomerOrder order, Product product) {
        this.id = UUID.randomUUID();
        this.order = order;
        this.product = product;
        label = "";
    }
}
