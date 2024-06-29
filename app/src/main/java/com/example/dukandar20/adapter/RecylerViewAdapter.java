package com.example.dukandar20.adapter;

import android.app.Activity;
import android.content.Context;
//import android.graphics.drawable.Drawable;
//import android.content.Intent;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dukandar20.Item_Activity;
import com.example.dukandar20.MainActivity;
import com.example.dukandar20.R;

import java.util.ArrayList;


public class RecylerViewAdapter extends RecyclerView.Adapter<RecylerViewAdapter.RecyclerViewHolder>{
    private ArrayList cat_name;
    private ArrayList<Integer>cat_image;
    private Activity activity;
    private Context mcontext;


    public RecylerViewAdapter(ArrayList cat_name, ArrayList<Integer> cat_image, Activity activity, Context mcontext) {


        this.cat_name = cat_name;
        this.cat_image = cat_image;
        this.activity = activity;
        this.mcontext = mcontext;
    }






    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.catgeory_row, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        String categoryName = (String) cat_name.get(position);
        int categoryImageResource = cat_image.get(position);
       // Drawable index_cat_image = mcontext.getResources().getDrawable(cat_image_resourceId) ;

        holder.cardText.setText(String.valueOf(cat_name.get(position)));
        holder.card_image.setImageResource(categoryImageResource);
        holder.card_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(mcontext, Item_Activity.class);
                activity.startActivity(intent);

            }
        });





    }



    @Override
    public int getItemCount() {
        return cat_name.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {

        TextView cardText;
        ImageView card_image;
        LinearLayoutCompat card_layout;


        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            cardText = itemView.findViewById(R.id.categoryText);
            card_image = itemView.findViewById(R.id.categoryImage);
            card_layout =itemView.findViewById(R.id.categoryCard);



        }
    }
}
