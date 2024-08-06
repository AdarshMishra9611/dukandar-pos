package com.example.dukandar20.models;

import java.io.Serializable;

public class cart_model implements Serializable {
    public String productName;
    public int productQuantity;
    public double productPrice;

    public cart_model(String productName, double productPrice, int productQuantity ) {
        this.productName = productName;
        this.productPrice = productPrice;
        this.productQuantity = productQuantity;

    }
}

