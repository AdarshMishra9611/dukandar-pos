package com.example.dukandar20.Printer;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.dantsu.escposprinter.EscPosPrinter;
import com.dantsu.escposprinter.connection.bluetooth.BluetoothConnection;
import com.dantsu.escposprinter.connection.bluetooth.BluetoothPrintersConnections;
import com.dantsu.escposprinter.textparser.PrinterTextParserImg;
import com.example.dukandar20.R;

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

    public void doPrint() {
        checkPermissions();

        try {
            if (connectToPrinter()) {
                EscPosPrinter printer = new EscPosPrinter(connection, 203, 48f, 32);
                printer.printFormattedText(
                        "[C]<img>" + PrinterTextParserImg.bitmapToHexadecimalString(printer, context.getResources().getDrawableForDensity(R.drawable.a, DisplayMetrics.DENSITY_MEDIUM)) + "</img>\n" +
                                "[L]\n" +
                                "[C]<u><font size='big'>ORDER N°045</font></u>\n" +
                                "[L]\n" +
                                "[C]================================\n" +
                                "[L]\n" +
                                "[L]<b>BEAUTIFUL SHIRT</b>[R]9.99e\n" +
                                "[L]  + Size : S\n" +
                                "[L]\n" +
                                "[L]<b>AWESOME HAT</b>[R]24.99e\n" +
                                "[L]  + Size : 57/58\n" +
                                "[L]\n" +
                                "[C]--------------------------------\n" +
                                "[R]TOTAL PRICE :[R]34.98e\n" +
                                "[R]TAX :[R]4.23e\n" +
                                "[L]\n" +
                                "[C]================================\n" +
                                "[L]\n" +
                                "[L]<font size='tall'>Customer :</font>\n" +
                                "[L]Raymond DUPONT\n" +
                                "[L]5 rue des girafes\n" +
                                "[L]31547 PERPETES\n" +
                                "[L]Tel : +33801201456\n" +
                                "[L]\n" +
                                "[C]<barcode type='ean13' height='10'>831254784551</barcode>\n" +
                                "[C]<qrcode size='20'>https://dantsu.com/</qrcode>"
                );

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
}
