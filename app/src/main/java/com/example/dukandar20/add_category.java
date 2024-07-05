package com.example.dukandar20;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.graphics.Bitmap;
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

import com.example.dukandar20.adapter.DataBaseHelper;
import com.github.dhaval2404.imagepicker.ImagePicker;

import java.io.ByteArrayOutputStream;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link add_category#newInstance} factory method to
 * create an instance of this fragment.
 */
public class add_category extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    private final int IMG_REQ_CODE = 100;

    EditText editTextCategory;
    Button addButton;
    ImageView imagePicker;





    public add_category() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment add_category.
     */
    // TODO: Rename and change types and number of parameters
    public static add_category newInstance(String param1, String param2) {
        add_category fragment = new add_category();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

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

        //addButton when pressed

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DataBaseHelper mydb = new DataBaseHelper( getContext() );
                byte[] imageArray = convertToByteArray(imagePicker);
                mydb.addCategory( editTextCategory.getText().toString().trim(),imageArray);
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