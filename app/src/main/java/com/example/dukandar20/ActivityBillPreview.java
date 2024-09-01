package com.example.dukandar20;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageButton;

import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dukandar20.Printer.PDFGenerator;
import com.example.dukandar20.models.ReceiptItem;

import java.util.ArrayList;
import java.util.List;

public class ActivityBillPreview extends AppCompatActivity {

    private LinearLayout containerItems;
    private TextView textViewTotalAmount;
    List<ReceiptItem> dataset;
    String billId;
    AppCompatImageButton buttonBack,buttonDownload,buttonShare;
    Button buttonPrint;

    DataBaseHelper myDB;
    private PDFGenerator pdfGenerator;
    private static final int REQUEST_PERMISSION_WRITE_STORAGE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_priview);

        containerItems = findViewById(R.id.containerItems);
        textViewTotalAmount = findViewById(R.id.textViewTotalAmount);
        buttonBack = findViewById(R.id.buttonBack);
        buttonDownload = findViewById(R.id.buttonDownload);
        buttonPrint = findViewById(R.id.buttonPrint);
        buttonShare = findViewById(R.id.buttonShare);
        pdfGenerator = new PDFGenerator(this);







        myDB = new DataBaseHelper(this);
        dataset = new ArrayList<>();

        billId = getIntent().getStringExtra("BILL_ID");
        Log.v("PreviewBillId", String.valueOf(billId));
        getItemsFromDatabase();

        double total = calculateTotal(dataset);
        textViewTotalAmount.setText(String.format("₹%.2f", total));

        buttonBack.setOnClickListener(v -> onBackPressed());
        buttonDownload.setOnClickListener(v -> downloadReceipt());
        buttonShare.setOnClickListener(v -> shareReceipt());
        buttonPrint.setOnClickListener(v -> printReceipt());
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
    private void requestStoragePermission() {
        if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_PERMISSION_WRITE_STORAGE);
        } else {
            downloadReceipt();
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSION_WRITE_STORAGE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                downloadReceipt();
            } else {
                Toast.makeText(this, "Permission denied to write to storage", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void downloadReceipt() {
        // Implement your download functionality here
        double total = calculateTotal(dataset);
        pdfGenerator.generatePDF(dataset, total);
        Toast.makeText(this, "Download clicked", Toast.LENGTH_SHORT).show();
    }
    private void shareReceipt() {
        // Implement your share functionality here
        Toast.makeText(this, "Share clicked", Toast.LENGTH_SHORT).show();
    }
    private void printReceipt() {
        // Implement your print functionality here
        Toast.makeText(this, "Print clicked", Toast.LENGTH_SHORT).show();
    }
}
