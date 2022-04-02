package com.example.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class Db extends SQLiteOpenHelper {
    public static final String TABLE_CUSTOMER = "Customers";
    public static final String COLUMN_ID = "ID";
    public static final String COLUMN_CUSTOMER_NAME = "Customer_Name";


    public Db(@Nullable Context context) {

        super(context, "customers.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createTable = "CREATE TABLE " + TABLE_CUSTOMER + "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_CUSTOMER_NAME +" TEXT)";
        sqLiteDatabase.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public boolean addCustomer(Customers customers){
            SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
            ContentValues cv = new ContentValues();
            cv.put(COLUMN_CUSTOMER_NAME, customers.getName());

            long result = sqLiteDatabase.insert(TABLE_CUSTOMER, null, cv);

            if(result!=-1){
                return  true;
            }
            else{
                return false;
            }
    }

    public ArrayList<Customers> getCustomers(Context context){
        ArrayList<Customers> customerResultSet = new ArrayList<>();

        String query = "SELECT * FROM " + TABLE_CUSTOMER;
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        Cursor cursor = sqLiteDatabase.rawQuery(query, null);
        
            if(cursor.moveToFirst()){
                while(cursor.moveToNext()){
                    int id = cursor.getInt(0);
                    String customerName = cursor.getString(1);


                    Customers customers = new Customers(customerName);
                    customerResultSet.add(customers);
                }
            }
            else {
                    Toast.makeText(context, "FAiled", Toast.LENGTH_SHORT).show();
            }
            cursor.close();
            sqLiteDatabase.close();

        return customerResultSet;
    }
}
