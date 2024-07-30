package com.example.dukandar20.models;

import android.graphics.Bitmap;

public class Item_model {
    public double item_price;
    public int productQuantity ;
    public Bitmap item_image;
    public String item_name;
    public Item_model(Bitmap item_image,String item_name,double  item_price ,int productQuantity){
        this.item_image =item_image;
        this.item_name = item_name;
        this.item_price = item_price;
        this.productQuantity = productQuantity;

    }
}
