package com.example.arieldiax.inventoryapp;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.arieldiax.inventoryapp.data.InventoryContract.ProductEntry;

import java.sql.Timestamp;
import java.text.NumberFormat;

public class ProductCursorAdapter extends CursorAdapter {

    /**
     * @var mQuantityScaleSetting Setting value for quantity scale.
     */
    private int mQuantityScaleSetting;

    /**
     * @var mMainToast Toast message for interaction buttons.
     */
    private Toast mMainToast;

    /**
     * Creates a new ProductCursorAdapter object.
     *
     * @param context Instance of the Context class.
     * @param cursor  Instance of the Cursor class.
     */
    public ProductCursorAdapter(Context context, Cursor cursor) {
        super(context, cursor, 0);
        init(context);
    }

    /**
     * Initializes the back end logic bindings.
     *
     * @param context Instance of the Context class.
     */
    private void init(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        mQuantityScaleSetting = Integer.parseInt(sharedPreferences.getString(context.getString(R.string.setting_key_quantity_scale), context.getString(R.string.setting_default_quantity_scale)));
        mMainToast = Toast.makeText(context, "", Toast.LENGTH_SHORT);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.list_item_product, parent, false);
    }

    @Override
    public void bindView(View view, final Context context, Cursor cursor) {
        long id = cursor.getLong(cursor.getColumnIndex(ProductEntry._ID));
        final Uri currentProductUri = ContentUris.withAppendedId(ProductEntry.CONTENT_URI, id);
        ImageView productImageImageView = (ImageView) view.findViewById(R.id.product_image_image_view);
        String productImage = cursor.getString(cursor.getColumnIndex(ProductEntry.COLUMN_NAME_IMAGE));
        if (!TextUtils.isEmpty(productImage)) {
            productImageImageView.setImageBitmap(ImageUtils.decodeSampledBitmapFromUri(context, Uri.parse(productImage), 100, 100));
        } else {
            productImageImageView.setImageResource(R.drawable.android_studio_icon);
        }
        TextView productNameTextView = (TextView) view.findViewById(R.id.product_name_text_view);
        productNameTextView.setText(cursor.getString(cursor.getColumnIndex(ProductEntry.COLUMN_NAME_NAME)));
        TextView productPriceTextView = (TextView) view.findViewById(R.id.product_price_text_view);
        double productPrice = cursor.getDouble(cursor.getColumnIndex(ProductEntry.COLUMN_NAME_PRICE));
        productPriceTextView.setText(NumberFormat.getCurrencyInstance().format(productPrice));
        TextView productQuantityTextView = (TextView) view.findViewById(R.id.product_quantity_text_view);
        final int productQuantity = cursor.getInt(cursor.getColumnIndex(ProductEntry.COLUMN_NAME_QUANTITY));
        if (productQuantity > 0) {
            productQuantityTextView.setText(context.getString(R.string.product_formatted_quantity, NumberFormat.getInstance().format(productQuantity)));
        } else {
            productQuantityTextView.setText(R.string.product_formatted_quantity_empty);
        }
        TextView productLastUpdatedTextView = (TextView) view.findViewById(R.id.product_last_updated_text_view);
        long productCreatedAt = cursor.getLong(cursor.getColumnIndex(ProductEntry.COLUMN_NAME_CREATED_AT));
        long productUpdatedAt = cursor.getLong(cursor.getColumnIndex(ProductEntry.COLUMN_NAME_UPDATED_AT));
        long productLastUpdated = (productUpdatedAt > 0) ? productUpdatedAt : productCreatedAt;
        productLastUpdatedTextView.setText(Utils.formatTimestamp(productLastUpdated, "yyyy-MM-dd HH:mm:ss"));
        Button productQuantitySaleButton = (Button) view.findViewById(R.id.product_quantity_sale_button);
        productQuantitySaleButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                updateProductQuantity(context, currentProductUri, productQuantity);
            }
        });
    }

    /**
     * Updates the quantity column into the products table.
     *
     * @param context           Instance of the Context class.
     * @param currentProductUri URI of the current product.
     * @param productQuantity   Quantity of the product.
     */
    private void updateProductQuantity(Context context, Uri currentProductUri, int productQuantity) {
        if (Utils.isPositive(productQuantity - mQuantityScaleSetting)) {
            productQuantity -= mQuantityScaleSetting;
        } else {
            mMainToast.setText(R.string.message_cannot_decrease_anymore);
            mMainToast.show();
            return;
        }
        long currentTimestamp = (new Timestamp(System.currentTimeMillis())).getTime();
        ContentValues product = new ContentValues();
        product.put(ProductEntry.COLUMN_NAME_QUANTITY, productQuantity);
        product.put(ProductEntry.COLUMN_NAME_UPDATED_AT, currentTimestamp);
        int rowsUpdated = context.getContentResolver().update(currentProductUri, product, null, null);
        if (rowsUpdated > 0) {
            mMainToast.setText(R.string.message_product_updated);
        } else {
            mMainToast.setText(R.string.message_error_updating_product);
        }
        mMainToast.show();
    }
}
