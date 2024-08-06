package com.example.dukandar20;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.example.dukandar20.adapter.ReceiptAdapter;
import com.example.dukandar20.models.ReceiptItem;

import java.util.ArrayList;
import java.util.List;

public class ActivityBillPreview extends AppCompatActivity {

    private ListView listViewItems;
    private TextView textViewTotalAmount;
    List<ReceiptItem> dataset;
    long billId;

    DataBaseHelper myDB ;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_priview);

        listViewItems = findViewById(R.id.listViewItems);
        textViewTotalAmount = findViewById(R.id.textViewTotalAmount);
        myDB = new DataBaseHelper(this);
        dataset = new ArrayList<>();

         billId = getIntent().getLongExtra("BILL_ID", -1);
        if (billId != -1) {
            getItemsFromDatabase();
        } else {
//            previewTextView.setText("Invalid bill ID");
        }


        ReceiptAdapter adapter = new ReceiptAdapter(this,dataset);
        listViewItems.setAdapter(adapter);


        double total = calculateTotal(dataset);
        textViewTotalAmount.setText(String.format("$%.2f", total));

    }
    private double calculateTotal(List<ReceiptItem> items) {
        double total = 0.0;
        for (ReceiptItem item : items) {
            total += item.getPrice(); // or use item.getRate() * item.getQuantity() if price is not stored directly
        }
        return total;
    }

    private void getItemsFromDatabase() {
        Cursor cursor = myDB.getBillItemsByBillId(billId);

        if (cursor!= null && cursor.moveToFirst()){
            do{
               String itemName = cursor.getString(2);
               int quantity = cursor.getInt(3);
//               double rate = cursor.getDouble();
               double price = cursor.getDouble(4);

               dataset.add(new ReceiptItem(itemName,quantity,price));

            }while (cursor.moveToNext());
        }
        cursor.close();
        myDB.close();
    }

}