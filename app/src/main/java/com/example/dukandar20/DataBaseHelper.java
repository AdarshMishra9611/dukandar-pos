package com.example.dukandar20;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.dukandar20.models.BillItem;
import com.example.dukandar20.models.cart_model;
import com.example.dukandar20.models.due_customer_model;

import java.util.ArrayList;
import java.util.List;

public class DataBaseHelper extends SQLiteOpenHelper {

    private Context context;
    // Database variables
    public static final String DATABASE_NAME = "database.db";
    public static final int DATABASE_VERSION = 1;

    // Constants for the database table and column names

    // customer
    public static final String CUSTOMERS_TABLE_NAME = "Customers";
    public static final String CUSTOMER_ID = "CustomerID";
    public static final String CUSTOMER_NAME = "CustomerName";
    public static final String CUSTOMER_PHONE = "CustomerPhone";
    public static final String CAT_TABLE_NAME = "my_category";



    // category table
    public static final String CAT_ID = "cat_id";
    public static final String CAT_IMG = "cat_img";
    public static final String CAT_NAME = "category_name";

    // Item table

    public static final String ITM_TABLE_NAME = "my_item";
    public static final String ITM_ID = "itm_id";
    public static final String ITM_NAME = "item_name";
    public static final String ITM_PRC = "itm_prc";
    public static final String ITM_IMG = "itm_img";
    public static final String C_ID = "cat_id";



    /// cart table
    public static final String CART_TABLE_NAME = "my_Cart";
    public static final String CART_ID = "cart_id";
    public static final String PRODUCT_NAME = "product_name";
    public static final String PRODUCT_PRICE = "product_price";
    public static final String PRODUCT_QUANTITY = "product_quantity";

    //Bill table
    public static final String BILL_TABLE_NAME = "Bill";
    public static final String BILL_ID = "BillID";
    public static final String BILL_CUSTOMER_ID = "CustomerID";
    public static final String BILL_DATE = "BillDate";
    public static final String BILL_TOTAL_AMOUNT = "TotalAmount";
    public static final String BILL_STATUS = "Status";
    public static final String BILL_PAYMENT_METHOD = "PaymentMethod";

    // BillItem table
    public static final String BILL_ITEM_TABLE_NAME = "BillItem";
    public static final String BILL_ITEM_ID = "BillItemID";
    public static final String BILL_ITEM_BILL_ID = "BillID";
    public static final String BILL_ITEM_ITM_ID = "ItemName";
    public static final String BILL_ITEM_QUANTITY = "Quantity";

    public static final String BILL_ITEM_PRICE = "Price";

    // CustomerBalance table
    public static final String CUSTOMER_BALANCE_TABLE_NAME = "CustomerBalance";
    public static final String BALANCE_ID = "BalanceID";
    public static final String BALANCE_CUSTOMER_ID = "CustomerID";
    public static final String BALANCE_AMOUNT = "Balance";
    public static final String BALANCE_LAST_UPDATE = "LastUpdate";

    // BalanceHistory table
    public static final String BALANCE_HISTORY_TABLE_NAME = "BalanceHistory";
    public static final String HISTORY_ID = "HistoryID";
    public static final String HISTORY_CUSTOMER_ID = "CustomerID";
    public static final String HISTORY_BALANCE_CHANGE = "BalanceChange";
    public static final String HISTORY_CHANGE_DATE = "ChangeDate";


    public DataBaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // Query to create Customers table
        String createCustomersTableQuery = "CREATE TABLE " + CUSTOMERS_TABLE_NAME + " (" +
                CUSTOMER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                CUSTOMER_NAME + " TEXT NOT NULL, " +
                CUSTOMER_PHONE + " TEXT NOT NULL" +
                ");";


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

        // queary to create CartTable
        String createCartTableQuery = "CREATE TABLE " + CART_TABLE_NAME + " (" +
                CART_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                PRODUCT_NAME + " TEXT NOT NULL, " +
                PRODUCT_PRICE + " DECIMAL(10, 2) NOT NULL, " +
                PRODUCT_QUANTITY + " INTEGER NOT NULL" +
                ");";

// Queary to create Bill Table
        String createBillTableQuery = "CREATE TABLE " + BILL_TABLE_NAME + " (" +
                BILL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                BILL_CUSTOMER_ID + " INTEGER, " +
                BILL_DATE + " TEXT, " +
                BILL_TOTAL_AMOUNT + " REAL, " +
                BILL_STATUS + " TEXT, " +
                BILL_PAYMENT_METHOD + " TEXT, " +
                "FOREIGN KEY(" + BILL_CUSTOMER_ID + ") REFERENCES " + CUSTOMERS_TABLE_NAME + "(" + CUSTOMER_ID + ")" +
                ");";

