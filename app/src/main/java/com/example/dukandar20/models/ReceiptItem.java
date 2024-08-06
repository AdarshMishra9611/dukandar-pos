package com.example.dukandar20.models;

public class ReceiptItem {

    private String name;
    private int quantity;
//    private double rate;
    private double price;

    // Constructor
    public ReceiptItem(String name, int quantity, double price) {
        this.name = name;
        this.quantity = quantity;

        this.price = price;
    }

    // Getters
    public String getName() { return name; }
    public int getQuantity() { return quantity; }
//    public double getRate() { return rate; }
    public double getPrice() { return price; }
}
