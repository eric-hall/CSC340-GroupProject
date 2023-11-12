package com.restready.common;

import lombok.Getter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;

@Getter
public class CustomerTicket implements Serializable {

    private final UUID id;
    private final UUID owningCashierId;
    private final LocalDateTime createdTimestamp;
    private final ArrayList<CustomerOrder> orders;

    public CustomerTicket(CashierProfile owningCashier) {
        id = UUID.randomUUID();
        this.owningCashierId = owningCashier.getId();
        createdTimestamp = LocalDateTime.now();
        orders = new ArrayList<>();
    }

    public CustomerOrder openNewCustomerOrder() {
        CustomerOrder order = new CustomerOrder(this);
        orders.add(order);
        return order;
    }
}
