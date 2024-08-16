package com.example.dukandar20.Fragments;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.dukandar20.DataBaseHelper;
import com.example.dukandar20.R;
import com.github.dhaval2404.imagepicker.ImagePicker;

import java.io.ByteArrayOutputStream;


public class Fragment_Add_Item extends Fragment {


    EditText editTextItemName,editTextItemPrice;

    ImageView item_image;
    Button buttonAddItem,buttonUpdateItem;
    String cat_id;
    int IMG_REQ_CODE =100;
    boolean isUpdate = false;
    int itemId = -1;
    String itemName = null;
    double itemPrice = 0.00;
    Bitmap itemImageResource;







    public Fragment_Add_Item() {
        // Required empty public constructor
    }

    public static Fragment_Add_Item newInstance(boolean isUpdate, int itemId,String cat_id) {
        Fragment_Add_Item fragment = new Fragment_Add_Item();
        Bundle args = new Bundle();
        args.putBoolean("isUpdate", isUpdate);
        args.putInt("itemId", itemId);
        args.putString("cat_id",cat_id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

            isUpdate = getArguments().getBoolean("isUpdate");
            itemId = getArguments().getInt("itemId");
            cat_id = getArguments().getString("cat_id");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment__add__item, container, false);

        editTextItemName = view.findViewById(R.id.editTextItem);
        editTextItemPrice = view.findViewById(R.id.editTextItemPrice);
        item_image = view.findViewById(R.id.itemImage);
        buttonAddItem = view.findViewById(R.id.buttonAddItem);
        buttonUpdateItem = view.findViewById(R.id.buttonUpdateItem);


        if(isUpdate){
            buttonUpdateItem.setVisibility(View.VISIBLE);
            buttonAddItem.setVisibility(View.GONE);

            DataBaseHelper myDB = new DataBaseHelper(getContext());
            Cursor cursor = myDB.getItemByitemId(itemId);

            if (cursor != null && cursor.moveToFirst()) {

                itemName = cursor.getString(1);
                itemPrice = cursor.getInt(2);
                byte[] ByteArray = cursor.getBlob(4);
                itemImageResource = BitmapFactory.decodeByteArray(ByteArray,0,ByteArray.length);
            }

            editTextItemName.setText(itemName);
            editTextItemPrice.setText(String.valueOf(itemPrice));
            item_image.setImageBitmap(itemImageResource);

        }else {
            buttonAddItem.setVisibility(View.VISIBLE);
            buttonUpdateItem.setVisibility(View.GONE);
        }







        buttonAddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DataBaseHelper myDB = new DataBaseHelper(getContext());
                byte[] ibyteImage = convertToByteArray(item_image);

                myDB.addItem(
                        editTextItemName.getText().toString().trim(),// item name
                        Integer.parseInt(editTextItemPrice.getText().toString().trim()),// item price
                        Integer.parseInt(cat_id),
                        ibyteImage

                );
                getParentFragmentManager().popBackStack();
            }
        });

        buttonUpdateItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DataBaseHelper myDB = new DataBaseHelper(getContext());
                byte[] ibyteImage = convertToByteArray(item_image);
                myDB.updateItem(
                        itemId,
                        editTextItemName.getText().toString().trim(),
                        Double.parseDouble(editTextItemPrice.getText().toString().trim()),
                        ibyteImage
                );
                if (getActivity() != null) {
                    getActivity().finish();
                }

            }
        });


        item_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImagePicker.with(Fragment_Add_Item.this)
                        .crop(95f,75f)
                        .compress(1024)
                        .maxResultSize(1080,1080)
                        .start(IMG_REQ_CODE);
            }
        });




        return view;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if(resultCode == RESULT_OK){
            if (requestCode == IMG_REQ_CODE){

                Uri uri = data.getData();
                item_image.setImageURI(uri);
            }

        }
    }

    private byte[] convertToByteArray(ImageView imageView) {
        Bitmap bitmap;

        if (imageView.getDrawable() instanceof BitmapDrawable) {
            bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        } else {
            // Load a default bitmap from your resources as a fallback
            bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.gallery); // Replace with your default image
        }

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 70, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

}