package com.example.inventory;

public class Product {
    private int id;
    private String name;
    private int quantity;
    private String expireDate;

    public Product(int id, String name, int quantity, String expireDate) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.expireDate = expireDate;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getExpireDate() {
        return expireDate;
    }
}
