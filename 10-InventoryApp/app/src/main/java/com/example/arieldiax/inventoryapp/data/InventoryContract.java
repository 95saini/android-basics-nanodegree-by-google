package com.example.arieldiax.inventoryapp.data;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;
import android.text.TextUtils;

import com.example.arieldiax.inventoryapp.Utils;

public class InventoryContract {

    /**
     * @const CONTENT_AUTHORITY Authority of the content.
     */
    public static final String CONTENT_AUTHORITY = "com.example.arieldiax.inventoryapp";

    /**
     * @const CONTENT_URI_BASE Base URI of the content.
     */
    public static final Uri CONTENT_URI_BASE = Uri.parse("content://" + CONTENT_AUTHORITY);

    /**
     * @const TABLE_PATH_* Path of the tables.
     */
    public static final String TABLE_PATH_PRODUCTS = "products";

    /**
     * Creates a new InventoryContract object (no, it won't).
     */
    private InventoryContract() {
        // Required empty private constructor (to prevent instantiation).
    }

    public static final class ProductEntry implements BaseColumns {

        /**
         * @const CONTENT_URI URI of the content.
         */
        public static final Uri CONTENT_URI = Uri.withAppendedPath(CONTENT_URI_BASE, TABLE_PATH_PRODUCTS);

        /**
         * @const CONTENT_LIST_MIME_TYPE MIME type of the content list.
         */
        public static final String CONTENT_LIST_MIME_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + TABLE_PATH_PRODUCTS;

        /**
         * @const CONTENT_LIST_ITEM_MIME_TYPE MIME type of the content list item.
         */
        public static final String CONTENT_LIST_ITEM_MIME_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + TABLE_PATH_PRODUCTS;

        /**
         * @const TABLE_NAME Name of the table.
         */
        public static final String TABLE_NAME = "products";

        /**
         * @const COLUMN_NAME_* Name of the columns.
         */
        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_PRICE = "price";
        public static final String COLUMN_NAME_QUANTITY = "quantity";
        public static final String COLUMN_NAME_IMAGE = "image";
        public static final String COLUMN_NAME_CREATED_AT = "created_at";
        public static final String COLUMN_NAME_UPDATED_AT = "updated_at";

        /**
         * @const SQL_STATEMENT_CREATE_TABLE SQL statement to create the table.
         */
        public static final String SQL_STATEMENT_CREATE_TABLE = "" +
                "CREATE TABLE " + TABLE_NAME + " (" +
                _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME_NAME + " TEXT NOT NULL, " +
                COLUMN_NAME_PRICE + " REAL NOT NULL DEFAULT 0.00, " +
                COLUMN_NAME_QUANTITY + " INTEGER NOT NULL DEFAULT 0, " +
                COLUMN_NAME_IMAGE + " TEXT NOT NULL, " +
                COLUMN_NAME_CREATED_AT + " INTEGER NOT NULL DEFAULT (STRFTIME('%s', 'now')), " +
                COLUMN_NAME_UPDATED_AT + " INTEGER" +
                ");";

        /**
         * Determines whether or not the name field is valid.
         *
         * @param productName Field to validate.
         * @return Whether or not the name field is valid.
         */
        public static boolean isValidName(String productName) {
            return (!Utils.isNull(productName) && !TextUtils.isEmpty(productName));
        }

        /**
         * Determines whether or not the price field is valid.
         *
         * @param productPrice Field to validate.
         * @return Whether or not the price field is valid.
         */
        public static boolean isValidPrice(Double productPrice) {
            return (!Utils.isNull(productPrice) && Utils.isPositive(productPrice));
        }

        /**
         * Determines whether or not the quantity field is valid.
         *
         * @param productQuantity Field to validate.
         * @return Whether or not the quantity field is valid.
         */
        public static boolean isValidQuantity(Integer productQuantity) {
            return (!Utils.isNull(productQuantity) && Utils.isPositive(productQuantity));
        }

        /**
         * Determines whether or not the created_at field is valid.
         *
         * @param productCreatedAt Field to validate.
         * @return Whether or not the updated_at field is valid.
         */
        public static boolean isValidCreatedAt(Integer productCreatedAt) {
            return (!Utils.isNull(productCreatedAt) && Utils.isPositive(productCreatedAt));
        }

        /**
         * Determines whether or not the updated_at field is valid.
         *
         * @param productUpdatedAt Field to validate.
         * @return Whether or not the updated_at field is valid.
         */
        public static boolean isValidUpdatedAt(Integer productUpdatedAt) {
            return (!Utils.isNull(productUpdatedAt) && Utils.isPositive(productUpdatedAt));
        }
    }
}
