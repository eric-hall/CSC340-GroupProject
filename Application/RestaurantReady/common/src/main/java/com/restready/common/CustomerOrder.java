package com.restready.common;

import com.restready.common.util.Log;
import lombok.Getter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Iterator;
import java.util.UUID;

@Getter
public class CustomerOrder implements Serializable, Iterable<CustomerOrderItem> {

    private final UUID id;
    private final CustomerTicket ticket;
    private final LocalDateTime createdTimestamp;
    private final HashMap<UUID, CustomerOrderItem> items;
    private LocalDateTime submittedTimestamp;
    private boolean submitted;

    public CustomerOrder(CustomerTicket ticket) {
        id = UUID.randomUUID();
        this.ticket = ticket;
        createdTimestamp = LocalDateTime.now();
        items = new HashMap<>();
        submittedTimestamp = null;
        submitted = false;
    }

    public void markAsSubmitted() {
        if (submitted) {
            Log.warn(this, "Multiple submissions");
        }
        submittedTimestamp = LocalDateTime.now();
        submitted = true;
    }

    public CustomerOrderItem addProductToOrder(Product product) {
        CustomerOrderItem item = new CustomerOrderItem(this, product);
        items.put(item.getId(), item);
        return item;
    }

    public boolean remove(CustomerOrderItem item) {
        if (submitted) {
            Log.warn(this, "Removing item from submitted order");
        }
        return items.remove(item.getId()) != null;
    }

    @Override
    public Iterator<CustomerOrderItem> iterator() {
        return items.values().iterator();
    }
}
