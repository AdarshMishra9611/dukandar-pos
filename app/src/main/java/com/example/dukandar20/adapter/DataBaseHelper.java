package com.example.dukandar20.adapter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;

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

    /// cart table
    public static final String CART_TABLE_NAME = "my_Cart";
    public static final String CART_ID = "cart_id";
    public static final String PRODUCT_NAME = "product_name";
    public static final String PRODUCT_PRICE = "product_price";
    public static final String PRODUCT_QUANTITY = "product_quantity";

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




        // queary to create CartTable
        String createCartTableQuery = "CREATE TABLE " + CART_TABLE_NAME + " (" +
                CART_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                PRODUCT_NAME + " TEXT NOT NULL, " +
                PRODUCT_PRICE + " DECIMAL(10, 2) NOT NULL, " +
                PRODUCT_QUANTITY + " INTEGER NOT NULL" +
                ");";

        // Execute the queries to create the tables
        db.execSQL(createCategoryTableQuery);
        db.execSQL(createItemTableQuery);
        db.execSQL(createBillsTableQuery);
        db.execSQL(createBillItemsTableQuery);
        db.execSQL(createCartTableQuery);

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
        db.execSQL("DROP TABLE IF EXISTS " + CART_TABLE_NAME);

        // Recreate tables
        onCreate(db);
    }

//insert into  Category
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

    //add to item
    public void  addItem(String item_Name,int item_price,int cat_id,byte[] item_img){
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(ITM_NAME,item_Name);
        cv.put(ITM_PRC,item_price);
        cv.put(C_ID,cat_id);
        cv.put(ITM_IMG,item_img);
        long result =db.insert(ITM_TABLE_NAME,null,cv);
        if (result == -1){
            Toast.makeText(context,"Failed",Toast.LENGTH_SHORT).show();

        }else {
            Toast.makeText(context,"Item Added successfully",Toast.LENGTH_SHORT).show();
        }

    }


    // add to cart
    // add to cart
    public void addItemsToCart(cart_model item) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Check if the item already exists in the cart
        String query = "SELECT * FROM " + CART_TABLE_NAME + " WHERE " + PRODUCT_NAME + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{item.productName});

        if (cursor != null && cursor.moveToFirst()) {
            // Item exists, update the quantity
            int currentQuantity = cursor.getInt(cursor.getColumnIndexOrThrow(PRODUCT_QUANTITY));
            int newQuantity = item.productQuantity;

            ContentValues cv = new ContentValues();
            cv.put(PRODUCT_QUANTITY, newQuantity);

            int result = db.update(CART_TABLE_NAME, cv, PRODUCT_NAME + " = ?", new String[]{item.productName});
            if (result == -1) {
                Toast.makeText(context, "Failed to update item quantity in cart: " + item.productName, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "Item quantity updated in cart: " + item.productName, Toast.LENGTH_SHORT).show();
            }
        } else {
            // Item does not exist, insert new item
            ContentValues cv = new ContentValues();
            cv.put(PRODUCT_NAME, item.productName);
            cv.put(PRODUCT_PRICE, item.productPrice);

            //if product queantiy is zero
            if(item.productQuantity == 0){
                item.productQuantity = 1;
            }
            cv.put(PRODUCT_QUANTITY, item.productQuantity);

            long result = db.insert(CART_TABLE_NAME, null, cv);
            if (result == -1) {
                Toast.makeText(context, "Failed to add item to cart: " + item.productName, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "Item added to cart: " + item.productName, Toast.LENGTH_SHORT).show();
            }
        }

        if (cursor != null) {
            cursor.close();
        }

        Toast.makeText(context, "Items processed successfully", Toast.LENGTH_SHORT).show();
    }






    // read data from category table
    public Cursor  readCategoryData(){
        String queary = "SELECT * FROM "+ CAT_TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        if (db !=null ){
          cursor =  db.rawQuery(queary,null);
        }
        return cursor;
    }


    // ITEM READ
    public Cursor readItemData(int c_id){
           String queary = "SELECT * FROM "+ ITM_TABLE_NAME+" WHERE "+ C_ID+" = " + c_id;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;

        if (db !=null ){
            cursor =  db.rawQuery(queary,null);
        }
        return cursor;
    }







    // cart read
    public Cursor readCartData(){
        String queary = "SELECT * FROM "+CART_TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(queary,null);
        }
        return cursor;
    }

    // to clear the cart

    public void clearCart() {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete(CART_TABLE_NAME, null, null);
        if (result == -1) {
            Toast.makeText(context, "Failed to clear cart", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Cart cleared successfully", Toast.LENGTH_SHORT).show();
        }
    }



    // Add this method to fetch the quantity of a specific item in the cart
    public Cursor getCartItemQuantity(String productName) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT " + PRODUCT_QUANTITY + " FROM " + CART_TABLE_NAME + " WHERE " + PRODUCT_NAME + " = ?";
        return db.rawQuery(query, new String[]{productName});
    }

    // Remove item from cart

    public void removeItemFromCart(String productName){
        SQLiteDatabase db = this.getWritableDatabase();
        // Define the criteria for deletion
        String whereClause = PRODUCT_NAME + "= ?";
        String[] whereArgs = {productName};

        // Perform the deletion

        int result = db.delete(CART_TABLE_NAME,whereClause,whereArgs);

        // Provide feedback to the user
        if (result == -1) {
            Toast.makeText(context, "Failed to remove item from cart: " + productName, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Item removed from cart: " + productName, Toast.LENGTH_SHORT).show();
        }


    }




}
