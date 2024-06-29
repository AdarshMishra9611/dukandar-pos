package com.example.dukandar20.adapter;

public class Item_model {
    int item_image,item_price, productQuantity =0;
    String item_name;
    public Item_model(int item_image,String item_name,int item_price){
        this.item_image =item_image;
        this.item_name = item_name;
        this.item_price = item_price;

    }
}
