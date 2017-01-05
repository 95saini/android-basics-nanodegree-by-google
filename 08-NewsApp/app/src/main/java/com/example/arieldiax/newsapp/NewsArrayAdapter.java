package com.example.arieldiax.newsapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class NewsArrayAdapter extends ArrayAdapter<News> {

    /**
     * @var mNews Instances of the News class.
     */
    private ArrayList<News> mNews;

    /**
     * Creates a new NewsArrayAdapter object.
     *
     * @param context Instance of the Context class.
     * @param news    List of the News objects.
     */
    public NewsArrayAdapter(Context context, ArrayList<News> news) {
        super(context, 0, news);
        mNews = news;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        News news = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_news, parent, false);
        }
        TextView newsWebTitleTextView = (TextView) convertView.findViewById(R.id.news_web_title_text_view);
        newsWebTitleTextView.setText(news.getNewsWebTitle());
        TextView newsSectionNameTextView = (TextView) convertView.findViewById(R.id.news_section_name_text_view);
        newsSectionNameTextView.setText(news.getNewsSectionName());
        TextView newsTypeTextView = (TextView) convertView.findViewById(R.id.news_type_text_view);
        newsTypeTextView.setText(news.getFormattedType());
        TextView newsWebPublicationDateTextView = (TextView) convertView.findViewById(R.id.news_web_publication_date_text_view);
        newsWebPublicationDateTextView.setText(news.getFormattedWebPublicationDate());
        return convertView;
    }

    /**
     * Gets the instances of the News class.
     *
     * @return The instances of the News class.
     */
    public ArrayList<News> getNews() {
        return mNews;
    }
}
