package com.restready.common;

import com.restready.common.util.Log;
import lombok.Getter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.UUID;

@Getter
public class CustomerOrder implements Serializable, Iterable<CustomerOrderItem> {

    private final UUID id;
    private final CustomerTicket ticket;
    private final LocalDateTime createdTimestamp;
    private final ArrayList<CustomerOrderItem> items;
    private LocalDateTime submittedTimestamp;
    private boolean submitted;

    public CustomerOrder(CustomerTicket ticket) {
        id = UUID.randomUUID();
        this.ticket = ticket;
        createdTimestamp = LocalDateTime.now();
        items = new ArrayList<>();
        submittedTimestamp = null;
        submitted = false;
    }

    public void markAsSubmitted() {
        if (submitted) {
            Log.warn(this, "Multiple submissions for: " + id.toString());
        }
        submittedTimestamp = LocalDateTime.now();
        submitted = true;
    }

    public CustomerOrderItem addProductToOrder(Product product) {
        CustomerOrderItem item = new CustomerOrderItem(this, product);
        items.add(item);
        return item;
    }

    @Override
    public Iterator<CustomerOrderItem> iterator() {
        return items.iterator();
    }
}
