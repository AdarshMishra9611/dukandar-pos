package com.example.dukandar20;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.dukandar20.models.ReceiptItem;

import java.util.ArrayList;
import java.util.List;

public class ActivityBillPreview extends AppCompatActivity {

    private LinearLayout containerItems;
    private TextView textViewTotalAmount;
    List<ReceiptItem> dataset;
    String billId;

    DataBaseHelper myDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_priview);

        containerItems = findViewById(R.id.containerItems);
        textViewTotalAmount = findViewById(R.id.textViewTotalAmount);
        myDB = new DataBaseHelper(this);
        dataset = new ArrayList<>();

        billId = getIntent().getStringExtra("BILL_ID");
        Log.v("PreviewBillId", String.valueOf(billId));
        getItemsFromDatabase();

        double total = calculateTotal(dataset);
        textViewTotalAmount.setText(String.format("₹%.2f", total));
    }

    private double calculateTotal(List<ReceiptItem> items) {
        double total = 0.0;
        for (ReceiptItem item : items) {
            total = total + item.getPrice() * item.getQuantity();
        }
        return total;
    }

    private void getItemsFromDatabase() {
        Cursor cursor = myDB.getBillItemsByBillId(billId);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                String itemName = cursor.getString(2);
                int quantity = cursor.getInt(3);
                double price = cursor.getDouble(4);

                dataset.add(new ReceiptItem(itemName, quantity, price));
                Log.v("apple", "cursor returning something");

                // Inflate item layout and add to container
                addItemToContainer(itemName, quantity, price);

            } while (cursor.moveToNext());
        } else {
            Log.v("apple", "cursor is not returning anything");
        }

        cursor.close();
        myDB.close();
    }

    private void addItemToContainer(String itemName, int quantity, double price) {
        LayoutInflater inflater = LayoutInflater.from(this);
        View itemView = inflater.inflate(R.layout.item_receipt, containerItems, false);

        TextView itemNameView = itemView.findViewById(R.id.textViewItemName);
        TextView itemQtyView = itemView.findViewById(R.id.textViewItemQty);
        TextView itemRateView = itemView.findViewById(R.id.textViewItemRate);
        TextView itemPriceView = itemView.findViewById(R.id.textViewItemPrice);

        itemNameView.setText(itemName);
        itemQtyView.setText(String.valueOf(quantity));
//        itemRateView.setText(String.format("₹%.2f", price / quantity)); // Assuming rate is price per unit
        itemRateView.setText("rate");
        itemPriceView.setText(String.format("₹%.2f", price));

        containerItems.addView(itemView);
    }
}