        // Query to create BillItem table
        String createBillItemTableQuery = "CREATE TABLE " + BILL_ITEM_TABLE_NAME + " (" +
                BILL_ITEM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                BILL_ITEM_BILL_ID + " INTEGER, " +
                BILL_ITEM_ITM_ID + " TEXT, " +
                BILL_ITEM_QUANTITY + " INTEGER, " +

                BILL_ITEM_PRICE + " REAL, " +
                "FOREIGN KEY(" + BILL_ITEM_BILL_ID + ") REFERENCES " + BILL_TABLE_NAME + "(" + BILL_ID + "), " +
                "FOREIGN KEY(" + BILL_ITEM_ITM_ID + ") REFERENCES " + ITM_TABLE_NAME + "(" + ITM_ID + ")" +
                ");";

        // Query to create CustomerBalance table
        String createCustomerBalanceTableQuery = "CREATE TABLE " + CUSTOMER_BALANCE_TABLE_NAME + " (" +
                BALANCE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                BALANCE_CUSTOMER_ID + " INTEGER, " +
                BALANCE_AMOUNT + " REAL, " +
                BALANCE_LAST_UPDATE + " TEXT, " +
                "FOREIGN KEY(" + BALANCE_CUSTOMER_ID + ") REFERENCES " + CUSTOMERS_TABLE_NAME + "(" + CUSTOMER_ID + ")" +
                ");";



        // Query to create BalanceHistory table
        String createBalanceHistoryTableQuery = "CREATE TABLE " + BALANCE_HISTORY_TABLE_NAME + " (" +
                HISTORY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                HISTORY_CUSTOMER_ID + " INTEGER, " +
                HISTORY_BALANCE_CHANGE + " REAL, " +
                HISTORY_CHANGE_DATE + " TEXT, " +
                "FOREIGN KEY(" + HISTORY_CUSTOMER_ID + ") REFERENCES " + CUSTOMERS_TABLE_NAME + "(" + CUSTOMER_ID + ")" +
                ");";

        // Execute the queries to create the tables
        db.execSQL(createCategoryTableQuery);
        db.execSQL(createItemTableQuery);
        db.execSQL(createCartTableQuery);
        db.execSQL(createCustomersTableQuery);
        db.execSQL(createBillTableQuery);
        db.execSQL(createBillItemTableQuery);
        db.execSQL(createCustomerBalanceTableQuery);
        db.execSQL(createBalanceHistoryTableQuery);


        // Log the database path
        Log.d("DataBaseHelper", "Database created at: " + db.getPath());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop old tables if they exist
        db.execSQL("DROP TABLE IF EXISTS " + CAT_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + ITM_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + CART_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + CUSTOMERS_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + BILL_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + BILL_ITEM_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + CUSTOMER_BALANCE_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + BALANCE_HISTORY_TABLE_NAME);

        // Recreate tables
        onCreate(db);
    }



// Insert a new customer
public void insertCustomer(String name, String phone) {
    SQLiteDatabase db = this.getWritableDatabase();
    ContentValues contentValues = new ContentValues();
    contentValues.put(CUSTOMER_NAME, name);
    contentValues.put(CUSTOMER_PHONE, phone);
    long result = db.insert(CUSTOMERS_TABLE_NAME, null, contentValues);

    if (result == -1){
        Toast.makeText(context,"Failed",Toast.LENGTH_SHORT).show();

    }else {
        Toast.makeText(context,"Customer Added successfully",Toast.LENGTH_SHORT).show();
    }
    db.close();
}
// Update an existing customer
    public void updateCustomer(int customerId, String name, String phone) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CUSTOMER_NAME, name);
        contentValues.put(CUSTOMER_PHONE, phone);
        int result = db.update(CUSTOMERS_TABLE_NAME, contentValues, CUSTOMER_ID + " = ?", new String[]{String.valueOf(customerId)});
        db.close();

    }
    // Delete a customer
    public void deleteCustomer(int customerId) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete(CUSTOMERS_TABLE_NAME, CUSTOMER_ID + " = ?", new String[]{String.valueOf(customerId)});
        db.close();

     }
    // Get a single customer by ID
    public Cursor getCustomer(int customerId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(
                CUSTOMERS_TABLE_NAME,
                null, // Select all columns
                CUSTOMER_ID + " = ?",
                new String[]{String.valueOf(customerId)},
                null, // Group by
                null, // Having
                null  // Order by
        );
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor; // The caller is responsible for closing the cursor
    }
    // Get all customers
    public Cursor getAllCustomers() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(
                CUSTOMERS_TABLE_NAME,
                null, // Select all columns
                null, // No where clause
                null, // No where arguments
                null, // Group by
                null, // Having
                CUSTOMER_NAME + " ASC"  // Order by customer name in ascending order
        );
    }

