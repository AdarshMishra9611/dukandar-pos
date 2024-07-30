package com.example.dukandar20.models;

public class cart_model {
    public String productName;
    public int productQuantity;
    public double productPrice;

    public cart_model(String productName, double productPrice, int productQuantity ) {
        this.productName = productName;
        this.productPrice = productPrice;
        this.productQuantity = productQuantity;

    }
}

