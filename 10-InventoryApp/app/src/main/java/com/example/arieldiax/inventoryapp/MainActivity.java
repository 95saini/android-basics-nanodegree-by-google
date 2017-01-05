package com.example.arieldiax.inventoryapp;

import android.app.LoaderManager;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.example.arieldiax.inventoryapp.data.InventoryContract.ProductEntry;

import java.sql.Timestamp;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    /**
     * @var mListProductListView List view field for list product.
     */
    private ListView mListProductListView;

    /**
     * @var mListProductEmptyLinearLayout Linear layout field for list product empty.
     */
    private LinearLayout mListProductEmptyLinearLayout;

    /**
     * @var mAddProductFloatingActionButton Floating action button field for add product.
     */
    private FloatingActionButton mAddProductFloatingActionButton;

    /**
     * @var mMenuItemsVisibilityToggle Toggle for menu items visibility.
     */
    private boolean mMenuItemsVisibilityToggle;

    /**
     * @var mMainToast Toast message for interaction buttons.
     */
    private Toast mMainToast;

    /**
     * @var mProductCursorAdapter Cursor adapter of the product.
     */
    private ProductCursorAdapter mProductCursorAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUi();
        init();
        initListeners();
    }

    /**
     * Initializes the user interface view bindings.
     */
    private void initUi() {
        mListProductListView = (ListView) findViewById(R.id.list_product_list_view);
        mListProductEmptyLinearLayout = (LinearLayout) findViewById(R.id.list_product_empty_linear_layout);
        mAddProductFloatingActionButton = (FloatingActionButton) findViewById(R.id.add_product_floating_action_button);
    }

    /**
     * Initializes the back end logic bindings.
     */
    private void init() {
        mMenuItemsVisibilityToggle = false;
        mMainToast = Toast.makeText(getApplicationContext(), "", Toast.LENGTH_SHORT);
        mProductCursorAdapter = new ProductCursorAdapter(this, null);
        mListProductListView.setAdapter(mProductCursorAdapter);
        mListProductListView.setEmptyView(mListProductEmptyLinearLayout);
        getLoaderManager().initLoader(0, null, this);
    }

    /**
     * Initializes the event listener view bindings.
     */
    private void initListeners() {
        mAddProductFloatingActionButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent editorIntent = new Intent(MainActivity.this, EditorActivity.class);
                startActivity(editorIntent);
            }
        });
        mListProductListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent editorIntent = new Intent(MainActivity.this, EditorActivity.class);
                editorIntent.setData(ContentUris.withAppendedId(ProductEntry.CONTENT_URI, id));
                startActivity(editorIntent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        MenuItem insertDummyProductsMenuItem = menu.findItem(R.id.insert_dummy_products_menu_item);
        insertDummyProductsMenuItem.setVisible(!mMenuItemsVisibilityToggle);
        MenuItem deleteAllTheProductsMenuItem = menu.findItem(R.id.delete_all_the_products_menu_item);
        deleteAllTheProductsMenuItem.setVisible(mMenuItemsVisibilityToggle);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.insert_dummy_products_menu_item:
                insertDummyProducts();
                return true;
            case R.id.delete_all_the_products_menu_item:
                showDeleteAllTheProductsAlertDialog();
                return true;
            case R.id.settings_menu_item:
                Intent settingsIntent = new Intent(this, SettingsActivity.class);
                startActivity(settingsIntent);
                return true;
        }
        return super.onOptionsItemSelected(menuItem);
    }

    /**
     * Inserts the rows into the products table.
     */
    private void insertDummyProducts() {
        Random random = new Random();
        long currentTimestamp = (new Timestamp(System.currentTimeMillis())).getTime();
        ContentValues[] products = new ContentValues[10];
        for (int i = 0; i < products.length; i++) {
            ContentValues product = new ContentValues();
            product.put(ProductEntry.COLUMN_NAME_NAME, getString(R.string.product_default_name));
            product.put(ProductEntry.COLUMN_NAME_PRICE, 1000 * random.nextDouble());
            product.put(ProductEntry.COLUMN_NAME_QUANTITY, random.nextInt(100));
            product.put(ProductEntry.COLUMN_NAME_IMAGE, "");
            product.put(ProductEntry.COLUMN_NAME_CREATED_AT, currentTimestamp + i);
            products[i] = product;
        }
        int rowsInserted = getContentResolver().bulkInsert(ProductEntry.CONTENT_URI, products);
        if (rowsInserted > 0) {
            mMainToast.setText(R.string.message_all_products_inserted);
        } else {
            mMainToast.setText(R.string.message_error_inserting_products);
        }
        mMainToast.show();
    }

    /**
     * Shows an alert dialog, before it deletes all the products.
     */
    private void showDeleteAllTheProductsAlertDialog() {
        DialogInterface.OnClickListener positiveButtonOnClickListener = new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                deleteAllTheProducts();
            }
        };
        ViewUtils.showAlertDialog(this, R.string.title_delete_all_the_products, positiveButtonOnClickListener);
    }

    /**
     * Deletes the rows from the products table.
     */
    private void deleteAllTheProducts() {
        int rowsDeleted = getContentResolver().delete(ProductEntry.CONTENT_URI, null, null);
        if (rowsDeleted > 0) {
            mMainToast.setText(R.string.message_all_products_deleted);
        } else {
            mMainToast.setText(R.string.message_error_deleting_products);
        }
        mMainToast.show();
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle bundle) {
        String[] projection = new String[]{
                ProductEntry._ID,
                ProductEntry.COLUMN_NAME_NAME,
                ProductEntry.COLUMN_NAME_PRICE,
                ProductEntry.COLUMN_NAME_QUANTITY,
                ProductEntry.COLUMN_NAME_IMAGE,
                ProductEntry.COLUMN_NAME_CREATED_AT,
                ProductEntry.COLUMN_NAME_UPDATED_AT,
        };
        return new CursorLoader(this, ProductEntry.CONTENT_URI, projection, null, null, ProductEntry.COLUMN_NAME_CREATED_AT + " DESC");
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor products) {
        mMenuItemsVisibilityToggle = (products.getCount() > 0);
        invalidateOptionsMenu();
        mProductCursorAdapter.swapCursor(products);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mProductCursorAdapter.swapCursor(null);
    }
}
