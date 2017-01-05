package com.example.arieldiax.inventoryapp.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;

import com.example.arieldiax.inventoryapp.data.InventoryContract.ProductEntry;

public class InventoryContentProvider extends ContentProvider {

    /**
     * @var mInventorySQLiteOpenHelper SQLite open helper of the inventory.
     */
    private InventorySQLiteOpenHelper mInventorySQLiteOpenHelper;

    /**
     * @const PATH_MATCH_* Match of the paths.
     */
    private static final int PATH_MATCH_PRODUCTS = 100;
    private static final int PATH_MATCH_PRODUCTS_ID = 101;

    /**
     * @const sUriMatcher Matcher of the URI.
     */
    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        sUriMatcher.addURI(InventoryContract.CONTENT_AUTHORITY, InventoryContract.TABLE_PATH_PRODUCTS, PATH_MATCH_PRODUCTS);
        sUriMatcher.addURI(InventoryContract.CONTENT_AUTHORITY, InventoryContract.TABLE_PATH_PRODUCTS + "/#", PATH_MATCH_PRODUCTS_ID);
    }

    @Override
    public boolean onCreate() {
        mInventorySQLiteOpenHelper = new InventorySQLiteOpenHelper(getContext());
        return false;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteDatabase database = mInventorySQLiteOpenHelper.getReadableDatabase();
        Cursor cursor = null;
        switch (sUriMatcher.match(uri)) {
            case PATH_MATCH_PRODUCTS:
                cursor = database.query(ProductEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            case PATH_MATCH_PRODUCTS_ID:
                selection = ProductEntry._ID + " = ?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                cursor = database.query(ProductEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, null);
                break;
            default:
                throw new IllegalArgumentException("Error calling `query` with URI: '" + uri + "'");
        }
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        if (values.size() == 0) {
            return null;
        }
        long rowId = 0;
        switch (sUriMatcher.match(uri)) {
            case PATH_MATCH_PRODUCTS:
                rowId = insertProduct(values);
                break;
            default:
                throw new IllegalArgumentException("Error calling `insert` with URI: '" + uri + "'");
        }
        if (rowId == -1) {
            return null;
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return ContentUris.withAppendedId(uri, rowId);
    }

    /**
     * Inserts the row into the products table.
     *
     * @param values A set of column_name/value pairs to add to the database.
     * @return The ID for the newly inserted item.
     */
    private long insertProduct(ContentValues values) {
        validateNewProduct(values);
        SQLiteDatabase database = mInventorySQLiteOpenHelper.getWritableDatabase();
        return database.insert(ProductEntry.TABLE_NAME, null, values);
    }

    @Override
    public int bulkInsert(Uri uri, ContentValues[] values) {
        if (values.length == 0) {
            return 0;
        }
        int rowsInserted = 0;
        switch (sUriMatcher.match(uri)) {
            case PATH_MATCH_PRODUCTS:
                rowsInserted = insertProducts(values);
                break;
            default:
                throw new IllegalArgumentException("Error calling `bulkInsert` with URI: '" + uri + "'");
        }
        if (rowsInserted > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsInserted;
    }

    /**
     * Inserts the rows into the products table.
     *
     * @param values An array of sets of column_name/value pairs to add to the database.
     * @return The number of values that were inserted.
     */
    private int insertProducts(ContentValues[] values) {
        SQLiteDatabase database = mInventorySQLiteOpenHelper.getWritableDatabase();
        database.beginTransaction();
        int rowsInserted = 0;
        try {
            for (ContentValues product : values) {
                validateNewProduct(product);
                database.insert(ProductEntry.TABLE_NAME, null, product);
            }
            database.setTransactionSuccessful();
            rowsInserted = values.length;
        } catch (Exception exception) {
            exception.printStackTrace();
        } finally {
            database.endTransaction();
        }
        return rowsInserted;
    }

    /**
     * Validates the new row against the products table.
     *
     * @param values A set of column_name/value pairs to add to the database.
     */
    private void validateNewProduct(ContentValues values) {
        String productName = values.getAsString(ProductEntry.COLUMN_NAME_NAME);
        if (!ProductEntry.isValidName(productName)) {
            throw new IllegalArgumentException("Error calling `validateNewProduct` with `name`: '" + productName + "'");
        }
        Double productPrice = values.getAsDouble(ProductEntry.COLUMN_NAME_PRICE);
        if (!ProductEntry.isValidPrice(productPrice)) {
            throw new IllegalArgumentException("Error calling `validateNewProduct` with `price`: '" + productPrice + "'");
        }
        Integer productQuantity = values.getAsInteger(ProductEntry.COLUMN_NAME_QUANTITY);
        if (!ProductEntry.isValidQuantity(productQuantity)) {
            throw new IllegalArgumentException("Error calling `validateNewProduct` with `quantity`: '" + productQuantity + "'");
        }
        Integer productCreatedAt = values.getAsInteger(ProductEntry.COLUMN_NAME_CREATED_AT);
        if (!ProductEntry.isValidCreatedAt(productCreatedAt)) {
            throw new IllegalArgumentException("Error calling `validateNewProduct` with `created_at`: '" + productCreatedAt + "'");
        }
        if (values.containsKey(ProductEntry.COLUMN_NAME_UPDATED_AT)) {
            Integer productUpdatedAt = values.getAsInteger(ProductEntry.COLUMN_NAME_UPDATED_AT);
            throw new IllegalArgumentException("Cannot call `validateNewProduct` with `updated_at`: '" + productUpdatedAt + "'");
        }
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        if (values.size() == 0) {
            return 0;
        }
        int rowsUpdated = 0;
        switch (sUriMatcher.match(uri)) {
            case PATH_MATCH_PRODUCTS:
                rowsUpdated = updateProducts(values, selection, selectionArgs);
                break;
            case PATH_MATCH_PRODUCTS_ID:
                selection = ProductEntry._ID + " = ?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                rowsUpdated = updateProducts(values, selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Error calling `update` with URI: '" + uri + "'");
        }
        if (rowsUpdated > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsUpdated;
    }

    /**
     * Updates the rows from the products table.
     *
     * @param values        A set of column_name/value pairs to update in the database.
     * @param selection     An optional filter to match rows to update.
     * @param selectionArgs
     * @return The number of rows affected.
     */
    private int updateProducts(ContentValues values, String selection, String[] selectionArgs) {
        validateExistingProduct(values);
        SQLiteDatabase database = mInventorySQLiteOpenHelper.getWritableDatabase();
        return database.update(ProductEntry.TABLE_NAME, values, selection, selectionArgs);
    }

    /**
     * Validates the existing row against the products table.
     *
     * @param values A set of column_name/value pairs to update in the database.
     */
    private void validateExistingProduct(ContentValues values) {
        if (values.containsKey(ProductEntry.COLUMN_NAME_NAME)) {
            String productName = values.getAsString(ProductEntry.COLUMN_NAME_NAME);
            if (!ProductEntry.isValidName(productName)) {
                throw new IllegalArgumentException("Error calling `validateExistingProduct` with `name`: '" + productName + "'");
            }
        }
        if (values.containsKey(ProductEntry.COLUMN_NAME_PRICE)) {
            Double productPrice = values.getAsDouble(ProductEntry.COLUMN_NAME_PRICE);
            if (!ProductEntry.isValidPrice(productPrice)) {
                throw new IllegalArgumentException("Error calling `validateExistingProduct` with `price`: '" + productPrice + "'");
            }
        }
        if (values.containsKey(ProductEntry.COLUMN_NAME_QUANTITY)) {
            Integer productQuantity = values.getAsInteger(ProductEntry.COLUMN_NAME_QUANTITY);
            if (!ProductEntry.isValidQuantity(productQuantity)) {
                throw new IllegalArgumentException("Error calling `validateExistingProduct` with `quantity`: '" + productQuantity + "'");
            }
        }
        if (values.containsKey(ProductEntry.COLUMN_NAME_CREATED_AT)) {
            Integer productCreatedAt = values.getAsInteger(ProductEntry.COLUMN_NAME_CREATED_AT);
            throw new IllegalArgumentException("Cannot call `validateExistingProduct` with `created_at`: '" + productCreatedAt + "'");
        }
        if (values.containsKey(ProductEntry.COLUMN_NAME_UPDATED_AT)) {
            Integer productUpdatedAt = values.getAsInteger(ProductEntry.COLUMN_NAME_UPDATED_AT);
            if (!ProductEntry.isValidUpdatedAt(productUpdatedAt)) {
                throw new IllegalArgumentException("Error calling `validateExistingProduct` with `updated_at`: '" + productUpdatedAt + "'");
            }
        }
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase database = mInventorySQLiteOpenHelper.getWritableDatabase();
        int rowsDeleted = 0;
        switch (sUriMatcher.match(uri)) {
            case PATH_MATCH_PRODUCTS:
                rowsDeleted = database.delete(ProductEntry.TABLE_NAME, selection, selectionArgs);
                break;
            case PATH_MATCH_PRODUCTS_ID:
                selection = ProductEntry._ID + " = ?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                rowsDeleted = database.delete(ProductEntry.TABLE_NAME, selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Error calling `delete` with URI: '" + uri + "'");
        }
        if (rowsDeleted > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsDeleted;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        switch (sUriMatcher.match(uri)) {
            case PATH_MATCH_PRODUCTS:
                return ProductEntry.CONTENT_LIST_MIME_TYPE;
            case PATH_MATCH_PRODUCTS_ID:
                return ProductEntry.CONTENT_LIST_ITEM_MIME_TYPE;
            default:
                throw new IllegalStateException("Error calling `getType` with URI: '" + uri + "'");
        }
    }
}
