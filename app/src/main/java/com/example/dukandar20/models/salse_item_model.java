package com.example.dukandar20.models;

import java.util.Date;

public class salse_item_model {


    public String billNumber = null;
    public double totalbillAmount;
    public String billDate;



    public salse_item_model(String billNumber, double totalbillAmount, String billDate) {
        this.billNumber = billNumber;
        this.totalbillAmount = totalbillAmount;
        this.billDate = billDate;
    }


}
