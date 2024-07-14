package com.example.dukandar20.adapter;

import android.graphics.Bitmap;

public class Item_model {
    int item_price, productQuantity ;
    Bitmap item_image;
    String item_name;
    public Item_model(Bitmap item_image,String item_name,int item_price ,int productQuantity){
        this.item_image =item_image;
        this.item_name = item_name;
        this.item_price = item_price;
        this.productQuantity = productQuantity;

    }
}
