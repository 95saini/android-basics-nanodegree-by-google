package com.example.arieldiax.newsapp;

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
     * Fetches the instances of the News class.
     *
     * @param context    Instance of the Context class.
     * @param requestUrl URL of the request.
     * @return The instances of the News class.
     */
    public static List<News> fetchNews(Context context, String requestUrl) {
        URL url = getUrl(requestUrl);
        String jsonString = getHttpResponse(url);
        return getNews(context, jsonString);
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
     * Gets the instances of the News class.
     *
     * @param context    Instance of the Context class.
     * @param jsonString String of the JSON.
     * @return The instances of the News class.
     */
    private static List<News> getNews(Context context, String jsonString) {
        if (TextUtils.isEmpty(jsonString)) {
            return null;
        }
        List<News> news = new ArrayList<News>();
        try {
            JSONObject rootObject = new JSONObject(jsonString);
            JSONArray results = rootObject.getJSONObject("response").getJSONArray("results");
            for (int i = 0; i < results.length(); i++) {
                JSONObject result = results.getJSONObject(i);
                String newsWebTitle = (!result.isNull("webTitle")) ? result.getString("webTitle") : context.getString(R.string.news_default_web_title);
                String newsSectionName = (!result.isNull("sectionName")) ? result.getString("sectionName") : context.getString(R.string.news_default_section_name);
                String newsType = (!result.isNull("type")) ? result.getString("type") : context.getString(R.string.news_default_type);
                String newsWebPublicationDate = (!result.isNull("webPublicationDate")) ? result.getString("webPublicationDate") : context.getString(R.string.news_default_web_publication_date);
                String newsWebUrl = (!result.isNull("webUrl")) ? result.getString("webUrl") : context.getString(R.string.news_default_web_url);
                news.add(new News(newsWebTitle, newsSectionName, newsType, newsWebPublicationDate, newsWebUrl));
            }
        } catch (JSONException exception) {
            exception.printStackTrace();
        }
        return news;
    }
}
