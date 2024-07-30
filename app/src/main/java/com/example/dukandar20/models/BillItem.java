package com.example.dukandar20.models;

public class BillItem {
    private  String itemName;
    private int quantity;
    private double price;


    // Constructor
    public BillItem(String itemName, int quantity, double price) {
        this.itemName = itemName;
        this.quantity = quantity;
        this.price = price;
    }

    // Getters
    public String getItemName() {
        return itemName;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getPrice() {
        return price;
    }
}

