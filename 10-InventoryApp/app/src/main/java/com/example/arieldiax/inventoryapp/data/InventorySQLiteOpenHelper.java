package com.example.arieldiax.inventoryapp.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.arieldiax.inventoryapp.data.InventoryContract.ProductEntry;

public class InventorySQLiteOpenHelper extends SQLiteOpenHelper {

    /**
     * @const DATABASE_NAME Name of the database.
     */
    private static final String DATABASE_NAME = "inventory.db";

    /**
     * @const DATABASE_VERSION Version of the database.
     */
    private static final int DATABASE_VERSION = 1;

    /**
     * Creates a new InventorySQLiteOpenHelper object.
     *
     * @param context Instance of the Context class.
     */
    public InventorySQLiteOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(ProductEntry.SQL_STATEMENT_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Required empty public method.
    }
}
