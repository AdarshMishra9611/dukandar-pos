package com.example.dukandar20;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;

public class DefaultDataset {

    Context context;

    public DefaultDataset(Context context) {
        this.context = context;
    }
    DataBaseHelper mydb = new DataBaseHelper(context);

    String categoryNames[] = {"Perle-G","Britannia","Nestle","ITC","Beverages","Dariy Product"};



    private byte[] convertToByteArray() {
        Bitmap bitmap;

//        if (imageView.getDrawable() instanceof BitmapDrawable) {
//            bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
//        } else {
//            // Load a default bitmap from your resources as a fallback
//            // Replace with your default image
//        }

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        return byteArrayOutputStream.toByteArray();
    }
}

