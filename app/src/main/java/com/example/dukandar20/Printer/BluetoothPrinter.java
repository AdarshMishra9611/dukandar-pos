package com.example.dukandar20.Printer;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.dantsu.escposprinter.EscPosPrinter;
import com.dantsu.escposprinter.connection.bluetooth.BluetoothConnection;
import com.dantsu.escposprinter.connection.bluetooth.BluetoothPrintersConnections;
import com.example.dukandar20.models.cart_model;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class BluetoothPrinter {

    public static final int PERMISSION_BLUETOOTH = 1;
    public static final int PERMISSION_BLUETOOTH_ADMIN = 2;
    public static final int PERMISSION_BLUETOOTH_CONNECT = 3;
    public static final int PERMISSION_BLUETOOTH_SCAN = 4;
    public static final int PERMISSION_BLUETOOTH_ADVERTISE = 5; // For Android 12 and above

    private Context context;
    private Activity activity;
    private BluetoothConnection connection;

    public BluetoothPrinter(Context context, Activity activity) {
        this.context = context;
        this.activity = activity;
    }

    private void checkPermissions() {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.BLUETOOTH) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.BLUETOOTH}, PERMISSION_BLUETOOTH);
        } else if (ContextCompat.checkSelfPermission(context, Manifest.permission.BLUETOOTH_ADMIN) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.BLUETOOTH_ADMIN}, PERMISSION_BLUETOOTH_ADMIN);
        } else if (ContextCompat.checkSelfPermission(context, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.BLUETOOTH_CONNECT}, PERMISSION_BLUETOOTH_CONNECT);
        } else if (ContextCompat.checkSelfPermission(context, Manifest.permission.BLUETOOTH_SCAN) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.BLUETOOTH_SCAN}, PERMISSION_BLUETOOTH_SCAN);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S && ContextCompat.checkSelfPermission(context, Manifest.permission.BLUETOOTH_ADVERTISE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.BLUETOOTH_ADVERTISE}, PERMISSION_BLUETOOTH_ADVERTISE);
        }
    }

    private boolean connectToPrinter() {
        connection = BluetoothPrintersConnections.selectFirstPaired();
        if (connection != null) {
            try {
                if (!connection.isConnected()) {
                    connection.connect();
                }
                return true;
            } catch (Exception e) {
                Log.e("BluetoothPrinter", "Connection failed: " + e.getMessage(), e);
            }
        } else {
            Log.e("BluetoothPrinter", "No paired printer found.");
        }
        return false;
    }

    public void doPrint(ArrayList<cart_model> dataset) {
        checkPermissions();

        try {
            if (connectToPrinter()) {
                EscPosPrinter printer = new EscPosPrinter(connection, 203, 48f, 32);
                String receipt = generateReceipt(dataset,"1","Adarsh","8867510457");
                printer.printFormattedText(receipt);

                // Send ESC/POS command to cut the paper
                connection.write(new byte[]{0x1D, 'V', 1});

                connection.disconnect();
                connection = null;
                Toast.makeText(context, "Print successful", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "Printer connection failed.", Toast.LENGTH_SHORT).show();
                Log.e("BluetoothPrinter", "Printer connection failed.");
            }
        } catch (Exception e) {
            Toast.makeText(context, "Can't print: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            Log.e("BluetoothPrinter", "Can't print: " + e.getMessage(), e);
        }
    }


    @SuppressLint("DefaultLocale")
    public String generateReceipt(ArrayList<cart_model> dataset, String orderNumber, String customerName, String customerPhone) {
        StringBuilder receipt = new StringBuilder();

        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yy hh:mm");
        String currentDate = sdf.format(new Date());

        // Receipt Header with Order Number
        receipt.append(String.format("[L]Bill No:%s<b> [R]%s</b>\n",orderNumber,currentDate));
//        receipt.append("[L]\n");
        receipt.append("[L]--------------------------------\n");


        // Header for Item details
        receipt.append("<b>Item         Rate   Qty   Amount</b>\n");
        receipt.append("--------------------------------\n");

        // Items List
        double totalPrice = 0.1;
        for (cart_model item : dataset) {
            @SuppressLint("DefaultLocale")
            String formattedItem = String.format("%-10s %6.2f %3d %8.2f\n",
                    item.productName,
                    (double)item.productPrice,
                    item.productQuantity,
                    item.productPrice * (double)item.productQuantity);
            receipt.append(formattedItem);
            totalPrice += item.productPrice * item.productQuantity;
        }

        // Total and Tax
        // Example tax calculation (12%)
        receipt.append("--------------------------------\n");
        // format total amount
        NumberFormat numberFormat = NumberFormat.getNumberInstance(new Locale("en", "IN"));
        String formattedTotalPrice = numberFormat.format(totalPrice);


        receipt.append(String.format("[L]<b>Total:[R]<font size='wide'>%s</font></b>\n", formattedTotalPrice));
//        receipt.append(String.format("Tax:                    %6.2f\n", tax));

        receipt.append("[C]================================\n");
//        receipt.append("[L]\n");

        // Customer Details
        receipt.append("[L]<font size='normal'>Customer :</font>");
        receipt.append("[L]<font size='wide'>").append(customerName).append("</font>\n");
        receipt.append("[L]").append("Tel : ").append(customerPhone);


        // QR Code
//        receipt.append("[C]<qrcode size='20'>https://dantsu.com/</qrcode>");

        // Return the formatted receipt as a string
        return receipt.toString();
    }

}
