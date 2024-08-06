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

import android.util.Log;
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



public class add_category extends Fragment {



    private final int IMG_REQ_CODE = 100;

    EditText editTextCategory;
    Button addButton,updateButton;
    ImageView imagePicker;
    boolean isUpdate = false;
    int categoryId = -1;
    String categoryName;
    Bitmap categoryImageResource;
    byte[] ByteArray;






    public add_category() {
        // Required empty public constructor
    }

    public static add_category newInstance(boolean isUpdate,int categoryId) {
        add_category fragment = new add_category();
        Bundle args = new Bundle();
        args.putBoolean("isUpdate",isUpdate);
        args.putInt("categoryId",categoryId);



        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            isUpdate = getArguments().getBoolean("isUpdate");
            categoryId = getArguments().getInt("categoryId");
//            categoryName = getArguments().getString("categoryName");
//            ByteArray = getArguments().getByteArray("categoryImageResource");
//            categoryImageResource =  BitmapFactory.decodeByteArray(ByteArray,0,ByteArray.length);



        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_category, container, false);

        editTextCategory = view.findViewById(R.id.editTextCategory);
        addButton = view.findViewById(R.id.buttonAddCategory);
        imagePicker = view.findViewById(R.id.categoryImage);
        updateButton = view.findViewById(R.id.buttonUpdateCategory);

       // set button visibility bsased on operation
        if (isUpdate){
            updateButton.setVisibility(View.VISIBLE);
            addButton.setVisibility(View.GONE);
            DataBaseHelper myDB = new DataBaseHelper(getContext());
            Cursor cursor =  myDB.gateCategoryById(categoryId);
//            Log.v("categoryId", String.valueOf(categoryId));
            if(cursor != null && cursor.moveToFirst()){

                categoryName = cursor.getString(1);
//                Log.v("categoryId", categoryName);
                ByteArray = cursor.getBlob(2);
                categoryImageResource =  BitmapFactory.decodeByteArray(ByteArray,0,ByteArray.length);
            }
            imagePicker.setImageBitmap(categoryImageResource);
            editTextCategory.setText(categoryName);
        }else {
            addButton.setVisibility(View.VISIBLE);
            updateButton.setVisibility(View.GONE);
        }







        //addButton when pressed

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DataBaseHelper mydb = new DataBaseHelper( getContext() );
                byte[] imageArray = convertToByteArray(imagePicker);
                mydb.addCategory( editTextCategory.getText().toString().trim(),  imageArray);
                if (getActivity() != null) {
                    getActivity().finish();
                }

            }
        });

        // UpdateButton
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DataBaseHelper myDB = new DataBaseHelper(getContext());
                byte[] imageArray = convertToByteArray(imagePicker);
                myDB.updateCategory(categoryId,editTextCategory.getText().toString().trim(),imageArray);
                if (getActivity() != null) {
                    getActivity().finish();
                }
            }
        });


        //when imagepicker clicked

        imagePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImagePicker.with(add_category.this)
                        .crop()	    			//Crop image(Optional), Check Customization for more option
                        .compress(1024)			//Final image size will be less than 1 MB(Optional)
                        .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                        .start(IMG_REQ_CODE);
            }
        });



        return  view;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode ==RESULT_OK){
            if (requestCode == IMG_REQ_CODE ){
                Uri uri = data.getData();
                imagePicker.setImageURI(uri);
            }
        }


    }


    //convert imageview into bit map
    private byte[] convertToByteArray(ImageView imageView){
        Bitmap bitmap =((BitmapDrawable) imageView.getDrawable()).getBitmap();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,70,byteArrayOutputStream);
        return  byteArrayOutputStream.toByteArray();
    }

}