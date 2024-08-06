package com.example.dukandar20.models;

import android.graphics.Bitmap;

public class Item_model {
    public int item_id;
    public int cat_id;
    public double item_price;
    public int productQuantity ;
    public Bitmap item_image;
    public String item_name;
    public Item_model(int item_id, int cat_id, Bitmap item_image,String item_name,double  item_price ,int productQuantity){
        this.item_id = item_id;
        this.cat_id= cat_id;
        this.item_image =item_image;
        this.item_name = item_name;
        this.item_price = item_price;
        this.productQuantity = productQuantity;

    }
}
