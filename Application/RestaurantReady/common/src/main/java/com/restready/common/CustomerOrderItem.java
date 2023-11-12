package com.restready.common;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CustomerOrderItem {

    private int id;
    private int productId;
    private int cashierId;
    private String label;

    public CustomerOrderItem(int id, int productId, int cashierId, String label) {
        this.id = id;
        this.productId = productId;
        this.cashierId = cashierId;
        this.label = label;
    }
}