//getcustomer




//Insert into  Category
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


// Read data from category table
    public Cursor  readCategoryData(){
        String queary = "SELECT * FROM "+ CAT_TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        if (db !=null ){
            cursor =  db.rawQuery(queary,null);
        }
        return cursor;
    }
//  Delete data from Category
    public  void  deleteCategory(int cat_id){
        SQLiteDatabase db = this.getWritableDatabase();
        String whereClause = C_ID+"= ?";
        String[] whereArgs = {String.valueOf(cat_id)};
        int result = db.delete(CAT_TABLE_NAME, whereClause, whereArgs);

        // Provide feedback to the user
        if (result == -1) {
            Toast.makeText(context, "Failed to delete category", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Category deleted successfully", Toast.LENGTH_SHORT).show();
        }
    }


// Update category
    public void updateCategory(int categoryID,String newCategoryName,byte[] newCategoryImage){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(CAT_NAME,newCategoryName);
        cv.put(CAT_IMG,newCategoryImage);

        String whereClause = C_ID + "= ?";
        String[] whereArgs = {String.valueOf(categoryID)};
        int result = db.update(CAT_TABLE_NAME,cv,whereClause,whereArgs);

        if (result == -1){
            Toast.makeText(context,"Failed to update category",Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context,"Category Update successfully",Toast.LENGTH_SHORT).show();
        }
    }


// get a row from category
    public Cursor gateCategoryById(int categoryId){
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT * FROM " +CAT_TABLE_NAME+" WHERE " + CAT_ID +" = ?";
        String[] selectionArgs ={String.valueOf(categoryId)};

        return db.rawQuery(query,selectionArgs);
    }


// Insert into item
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


//Read data from Item
    public Cursor readItemData(int c_id){
        String queary = "SELECT * FROM "+ ITM_TABLE_NAME+" WHERE "+ C_ID+" = " + c_id;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;

        if (db !=null ){
            cursor =  db.rawQuery(queary,null);
        }
        return cursor;
    }


// Insert into cart
    public void addItemsToCart(@NonNull cart_model item) {
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

    public void updateItemQuantity(@NonNull cart_model item) {
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
            Toast.makeText(context, "Item not found in cart: " + item.productName, Toast.LENGTH_SHORT).show();
        }

        if (cursor != null) {
            cursor.close();
        }
    }




    // Update item
   public  void updateItem(int itemId,String newItemName,double newItemPrice,byte[] newItemImage){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(ITM_NAME,newItemName);
        cv.put(ITM_PRC,newItemPrice);
        cv.put(ITM_IMG,newItemImage);

        String whereClause = ITM_ID+"= ?";
        String[] whereArgs = {String.valueOf(itemId)};
        int result = db.update(ITM_TABLE_NAME,cv,whereClause,whereArgs);

        if(result == -1){
            Toast.makeText(context,"Failed to update Item",Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context,"Category Update successfully",Toast.LENGTH_SHORT).show();
        }
   }


// Delete Item

  public void deleteItem(int itemId){
        SQLiteDatabase db = this.getWritableDatabase();
        String whereClause = ITM_ID+"= ?";
        String[] whereArgs = {String.valueOf(itemId)};

        int result = db.delete(ITM_TABLE_NAME,whereClause,whereArgs);

        if(result == -1){
            Toast.makeText(context,"Failed to Delete Item",Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context,"Item Deleted successfully",Toast.LENGTH_SHORT).show();
        }
  }


// get a row from Item
    public Cursor getItemByitemId(int itremId){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM "+ITM_TABLE_NAME+" WHERE "+ITM_ID+" = ?";
        String[] selectionArgs = {String.valueOf(itremId)};

        Cursor cursor = db.rawQuery(query,selectionArgs);
        return cursor;
    }


//Read from Cart
    public Cursor readCartData(){
        String queary = "SELECT * FROM "+CART_TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(queary,null);
        }
        return cursor;
    }

