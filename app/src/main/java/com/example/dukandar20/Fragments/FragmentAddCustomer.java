package com.example.dukandar20.Fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.dukandar20.DataBaseHelper;
import com.example.dukandar20.R;

import org.w3c.dom.Text;

public class FragmentAddCustomer extends Fragment {


    private Toolbar toolbar;
    private EditText phoneNumber,customerName;
    private Button addfromContact;

    public FragmentAddCustomer() {
        // Required empty public constructor
    }


    public static FragmentAddCustomer newInstance(String param1, String param2) {
        FragmentAddCustomer fragment = new FragmentAddCustomer();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        if (getArguments() != null) {

        }
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_customer, container, false);
        customerName = view.findViewById(R.id.editTextNewCustomerName);
        phoneNumber = view.findViewById(R.id.editTextNewPhoneNumber);
        addfromContact = view.findViewById(R.id.buttonAddFromContacts);

        addfromContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DataBaseHelper myDB = new DataBaseHelper(getContext());
                myDB.insertCustomer(
                        customerName.getText().toString().trim(),
                        phoneNumber.getText().toString().trim()
                );
            }
        });








        return view;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            // Handle the back button click here
            if (getActivity() != null) {
                getActivity().onBackPressed();
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


}