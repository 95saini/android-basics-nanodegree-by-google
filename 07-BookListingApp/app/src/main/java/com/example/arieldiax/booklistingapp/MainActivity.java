package com.example.arieldiax.booklistingapp;

import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Book>> {

    /**
     * @var mSearchBoxEditText Edit text field for search box.
     */
    private EditText mSearchBoxEditText;

    /**
     * @var mSearchBoxButton Button field for search box.
     */
    private Button mSearchBoxButton;

    /**
     * @var mListBookProgressBar Progress bar field for list book.
     */
    private ProgressBar mListBookProgressBar;

    /**
     * @var mListBookListView List view field for list book.
     */
    private ListView mListBookListView;

    /**
     * @var mListBookEmptyTextView Text view field for list book empty.
     */
    private TextView mListBookEmptyTextView;

    /**
     * @var mMainToast Toast message for interaction buttons.
     */
    private Toast mMainToast;

    /**
     * @var mBookArrayAdapter Array adapter of the book.
     */
    private BookArrayAdapter mBookArrayAdapter;

    /**
     * @var mBookLoaderManager Loader manager of the book.
     */
    private LoaderManager mBookLoaderManager;

    /**
     * @var mSearchQuery Query of the search.
     */
    private String mSearchQuery;

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
        mSearchBoxEditText = (EditText) findViewById(R.id.search_box_edit_text);
        mSearchBoxButton = (Button) findViewById(R.id.search_box_button);
        mListBookProgressBar = (ProgressBar) findViewById(R.id.list_book_progress_bar);
        mListBookListView = (ListView) findViewById(R.id.list_book_list_view);
        mListBookEmptyTextView = (TextView) findViewById(R.id.list_book_empty_text_view);
    }

    /**
     * Initializes the back end logic bindings.
     */
    private void init() {
        mMainToast = Toast.makeText(getApplicationContext(), "", Toast.LENGTH_SHORT);
        mBookArrayAdapter = new BookArrayAdapter(this, new ArrayList<Book>());
        mListBookListView.setAdapter(mBookArrayAdapter);
        mListBookListView.setEmptyView(mListBookEmptyTextView);
        mBookLoaderManager = getLoaderManager();
        mBookLoaderManager.initLoader(0, null, this);
        mSearchQuery = "";
    }

    /**
     * Initializes the event listener view bindings.
     */
    private void initListeners() {
        mSearchBoxButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                mSearchQuery = Uri.encode(ViewUtils.getEditTextValue(mSearchBoxEditText).trim().toLowerCase());
                ViewUtils.hideKeyboard(MainActivity.this);
                if (TextUtils.isEmpty(mSearchQuery)) {
                    mMainToast.setText(R.string.message_please_enter_title);
                    mMainToast.show();
                    return;
                }
                if (!ConnectionUtils.hasInternetConnection(MainActivity.this)) {
                    mMainToast.setText(R.string.message_no_internet_connection);
                    mMainToast.show();
                    return;
                }
                mBookLoaderManager.restartLoader(0, null, MainActivity.this);
            }
        });
        mListBookListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Book book = (Book) parent.getAdapter().getItem(position);
                Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(book.getBookInfoLink()));
                if (webIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(webIntent);
                } else {
                    mMainToast.setText(R.string.message_no_applications_found);
                    mMainToast.show();
                }
            }
        });
    }

    @Override
    public Loader<List<Book>> onCreateLoader(int id, Bundle bundle) {
        mSearchBoxButton.setEnabled(false);
        mListBookProgressBar.setVisibility(View.VISIBLE);
        mListBookEmptyTextView.setText("");
        mBookArrayAdapter.clear();
        return new BookAsyncTaskLoader(this, mSearchQuery);
    }

    @Override
    public void onLoadFinished(Loader<List<Book>> loader, List<Book> books) {
        mSearchBoxButton.setEnabled(true);
        mListBookProgressBar.setVisibility(View.GONE);
        if (TextUtils.isEmpty(mSearchQuery)) {
            mListBookEmptyTextView.setText(R.string.message_please_enter_title);
        } else {
            mListBookEmptyTextView.setText(R.string.message_no_results_found);
        }
        if (!books.isEmpty()) {
            mBookArrayAdapter.clear();
            mBookArrayAdapter.addAll(books);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Book>> loader) {
        mBookArrayAdapter.clear();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList("books", mBookArrayAdapter.getBooks());
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (mBookArrayAdapter.getBooks().isEmpty()) {
            ArrayList<Book> books = savedInstanceState.getParcelableArrayList("books");
            mBookArrayAdapter.addAll(books);
        }
    }
}
