package model;

import java.util.ArrayList;
import java.util.List;

public class Cart {
    private List<Order> items = new ArrayList<>();

    public List<Order> getItems() {
        return items;
    }

    public void addItem(Order item) {
        items.add(item);
    }

    public void removeItem(int index) {
        if (index >= 0 && index < items.size()) {
            items.remove(index);
        } else {
            throw new IndexOutOfBoundsException("Invalid index for cart item removal.");
        }
    }
    
    public void clear() {
        this.items.clear();
    }
    

    public void updateItem(int index, int quantity) {
        if (index >= 0 && index < items.size()) {
            Order item = items.get(index);
            item.setQuantity(quantity);
        } else {
            throw new IndexOutOfBoundsException("Invalid index for cart item update.");
        }
    }

    public double getTotalPrice() {
        return items.stream().mapToDouble(Order::getTotalPrice).sum();
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }
}
