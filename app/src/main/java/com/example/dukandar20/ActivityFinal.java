package com.example.dukandar20;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.Animatable;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class ActivityFinal extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final);
        ImageView checkImage = findViewById(R.id.checkImage);


        Animation sale = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.scale_check);
        checkImage.setAnimation(sale);

    }
}