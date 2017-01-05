package com.example.arieldiax.newsapp;

import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
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

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<News>> {

    /**
     * @var mSearchBoxEditText Edit text field for search box.
     */
    private EditText mSearchBoxEditText;

    /**
     * @var mSearchBoxButton Button field for search box.
     */
    private Button mSearchBoxButton;

    /**
     * @var mListNewsProgressBar Progress bar field for list news.
     */
    private ProgressBar mListNewsProgressBar;

    /**
     * @var mListNewsListView List view field for list news.
     */
    private ListView mListNewsListView;

    /**
     * @var mListNewsEmptyTextView Text view field for list news empty.
     */
    private TextView mListNewsEmptyTextView;

    /**
     * @var mMainToast Toast message for interaction buttons.
     */
    private Toast mMainToast;

    /**
     * @var mNewsArrayAdapter Array adapter of the news.
     */
    private NewsArrayAdapter mNewsArrayAdapter;

    /**
     * @var mNewsLoaderManager Loader manager of the news.
     */
    private LoaderManager mNewsLoaderManager;

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
        mListNewsProgressBar = (ProgressBar) findViewById(R.id.list_news_progress_bar);
        mListNewsListView = (ListView) findViewById(R.id.list_news_list_view);
        mListNewsEmptyTextView = (TextView) findViewById(R.id.list_news_empty_text_view);
    }

    /**
     * Initializes the back end logic bindings.
     */
    private void init() {
        mMainToast = Toast.makeText(getApplicationContext(), "", Toast.LENGTH_SHORT);
        mNewsArrayAdapter = new NewsArrayAdapter(this, new ArrayList<News>());
        mListNewsListView.setAdapter(mNewsArrayAdapter);
        mListNewsListView.setEmptyView(mListNewsEmptyTextView);
        mNewsLoaderManager = getLoaderManager();
        mNewsLoaderManager.initLoader(0, null, this);
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
                    mMainToast.setText(R.string.message_please_enter_topic);
                    mMainToast.show();
                    return;
                }
                if (!ConnectionUtils.hasInternetConnection(MainActivity.this)) {
                    mMainToast.setText(R.string.message_no_internet_connection);
                    mMainToast.show();
                    return;
                }
                mNewsLoaderManager.restartLoader(0, null, MainActivity.this);
            }
        });
        mListNewsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                News news = (News) parent.getAdapter().getItem(position);
                Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(news.getNewsWebUrl()));
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        int menuItemId = menuItem.getItemId();
        if (menuItemId == R.id.settings_menu_item) {
            Intent settingsIntent = new Intent(this, SettingsActivity.class);
            startActivity(settingsIntent);
            return true;
        }
        return super.onOptionsItemSelected(menuItem);
    }

    @Override
    public Loader<List<News>> onCreateLoader(int id, Bundle bundle) {
        mSearchBoxButton.setEnabled(false);
        mListNewsProgressBar.setVisibility(View.VISIBLE);
        mListNewsEmptyTextView.setText("");
        mNewsArrayAdapter.clear();
        return new NewsAsyncTaskLoader(this, mSearchQuery);
    }

    @Override
    public void onLoadFinished(Loader<List<News>> loader, List<News> news) {
        mSearchBoxButton.setEnabled(true);
        mListNewsProgressBar.setVisibility(View.GONE);
        if (TextUtils.isEmpty(mSearchQuery)) {
            mListNewsEmptyTextView.setText(R.string.message_please_enter_topic);
        } else {
            mListNewsEmptyTextView.setText(R.string.message_no_results_found);
        }
        if (!news.isEmpty()) {
            mNewsArrayAdapter.clear();
            mNewsArrayAdapter.addAll(news);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<News>> loader) {
        mNewsArrayAdapter.clear();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList("news", mNewsArrayAdapter.getNews());
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (mNewsArrayAdapter.getNews().isEmpty()) {
            ArrayList<News> news = savedInstanceState.getParcelableArrayList("news");
            mNewsArrayAdapter.addAll(news);
        }
    }
}
