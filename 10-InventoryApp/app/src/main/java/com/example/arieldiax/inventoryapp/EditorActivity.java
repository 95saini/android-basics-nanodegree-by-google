package com.example.arieldiax.inventoryapp;

import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.arieldiax.inventoryapp.data.InventoryContract.ProductEntry;

import java.sql.Timestamp;
import java.util.Locale;

public class EditorActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    /**
     * @var mProductNameEditText Edit text field for product name.
     */
    private EditText mProductNameEditText;

    /**
     * @var mProductPriceEditText Edit text field for product price.
     */
    private EditText mProductPriceEditText;

    /**
     * @var mProductQuantityEditText Edit text field for product quantity.
     */
    private EditText mProductQuantityEditText;

    /**
     * @var mProductQuantity(Minus|Plus)Button Button fields for product quantity.
     */
    private Button mProductQuantityMinusButton;
    private Button mProductQuantityPlusButton;

    /**
     * @var mProductImageImageView Image view field for product image.
     */
    private ImageView mProductImageImageView;

    /**
     * @var mProductImage(Select|Remove)Button Button fields for product image.
     */
    private Button mProductImageSelectButton;
    private Button mProductImageRemoveButton;

    /**
     * @var mSupplierOrderMoreButton Button field for supplier order more.
     */
    private Button mSupplierOrderMoreButton;

    /**
     * @var mCurrentProductUri URI of the current product.
     */
    private Uri mCurrentProductUri;

    /**
     * @var mCurrentProductImageUri URI of the current product image.
     */
    private Uri mCurrentProductImageUri;

    /**
     * @var mQuantityScaleSetting Setting value for quantity scale.
     */
    private int mQuantityScaleSetting;

    /**
     * @var mIsDeletingProductFlag Flag for is deleting product.
     */
    private boolean mIsDeletingProductFlag;

    /**
     * @var mProductHasChangedFlag Flag for product has changed.
     */
    private boolean mProductHasChangedFlag;

    /**
     * @var mMainToast Toast message for interaction buttons.
     */
    private Toast mMainToast;

    /**
     * @var mProductQuantityButtonsOnClickListener On click view listener for product quantity buttons.
     */
    private View.OnClickListener mProductQuantityButtonsOnClickListener;

    /**
     * @var mProductFieldsOnTouchListener On touch view listener for product fields.
     */
    private View.OnTouchListener mProductFieldsOnTouchListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);
        initUi();
        init();
        initListeners();
    }

    /**
     * Initializes the user interface view bindings.
     */
    private void initUi() {
        mProductNameEditText = (EditText) findViewById(R.id.product_name_edit_text);
        mProductPriceEditText = (EditText) findViewById(R.id.product_price_edit_text);
        mProductQuantityEditText = (EditText) findViewById(R.id.product_quantity_edit_text);
        mProductQuantityMinusButton = (Button) findViewById(R.id.product_quantity_minus_button);
        mProductQuantityPlusButton = (Button) findViewById(R.id.product_quantity_plus_button);
        mProductImageImageView = (ImageView) findViewById(R.id.product_image_image_view);
        mProductImageSelectButton = (Button) findViewById(R.id.product_image_select_button);
        mProductImageRemoveButton = (Button) findViewById(R.id.product_image_remove_button);
        mSupplierOrderMoreButton = (Button) findViewById(R.id.supplier_order_more_button);
    }

    /**
     * Initializes the back end logic bindings.
     */
    private void init() {
        mCurrentProductUri = getIntent().getData();
        mCurrentProductImageUri = null;
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mQuantityScaleSetting = Integer.parseInt(sharedPreferences.getString(getString(R.string.setting_key_quantity_scale), getString(R.string.setting_default_quantity_scale)));
        mIsDeletingProductFlag = false;
        if (Utils.isNull(mCurrentProductUri)) {
            setTitle(R.string.app_title_add_product);
            invalidateOptionsMenu();
            mProductImageImageView.setImageResource(R.drawable.android_studio_icon);
            mProductImageRemoveButton.setVisibility(View.GONE);
            mSupplierOrderMoreButton.setVisibility(View.GONE);
        } else {
            setTitle(R.string.app_title_edit_product);
            getLoaderManager().initLoader(0, null, this);
        }
        mProductHasChangedFlag = false;
        mMainToast = Toast.makeText(getApplicationContext(), "", Toast.LENGTH_SHORT);
        mProductQuantityButtonsOnClickListener = new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String productQuantityString = ViewUtils.getEditTextValue(mProductQuantityEditText);
                int productQuantity = (!TextUtils.isEmpty(productQuantityString)) ? Integer.parseInt(productQuantityString) : 0;
                ViewUtils.hideKeyboard(EditorActivity.this);
                switch (view.getId()) {
                    case R.id.product_quantity_minus_button:
                        if (Utils.isPositive(productQuantity - mQuantityScaleSetting)) {
                            productQuantity -= mQuantityScaleSetting;
                        } else {
                            mMainToast.setText(R.string.message_cannot_decrease_anymore);
                            mMainToast.show();
                        }
                        break;
                    case R.id.product_quantity_plus_button:
                        productQuantity += mQuantityScaleSetting;
                        break;
                }
                mProductQuantityEditText.setText(String.valueOf(productQuantity));
                mProductQuantityEditText.clearFocus();
                mProductQuantityEditText.setError(null);
            }
        };
        mProductFieldsOnTouchListener = new View.OnTouchListener() {

            @Override
            public boolean onTouch(View view, MotionEvent event) {
                mProductHasChangedFlag = true;
                return false;
            }
        };
    }

    /**
     * Initializes the event listener view bindings.
     */
    private void initListeners() {
        mProductQuantityMinusButton.setOnClickListener(mProductQuantityButtonsOnClickListener);
        mProductQuantityPlusButton.setOnClickListener(mProductQuantityButtonsOnClickListener);
        mProductImageSelectButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent pickImageIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                startActivityForResult(pickImageIntent, 0);
            }
        });
        mProductImageRemoveButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                mCurrentProductImageUri = null;
                mProductImageImageView.setImageResource(R.drawable.android_studio_icon);
                mProductImageRemoveButton.setVisibility(View.GONE);
                mMainToast.setText(R.string.message_product_image_removed);
                mMainToast.show();
            }
        });
        mSupplierOrderMoreButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String productName = ViewUtils.getEditTextValue(mProductNameEditText).trim();
                if (!ProductEntry.isValidName(productName)) {
                    mProductNameEditText.setError(getString(R.string.message_missing_required_field));
                    return;
                }
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
                emailIntent.setData(Uri.parse("mailto:"));
                emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{
                        getString(R.string.supplier_default_email_address),
                });
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.supplier_default_email_subject, productName));
                emailIntent.putExtra(Intent.EXTRA_TEXT, getString(R.string.supplier_default_email_text, mQuantityScaleSetting));
                if (!Utils.isNull(emailIntent.resolveActivity(getPackageManager()))) {
                    startActivity(emailIntent);
                } else {
                    mMainToast.setText(R.string.message_no_applications_found);
                    mMainToast.show();
                }
            }
        });
        mProductNameEditText.setOnTouchListener(mProductFieldsOnTouchListener);
        mProductPriceEditText.setOnTouchListener(mProductFieldsOnTouchListener);
        mProductQuantityEditText.setOnTouchListener(mProductFieldsOnTouchListener);
        mProductQuantityMinusButton.setOnTouchListener(mProductFieldsOnTouchListener);
        mProductQuantityPlusButton.setOnTouchListener(mProductFieldsOnTouchListener);
        mProductImageSelectButton.setOnTouchListener(mProductFieldsOnTouchListener);
        mProductImageRemoveButton.setOnTouchListener(mProductFieldsOnTouchListener);
    }

    @Override
    public void onBackPressed() {
        if (!mProductHasChangedFlag) {
            super.onBackPressed();
            return;
        }
        DialogInterface.OnClickListener positiveButtonOnClickListener = new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        };
        ViewUtils.showAlertDialog(this, R.string.title_discard_changes, positiveButtonOnClickListener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_editor, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        if (Utils.isNull(mCurrentProductUri)) {
            MenuItem deleteProductMenuItem = menu.findItem(R.id.delete_product_menu_item);
            deleteProductMenuItem.setVisible(false);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case android.R.id.home:
                showDiscardProductAlertDialog();
                return true;
            case R.id.save_product_menu_item:
                saveProduct();
                return true;
            case R.id.delete_product_menu_item:
                showDeleteProductAlertDialog();
                return true;
        }
        return super.onOptionsItemSelected(menuItem);
    }

    /**
     * Shows an alert dialog, before it discards the product.
     */
    private void showDiscardProductAlertDialog() {
        if (!mProductHasChangedFlag) {
            NavUtils.navigateUpFromSameTask(this);
            return;
        }
        DialogInterface.OnClickListener positiveButtonOnClickListener = new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                NavUtils.navigateUpFromSameTask(EditorActivity.this);
            }
        };
        ViewUtils.showAlertDialog(this, R.string.title_discard_changes, positiveButtonOnClickListener);
    }

    /**
     * Saves the row into the products table.
     */
    private void saveProduct() {
        boolean productHasErrorFlag = false;
        String productName = ViewUtils.getEditTextValue(mProductNameEditText).trim();
        if (!ProductEntry.isValidName(productName)) {
            productHasErrorFlag = true;
            mProductNameEditText.setError(getString(R.string.message_missing_required_field));
        }
        String productPriceString = ViewUtils.getEditTextValue(mProductPriceEditText);
        Double productPrice = (!TextUtils.isEmpty(productPriceString)) ? Double.parseDouble(productPriceString) : -1.00;
        if (!ProductEntry.isValidPrice(productPrice)) {
            productHasErrorFlag = true;
            mProductPriceEditText.setError(getString(R.string.message_missing_required_field));
        }
        String productQuantityString = ViewUtils.getEditTextValue(mProductQuantityEditText);
        int productQuantity = (!TextUtils.isEmpty(productQuantityString)) ? Integer.parseInt(productQuantityString) : -1;
        if (!ProductEntry.isValidQuantity(productQuantity)) {
            productHasErrorFlag = true;
            mProductQuantityEditText.setError(getString(R.string.message_missing_required_field));
        }
        String productImage = (!Utils.isNull(mCurrentProductImageUri)) ? mCurrentProductImageUri.toString() : "";
        if (productHasErrorFlag) {
            return;
        }
        ContentValues product = new ContentValues();
        product.put(ProductEntry.COLUMN_NAME_NAME, productName);
        product.put(ProductEntry.COLUMN_NAME_PRICE, productPrice);
        product.put(ProductEntry.COLUMN_NAME_QUANTITY, productQuantity);
        product.put(ProductEntry.COLUMN_NAME_IMAGE, productImage);
        long currentTimestamp = (new Timestamp(System.currentTimeMillis())).getTime();
        boolean productHasSavedFlag = false;
        if (Utils.isNull(mCurrentProductUri)) {
            product.put(ProductEntry.COLUMN_NAME_CREATED_AT, currentTimestamp);
            productHasSavedFlag = !Utils.isNull(getContentResolver().insert(ProductEntry.CONTENT_URI, product));
        } else {
            product.put(ProductEntry.COLUMN_NAME_UPDATED_AT, currentTimestamp);
            productHasSavedFlag = (getContentResolver().update(mCurrentProductUri, product, null, null) > 0);
        }
        if (productHasSavedFlag) {
            mMainToast.setText(R.string.message_product_saved);
        } else {
            mMainToast.setText(R.string.message_error_saving_product);
        }
        mMainToast.show();
        finish();
    }

    /**
     * Shows an alert dialog, before it deletes the product.
     */
    private void showDeleteProductAlertDialog() {
        DialogInterface.OnClickListener positiveButtonOnClickListener = new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                deleteProduct();
                finish();
            }
        };
        ViewUtils.showAlertDialog(this, R.string.title_delete_product, positiveButtonOnClickListener);
    }

    /**
     * Deletes the row from the products table.
     */
    private void deleteProduct() {
        mIsDeletingProductFlag = true;
        int rowsDeleted = getContentResolver().delete(mCurrentProductUri, null, null);
        if (rowsDeleted > 0) {
            mMainToast.setText(R.string.message_product_deleted);
        } else {
            mMainToast.setText(R.string.message_error_deleting_product);
        }
        mMainToast.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            mCurrentProductImageUri = data.getData();
            mProductImageImageView.setImageBitmap(ImageUtils.decodeSampledBitmapFromUri(getApplicationContext(), mCurrentProductImageUri, 300, 300));
            mProductImageRemoveButton.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle bundle) {
        String[] projection = new String[]{
                ProductEntry._ID,
                ProductEntry.COLUMN_NAME_NAME,
                ProductEntry.COLUMN_NAME_PRICE,
                ProductEntry.COLUMN_NAME_QUANTITY,
                ProductEntry.COLUMN_NAME_IMAGE,
        };
        return new CursorLoader(this, mCurrentProductUri, projection, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor product) {
        if (product.moveToFirst()) {
            mProductNameEditText.setText(product.getString(product.getColumnIndex(ProductEntry.COLUMN_NAME_NAME)));
            mProductPriceEditText.setText(String.format(Locale.getDefault(), "%.2f", product.getDouble(product.getColumnIndex(ProductEntry.COLUMN_NAME_PRICE))));
            mProductQuantityEditText.setText(String.valueOf(product.getInt(product.getColumnIndex(ProductEntry.COLUMN_NAME_QUANTITY))));
            String productImage = product.getString(product.getColumnIndex(ProductEntry.COLUMN_NAME_IMAGE));
            if (!TextUtils.isEmpty(productImage)) {
                mCurrentProductImageUri = Uri.parse(productImage);
                mProductImageImageView.setImageBitmap(ImageUtils.decodeSampledBitmapFromUri(getApplicationContext(), mCurrentProductImageUri, 300, 300));
            } else {
                mProductImageImageView.setImageResource(R.drawable.android_studio_icon);
                mProductImageRemoveButton.setVisibility(View.GONE);
            }
        } else if (!mIsDeletingProductFlag) {
            mMainToast.setText(R.string.message_error_loading_product);
            mMainToast.show();
            finish();
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mProductNameEditText.setText("");
        mProductPriceEditText.setText("");
        mProductQuantityEditText.setText("");
        mCurrentProductImageUri = null;
        mProductImageImageView.setImageResource(R.drawable.android_studio_icon);
    }
}
