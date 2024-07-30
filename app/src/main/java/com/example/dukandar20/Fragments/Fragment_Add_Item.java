package com.example.dukandar20.Fragments;

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

import com.example.dukandar20.DataBaseHelper;
import com.example.dukandar20.R;
import com.github.dhaval2404.imagepicker.ImagePicker;

import java.io.ByteArrayOutputStream;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment_Add_Item#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment_Add_Item extends Fragment {


    EditText editTextItemName,editTextItemPrice;

    ImageView item_image;
    Button buttonAddItem;
    String cat_id;
    int IMG_REQ_CODE =100;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters


    public Fragment_Add_Item() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment_Add_Item.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment_Add_Item newInstance(String param1, String param2) {
        Fragment_Add_Item fragment = new Fragment_Add_Item();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
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

        // getting bundel
         cat_id = getArguments().getString("cat_id");
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment__add__item, container, false);

        editTextItemName = view.findViewById(R.id.editTextItem);
        editTextItemPrice = view.findViewById(R.id.editTextItemPrice);
        item_image = view.findViewById(R.id.itemImage);
        buttonAddItem = view.findViewById(R.id.buttonAddItem);






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
            }
        });


        item_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImagePicker.with(Fragment_Add_Item.this)
                        .crop()
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

    private  byte[] convertToByteArray(ImageView imageView){
        Bitmap bitmap = ((BitmapDrawable)imageView.getDrawable()).getBitmap();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,70,byteArrayOutputStream);

        return byteArrayOutputStream.toByteArray();
    }
}