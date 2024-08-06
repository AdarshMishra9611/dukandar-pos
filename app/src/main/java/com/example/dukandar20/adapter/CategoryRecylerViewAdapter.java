package com.example.dukandar20.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
//import android.graphics.drawable.Drawable;
//import android.content.Intent;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dukandar20.DataBaseHelper;
import com.example.dukandar20.Item_Activity;
import com.example.dukandar20.R;
import com.example.dukandar20.add_Activity;

import java.util.ArrayList;


public class CategoryRecylerViewAdapter extends RecyclerView.Adapter<CategoryRecylerViewAdapter.RecyclerViewHolder>{
    private ArrayList cat_name,cat_id;
    private ArrayList<Bitmap>cat_image;
    private Activity activity;
    private Context mcontext;


    public CategoryRecylerViewAdapter(ArrayList cat_id , ArrayList cat_name, ArrayList<Bitmap> cat_image, Activity activity, Context mcontext) {

        this.cat_id = cat_id;
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
        int getPosition = position;
        String categoryName = (String) cat_name.get(position);
        Bitmap categoryImageResource =  cat_image.get(position);
       // Drawable index_cat_image = mcontext.getResources().getDrawable(cat_image_resourceId) ;
       String categoryID = String.valueOf(cat_id.get(position));// cat id
        holder.cardText.setText(String.valueOf(cat_name.get(position)));
        holder.card_image.setImageBitmap(categoryImageResource);
        holder.card_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(mcontext, Item_Activity.class);
                intent.putExtra("cat_id",categoryID);
                activity.startActivity(intent);

            }
        });
        holder.card_layout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                new AlertDialog.Builder(mcontext)
                        .setTitle("Category Action")
                        .setIcon(R.drawable.info_vector_asset)
                        .setMessage("Are you sure you want to Change this category?")
                        .setPositiveButton("Update", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent intent = new Intent(activity, add_Activity.class);
                                intent.putExtra("FRAGMENT_KEY", "add_category");
                                intent.putExtra("isUpdate",true);
                                intent.putExtra("categoryId",categoryID);

                                activity.startActivity(intent);

                            }
                        })
                        .setNegativeButton("Delete", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                 deleteCategory(Integer.parseInt(categoryID),getPosition);
                            }
                        })
                        .setNeutralButton("Cancel",null).show();

                return false;
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

    private void deleteCategory(int categoryId,int getPosition){
        new AlertDialog.Builder(mcontext)
                .setTitle("Delete")
                .setIcon(R.drawable.ic_delete)
                .setMessage("Are you sure you want to Delete this category?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        DataBaseHelper myDB = new DataBaseHelper(mcontext);
                        myDB.deleteCategory(categoryId);
                        cat_id.remove(getPosition);
                        cat_name.remove(getPosition);
                        cat_image.remove(getPosition);
                        notifyItemRemoved(getPosition);
                        notifyItemRangeChanged(getPosition,cat_name.size());
                    }
                })
                .setNegativeButton("No",null)
                .show();
    }
}
