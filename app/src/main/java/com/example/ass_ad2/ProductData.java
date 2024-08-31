package com.example.ass_ad2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class ProductData extends SQLiteOpenHelper {
    private static final String COLUMN_NAME = "namec";
    private static final String COLUMN_PRICE = "pricec";
    private static final String COLUMN_QUANTITY = "quantityc";

    public ProductData(@Nullable Context context) {
        super(context, "produtdi", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE producti ( id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_NAME + " TEXT," + COLUMN_PRICE + " TEXT," + COLUMN_QUANTITY + " TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS producti");
        onCreate(db);
    }

    public ArrayList<Product> GetDataProductList() {
        SQLiteDatabase db = getWritableDatabase();
        ArrayList<Product> list = new ArrayList<>();

        Cursor cursor = db.rawQuery("SELECT * FROM producti ", null);

        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            String price = cursor.getString(2);
            String quantity = cursor.getString(3);

            list.add(new Product(id, name, price, quantity));
        }
        return list;
    }

    public boolean InsertData(Product product) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_NAME, product.getName());
        values.put(COLUMN_PRICE, product.getPrice());
        values.put(COLUMN_QUANTITY, product.getQuantity());

        long result = db.insert("producti", null, values);

        if (result == -1) return false;
        else return true;
    }

    public boolean UpdateData(Product product) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, product.getName());
        values.put(COLUMN_PRICE, product.getPrice());
        values.put(COLUMN_QUANTITY, product.getQuantity());

        long result = db.update("producti", values, "id", new String[product.getId()]);

        if(result > 0 )return true;
        else return false;
    }
    public Boolean DeleteData(int id){
        SQLiteDatabase db = getWritableDatabase();

        long result = db.delete("producti", "id" + " =? ", new String[]{String.valueOf(id)});

        if (result > 0) return true;
        else return false;
    }


//
//    private static final String TABLE_NAME = "products";
//    private static final String COLUMN_TITLE = "title";
//    private static final String COLUMN_PRICE = "price";
//    private static final String COLUMN_QUANTITY = "quantity";
//
//    public ProductData(@Nullable Context context) {
//        super(context, "productttts", null, 1);
//    }
//
//    @Override
//    public void onCreate(SQLiteDatabase db) {
//        db.execSQL("CREATE TABLE " + TABLE_NAME + " ( " + " id INTEGER PRIMARY KEY AUTOINCREMENT,"
//                + COLUMN_TITLE + " TEXT ,"
//                + COLUMN_PRICE + " TEXT ,"
//                + COLUMN_QUANTITY + " TEXT " + " ) ");
//    }
//
//    @Override
//    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
//        onCreate(db);
//    }
//
//    public ArrayList<Product> GetdataProductList() {
//        SQLiteDatabase db = getReadableDatabase();
//        ArrayList<Product> list = new ArrayList<>();
//
//        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
//
//        while (cursor.moveToNext()) {
//            int id = cursor.getInt(0);
//            String name = cursor.getString(1);
//            String price = cursor.getString(2);
//            String quantity = cursor.getString(3);
//
//            list.add(new Product(id, name, price, quantity));
//        }
//        return list;
//    }
//
//    public boolean InsertProduct(Product products) {
//        SQLiteDatabase db = getWritableDatabase();
//        ContentValues values = new ContentValues();
//        values.put(COLUMN_TITLE, products.getName());
//        values.put(COLUMN_PRICE, products.getPrice());
//        values.put(COLUMN_QUANTITY, products.getQuantity());
//
//        long result = db.insert(TABLE_NAME, null, values);
//
//        if (result == -1) return false;
//        else return true;
//    }
//
//    public boolean UpdateProduct(Product products) {
//        SQLiteDatabase db = getWritableDatabase();
//        ContentValues values = new ContentValues();
//        values.put("id", products.getId());
//        values.put(COLUMN_TITLE, products.getName());
//        values.put(COLUMN_PRICE, products.getPrice());
//        values.put(COLUMN_QUANTITY, products.getQuantity());
//
//        long result = db.update(TABLE_NAME, values, "id" + "=?", new String[products.getId()]);
//
//        if (result > 0) return true;
//        else return false;
//    }
//
//    public boolean DeleteProduct(int id) {
//        SQLiteDatabase db = getWritableDatabase();
//
//        long result = db.delete(TABLE_NAME, "id" + " =? ", new String[]{String.valueOf(id)});
//
//        if (result > 0) return true;
//        else return false;
//    }
}
