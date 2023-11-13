package com.restready.common;

import com.restready.common.util.Log;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.UUID;

@Getter @Setter
public class CustomerOrderItem implements Serializable {

    private final UUID id;
    private final CustomerOrder order;
    private final Product product;
    private final ArrayList<String> customerLabels;

    public CustomerOrderItem(CustomerOrder order, Product product) {
        this.id = UUID.randomUUID();
        this.order = order;
        this.product = product;
        customerLabels = new ArrayList<>();
        customerLabels.add(""); // Add a placeholder label
    }

    public int getCustomerLabelsCount() {
        return customerLabels.size();
    }

    public String getCustomerLabel(int index) {

        if (index < 0 || customerLabels.size() < index) {
            Log.error(this, "Split check label index out-of-bounds");
            return "ERROR"; // Return bad value regardless
        }

        return customerLabels.get(index);
    }

    public void addCustomerLabel(String label) {
        customerLabels.add(label);
    }

    public void setCustomerLabel(int index, String value) {

        if (index < 0 || customerLabels.size() < index) {
            Log.error(this, "Label index out-of-bounds");
            return;
        }

        customerLabels.set(index, value);
    }
}
