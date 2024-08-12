package com.example.dukandar20.models;

public class customer_model {
    public customer_model(int customerid,String customerName, String customerPhone ) {
        this.customerName = customerName;
        this.customerPhone = customerPhone;
        this.customerid = customerid;
    }

    public String customerName;
   public String customerPhone;
   public int customerid;
}
