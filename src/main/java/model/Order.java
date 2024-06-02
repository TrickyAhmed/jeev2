package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import test.DBUtil;

public class Order {
    private int customerId;
    private String pizzaId;
    private int quantity;
    private double totalPrice;
    private String orderStatus;
    private String Size;
    public Order(int customerId, String pizzaId, int quantity2, String Size, double totalPrice, String orderStatus) {
        this.customerId = customerId;
        this.pizzaId = pizzaId;
        this.quantity = quantity2;
        this.totalPrice = totalPrice;
        this.orderStatus = orderStatus;
        this.Size=Size;
    }
    
    
   
    public void setSize(String size) {
        this.Size = size;
    }

    // Getters
    public int getCustomerId() {
        return customerId;
    }
    
    public String getSize() {
        return Size;
    }
    

    public String getPizzaId() {
        return pizzaId;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    // Setters
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public void setPizzaId(String pizzaName) {
        this.pizzaId = pizzaName;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }
}
