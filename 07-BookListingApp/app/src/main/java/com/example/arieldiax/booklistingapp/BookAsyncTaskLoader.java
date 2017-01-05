package com.example.arieldiax.booklistingapp;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

public class BookAsyncTaskLoader extends AsyncTaskLoader {

    /**
     * @const GOOGLE_BOOKS_API_REQUEST_URL Request URL of the Google Books API.
     */
    private static final String GOOGLE_BOOKS_API_REQUEST_URL = "https://www.googleapis.com/books/v1/volumes";

    /**
     * @const BOOKS_NUMBER Number of the books.
     */
    private static final int BOOKS_NUMBER = 10;

    /**
     * @var mSearchQuery Query of the search.
     */
    private String mSearchQuery;

    /**
     * Creates a new BookAsyncTaskLoader object.
     *
     * @param context     Instance of the Context class.
     * @param searchQuery Query of the search.
     */
    public BookAsyncTaskLoader(Context context, String searchQuery) {
        super(context);
        mSearchQuery = searchQuery;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<Book> loadInBackground() {
        if (TextUtils.isEmpty(mSearchQuery) || !ConnectionUtils.hasInternetConnection(getContext())) {
            return new ArrayList<Book>();
        }
        Uri requestUri = Uri.parse(GOOGLE_BOOKS_API_REQUEST_URL);
        Uri.Builder requestUriBuilder = requestUri.buildUpon();
        requestUriBuilder.appendQueryParameter("q", mSearchQuery);
        requestUriBuilder.appendQueryParameter("maxResults", String.valueOf(BOOKS_NUMBER));
        String requestUrl = requestUriBuilder.toString();
        return QueryUtils.fetchBooks(getContext(), requestUrl);
    }
}
