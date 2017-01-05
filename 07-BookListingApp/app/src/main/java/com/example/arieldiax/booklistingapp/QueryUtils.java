package com.example.arieldiax.booklistingapp;

import android.content.Context;
import android.text.TextUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public final class QueryUtils {

    /**
     * Creates a new QueryUtils object (no, it won't).
     */
    private QueryUtils() {
        // Required empty private constructor (to prevent instantiation).
    }

    /**
     * Fetches the instances of the Book class.
     *
     * @param context    Instance of the Context class.
     * @param requestUrl URL of the request.
     * @return The instances of the Book class.
     */
    public static List<Book> fetchBooks(Context context, String requestUrl) {
        URL url = getUrl(requestUrl);
        String jsonString = getHttpResponse(url);
        return getBooks(context, jsonString);
    }

    /**
     * Gets the instance of the URL class.
     *
     * @param urlString String of the URL.
     * @return The instance of the URL class.
     */
    private static URL getUrl(String urlString) {
        if (TextUtils.isEmpty(urlString)) {
            return null;
        }
        URL url = null;
        try {
            url = new URL(urlString);
        } catch (MalformedURLException exception) {
            exception.printStackTrace();
        }
        return url;
    }

    /**
     * Gets the HTTP response of the given URL.
     *
     * @param url Instance of the URL class.
     * @return The HTTP response of the given URL.
     */
    private static String getHttpResponse(URL url) {
        if (url == null) {
            return null;
        }
        String httpResponse = null;
        HttpURLConnection httpUrlConnection = null;
        InputStream inputStream = null;
        try {
            httpUrlConnection = (HttpURLConnection) url.openConnection();
            httpUrlConnection.setRequestMethod("GET");
            httpUrlConnection.setConnectTimeout(15000);
            httpUrlConnection.setReadTimeout(10000);
            httpUrlConnection.connect();
            if (httpUrlConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                inputStream = httpUrlConnection.getInputStream();
                httpResponse = getFormattedInputStream(inputStream);
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        } finally {
            if (httpUrlConnection != null) {
                httpUrlConnection.disconnect();
            }
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException exception) {
                    exception.printStackTrace();
                }
            }
        }
        return httpResponse;
    }

    /**
     * Gets the formatted version of the input stream.
     *
     * @param inputStream Instance of the InputStream class.
     * @return The formatted version of the input stream.
     */
    private static String getFormattedInputStream(InputStream inputStream) {
        if (inputStream == null) {
            return null;
        }
        StringBuilder stringBuilder = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line = null;
            try {
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line);
                }
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
        return stringBuilder.toString();
    }

    /**
     * Gets the instances of the Book class.
     *
     * @param context    Instance of the Context class.
     * @param jsonString String of the JSON.
     * @return The instances of the Book class.
     */
    private static List<Book> getBooks(Context context, String jsonString) {
        if (TextUtils.isEmpty(jsonString)) {
            return null;
        }
        List<Book> books = new ArrayList<Book>();
        try {
            JSONObject rootObject = new JSONObject(jsonString);
            JSONArray items = rootObject.getJSONArray("items");
            for (int i = 0; i < items.length(); i++) {
                JSONObject item = items.getJSONObject(i);
                JSONObject volumeInfo = item.getJSONObject("volumeInfo");
                String bookTitle = (!volumeInfo.isNull("title")) ? volumeInfo.getString("title") : context.getString(R.string.book_default_title);
                String bookDescription = (!volumeInfo.isNull("description")) ? volumeInfo.getString("description") : context.getString(R.string.book_default_description);
                JSONArray authors = volumeInfo.optJSONArray("authors");
                String[] bookAuthors;
                if (authors != null && authors.length() > 0) {
                    bookAuthors = new String[authors.length()];
                    for (int j = 0; j < authors.length(); j++) {
                        bookAuthors[j] = authors.getString(j);
                    }
                } else {
                    bookAuthors = new String[1];
                    bookAuthors[0] = context.getString(R.string.book_default_authors);
                }
                String bookPublishedDate = (!volumeInfo.isNull("publishedDate")) ? volumeInfo.getString("publishedDate") : context.getString(R.string.book_default_published_date);
                String bookInfoLink = (!volumeInfo.isNull("infoLink")) ? volumeInfo.getString("infoLink") : context.getString(R.string.book_default_info_link);
                books.add(new Book(bookTitle, bookDescription, bookAuthors, bookPublishedDate, bookInfoLink));
            }
        } catch (JSONException exception) {
            exception.printStackTrace();
        }
        return books;
    }
}