//Clear the cart
    public void clearCart() {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete(CART_TABLE_NAME, null, null);
        if (result == -1) {
            Toast.makeText(context, "Failed to clear cart", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Cart cleared successfully", Toast.LENGTH_SHORT).show();
        }
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








//Add this method to fetch the quantity of a specific item in the cart
    public Cursor getCartItemQuantity(String productName) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT " + PRODUCT_QUANTITY + " FROM " + CART_TABLE_NAME + " WHERE " + PRODUCT_NAME + " = ?";
        return db.rawQuery(query, new String[]{productName});
    }


// Insert into bills

    public long insertBill(int customerId, String billDate, double totalAmount, String status, String paymentMethod) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(BILL_CUSTOMER_ID, customerId);
        cv.put(BILL_DATE, billDate);
        cv.put(BILL_TOTAL_AMOUNT, totalAmount);
        cv.put(BILL_STATUS, status);
        cv.put(BILL_PAYMENT_METHOD, paymentMethod);

        long result = db.insert(BILL_TABLE_NAME, null, cv);
        if (result == -1) {
            Toast.makeText(context, "Failed to insert bill", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Bill inserted successfully", Toast.LENGTH_SHORT).show();
        }
        return result; // Returning the BillID for reference
    }

// fetch bill by date

    public Cursor getBillsByDate(String date){


        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT * FROM "+ BILL_TABLE_NAME +" WHERE " + BILL_DATE + " = ?";

        Cursor cursor = db.rawQuery(query,new String[]{date});
        return cursor;
    }
//get bill by bill id
    public Cursor getBillById(long billId){
        SQLiteDatabase db = getReadableDatabase();
        String query ="SELECT * FROM "+ BILL_TABLE_NAME + " WHERE "+ BILL_ID + " = ?";
        Cursor cursor = db.rawQuery(query,new String[]{String.valueOf(billId)});
        return  cursor;
    }


// insert into bill item

    public void insertBillItems(long billId, ArrayList<cart_model> billItems) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv;

        for (cart_model item : billItems) {
            cv = new ContentValues();
            cv.put(BILL_ITEM_BILL_ID, billId);
            cv.put(BILL_ITEM_ITM_ID, item.productName);
            cv.put(BILL_ITEM_QUANTITY, item.productQuantity);

            cv.put(BILL_ITEM_PRICE, item.productPrice);

            long result = db.insert(BILL_ITEM_TABLE_NAME, null, cv);
            if (result == -1) {
                Toast.makeText(context, "Failed to insert bill item: " + item.productName, Toast.LENGTH_SHORT).show();
            }
        }
        Toast.makeText(context, "Bill items inserted successfully", Toast.LENGTH_SHORT).show();
    }


// get bill item by bill id
    public Cursor getBillItemsByBillId(String billId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + BILL_ITEM_TABLE_NAME + " WHERE " + BILL_ITEM_BILL_ID + " = ?";
        return db.rawQuery(query, new String[]{String.valueOf(billId)});
    }


// get balance by id
    public double getCustomerBalanceById(int customerId) {
        SQLiteDatabase db = this.getReadableDatabase();
        double balance = 0.0;

        String query = "SELECT " + BALANCE_AMOUNT + " FROM " + CUSTOMER_BALANCE_TABLE_NAME +
                " WHERE " + BALANCE_CUSTOMER_ID + " = ?";

        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(customerId)});

        if (cursor.moveToFirst()) {
            balance = cursor.getDouble(cursor.getColumnIndexOrThrow(BALANCE_AMOUNT));
        }

        cursor.close();
        db.close();

        return balance;
    }

    public List<due_customer_model> getCustomersWithBalance() {
        List<due_customer_model> customersWithBalance = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        // Query to get all customers with a balance greater than 0
        String query = "SELECT c." + CUSTOMER_ID + ", c." + CUSTOMER_NAME + ", c." + CUSTOMER_PHONE +
                ", b." + BALANCE_AMOUNT +
                " FROM " + CUSTOMERS_TABLE_NAME + " c" +
                " JOIN " + CUSTOMER_BALANCE_TABLE_NAME + " b" +
                " ON c." + CUSTOMER_ID + " = b." + BALANCE_CUSTOMER_ID +
                " WHERE b." + BALANCE_AMOUNT + " > 0";

        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                int customerId = cursor.getInt(0);
                String customerName = cursor.getString(1);
                String customerPhone = cursor.getString(2);
                double balance = cursor.getDouble(3);

                // Create a DueCustomerModel object and add it to the list
                due_customer_model customer = new due_customer_model(customerId, customerName, customerPhone, balance);
                customersWithBalance.add(customer);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return customersWithBalance;
    }









}
