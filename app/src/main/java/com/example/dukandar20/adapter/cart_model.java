package com.example.dukandar20.adapter;

public class cart_model {
    String productName;
    public int productQuantity;
    public int productPrice;

    public cart_model(String productName, int productPrice, int productQuantity ) {
        this.productName = productName;
        this.productPrice = productPrice;
        this.productQuantity = productQuantity;

    }
}

