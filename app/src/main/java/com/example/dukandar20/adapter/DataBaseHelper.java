package com.example.dukandar20.adapter;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class DataBaseHelper extends SQLiteOpenHelper {

    private Context context;
    // Database variables
    public static final String DATABASE_NAME = "database.db";
    public static final int DATABASE_VERSION = 1;

    // Constants for the database table and column names
    public static final String CAT_TABLE_NAME = "my_category";
    public static final String CAT_ID = "cat_id";
    public static final String CAT_IMG = "cat_img";
    public static final String CAT_NAME = "category_name";

    public static final String ITM_TABLE_NAME = "my_item";
    public static final String ITM_ID = "itm_id";
    public static final String ITM_NAME = "item_name";
    public static final String ITM_PRC = "itm_prc";
    public static final String ITM_IMG = "itm_img";
    public static final String C_ID = "cat_id";

    public static final String BILLS_TABLE_NAME = "Bills";
    public static final String BILL_ID = "BillID";
    public static final String CUSTOMER_ID = "CustomerID";
    public static final String BILL_DATE = "BillDate";
    public static final String DUE_DATE = "DueDate";
    public static final String TOTAL_AMOUNT = "TotalAmount";
    public static final String STATUS = "Status";

    public static final String BILL_ITEMS_TABLE_NAME = "BillItems";
    public static final String ITEM_ID = "ItemID";
    public static final String BILL_ID_REF = "BillID"; // Reference to BillID in BillItems table
    public static final String PRODUCT_ID = "ProductID";
    public static final String QUANTITY = "Quantity";
    public static final String UNIT_PRICE = "UnitPrice";
    public static final String TOTAL_PRICE = "TotalPrice";

    public DataBaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Query to create my_category table
        String createCategoryTableQuery = "CREATE TABLE " + CAT_TABLE_NAME + " (" +
                CAT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                CAT_NAME + " TEXT, " +
                CAT_IMG + " BLOB" +
                ");";

        // Query to create my_item table
        String createItemTableQuery = "CREATE TABLE " + ITM_TABLE_NAME + " (" +
                ITM_ID + " INTEGER PRIMARY KEY, " +
                ITM_NAME + " TEXT, " +
                ITM_PRC + " REAL, " +
                C_ID + " INTEGER, " +
                ITM_IMG + " BLOB, " +
                "FOREIGN KEY(" + C_ID + ") REFERENCES " + CAT_TABLE_NAME + "(" + CAT_ID + ")" +
                ");";

        // Query to create Bills table
        String createBillsTableQuery = "CREATE TABLE " + BILLS_TABLE_NAME + " (" +
                BILL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                CUSTOMER_ID + " INTEGER, " +
                BILL_DATE + " DATE NOT NULL, " +
                DUE_DATE + " DATE, " +
                TOTAL_AMOUNT + " DECIMAL(10, 2) NOT NULL, " +
                STATUS + " VARCHAR(50) NOT NULL, " +
                "FOREIGN KEY(" + CUSTOMER_ID + ") REFERENCES Customers(CustomerID)" +
                ");";

        // Query to create BillItems table
        String createBillItemsTableQuery = "CREATE TABLE " + BILL_ITEMS_TABLE_NAME + " (" +
                ITEM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                BILL_ID_REF + " INTEGER, " +
                PRODUCT_ID + " INTEGER, " +
                QUANTITY + " INTEGER NOT NULL, " +
                UNIT_PRICE + " DECIMAL(10, 2) NOT NULL, " +
                TOTAL_PRICE + " DECIMAL(10, 2) NOT NULL, " +
                "FOREIGN KEY(" + BILL_ID_REF + ") REFERENCES " + BILLS_TABLE_NAME + "(" + BILL_ID + "), " +
                "FOREIGN KEY(" + PRODUCT_ID + ") REFERENCES Products(ProductID)" +
                ");";

        // Execute the queries to create the tables
        db.execSQL(createCategoryTableQuery);
        db.execSQL(createItemTableQuery);
        db.execSQL(createBillsTableQuery);
        db.execSQL(createBillItemsTableQuery);

        // Log the database path
        Log.d("DataBaseHelper", "Database created at: " + db.getPath());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop old tables if they exist
        db.execSQL("DROP TABLE IF EXISTS " + CAT_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + ITM_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + BILLS_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + BILL_ITEMS_TABLE_NAME);

        // Recreate tables
        onCreate(db);
    }


    public void addCategory(String categoryName, byte[] cat_image){
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(CAT_NAME,categoryName);
        cv.put(CAT_IMG, cat_image);
        long result = db.insert(CAT_TABLE_NAME,null,cv);
        if (result == -1){
            Toast.makeText(context,"Failed",Toast.LENGTH_SHORT).show();

        }else {
            Toast.makeText(context,"Category Added successfully",Toast.LENGTH_SHORT).show();
        }
    }
}
