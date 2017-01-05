package com.example.arieldiax.newsapp;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

public class NewsAsyncTaskLoader extends AsyncTaskLoader {

    /**
     * @const GUARDIAN_CONTENT_API_REQUEST_URL Request URL of the Guardian Content API.
     */
    private static final String GUARDIAN_CONTENT_API_REQUEST_URL = "http://content.guardianapis.com/search";

    /**
     * @const API_KEY Key of the API.
     */
    private static final String API_KEY = "test";

    /**
     * @var mSearchQuery Query of the search.
     */
    private String mSearchQuery;

    /**
     * Creates a new NewsAsyncTaskLoader object.
     *
     * @param context     Instance of the Context class.
     * @param searchQuery Query of the search.
     */
    public NewsAsyncTaskLoader(Context context, String searchQuery) {
        super(context);
        mSearchQuery = searchQuery;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<News> loadInBackground() {
        if (TextUtils.isEmpty(mSearchQuery) || !ConnectionUtils.hasInternetConnection(getContext())) {
            return new ArrayList<News>();
        }
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        String pageSize = sharedPreferences.getString(getContext().getString(R.string.setting_key_page_size), getContext().getString(R.string.setting_default_page_size));
        String orderBy = sharedPreferences.getString(getContext().getString(R.string.setting_key_order_by), getContext().getString(R.string.setting_default_order_by));
        Uri requestUri = Uri.parse(GUARDIAN_CONTENT_API_REQUEST_URL);
        Uri.Builder requestUriBuilder = requestUri.buildUpon();
        requestUriBuilder.appendQueryParameter("q", mSearchQuery);
        requestUriBuilder.appendQueryParameter("page-size", pageSize);
        requestUriBuilder.appendQueryParameter("order-by", orderBy);
        requestUriBuilder.appendQueryParameter("api-key", API_KEY);
        String requestUrl = requestUriBuilder.toString();
        return QueryUtils.fetchNews(getContext(), requestUrl);
    }
}
