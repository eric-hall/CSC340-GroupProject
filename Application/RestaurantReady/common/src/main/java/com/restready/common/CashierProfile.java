package com.restready.common;

import lombok.Getter;

import java.io.Serializable;
import java.util.HashMap;
import java.util.UUID;

@Getter
public class CashierProfile implements Serializable {

    private final UUID id;
    private final HashMap<UUID, CustomerTicket> openTickets;

    public CashierProfile() {
        id = UUID.randomUUID();
        openTickets = new HashMap<>();
    }

    public CustomerTicket openNewCustomerTicket() {
        CustomerTicket ticket = new CustomerTicket(this);
        openTickets.put(ticket.getId(), ticket);
        return ticket;
    }
}
