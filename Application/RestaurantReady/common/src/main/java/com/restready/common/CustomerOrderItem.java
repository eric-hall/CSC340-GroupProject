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
    private String mainLabel;
    private String[] splitLabels;

    public CustomerOrderItem(CustomerOrder order, Product product) {
        this.id = UUID.randomUUID();
        this.order = order;
        this.product = product;
        mainLabel = "";
        splitLabels = new String[0];
    }

    public int getSplitCount() {
        return splitLabels.length;
    }

//    @Override
    public void setSplitLabels(String... splitLabels) {
        this.splitLabels = splitLabels;
    }
}
