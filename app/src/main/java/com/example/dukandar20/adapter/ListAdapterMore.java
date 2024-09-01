package com.example.dukandar20.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.dukandar20.R;

import java.util.ArrayList;

public class ListAdapterMore extends ArrayAdapter<String> {
    private Context context;
    private int resource;
    private ArrayList<String> items;

    public ListAdapterMore(Context context, int resource, ArrayList<String> items) {
        super(context, resource, items);
        this.context = context;
        this.resource = resource;
        this.items = items;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(resource, parent, false);
        }

        // Get the current string to be displayed
        String currentItem = items.get(position);

        // Find the TextView in the custom layout
        TextView titleTextView = convertView.findViewById(R.id.textViewMore);

        // Set the text for the TextView
        titleTextView.setText(currentItem);

        return convertView;
    }
}
