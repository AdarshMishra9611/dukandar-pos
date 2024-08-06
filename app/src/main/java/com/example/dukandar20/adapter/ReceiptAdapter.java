package com.example.dukandar20.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.dukandar20.R;
import com.example.dukandar20.models.ReceiptItem;

import java.util.List;

public class ReceiptAdapter extends ArrayAdapter<ReceiptItem> {

    private final Context context;
    private final List<ReceiptItem> items;

    public ReceiptAdapter(Context context, List<ReceiptItem> items) {
        super(context, R.layout.item_receipt, items);
        this.context = context;
        this.items = items;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_receipt, parent, false);
        }

        ReceiptItem item = items.get(position);

        TextView itemName = convertView.findViewById(R.id.textViewItemName);
        TextView itemQty = convertView.findViewById(R.id.textViewItemQty);
        TextView itemRate = convertView.findViewById(R.id.textViewItemRate);
        TextView itemPrice = convertView.findViewById(R.id.textViewItemPrice);

        itemName.setText(item.getName());
        itemQty.setText(String.valueOf(item.getQuantity()));
//        itemRate.setText(String.format("$%.2f", item.getRate()));
        itemPrice.setText(String.format("$%.2f", item.getPrice()));

        return convertView;
    }
}
