package com.example.arieldiax.booklistingapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class BookArrayAdapter extends ArrayAdapter<Book> {

    /**
     * @var mBooks Instances of the Book class.
     */
    private ArrayList<Book> mBooks;

    /**
     * Creates a new BookArrayAdapter object.
     *
     * @param context Instance of the Context class.
     * @param books   List of the Book objects.
     */
    public BookArrayAdapter(Context context, ArrayList<Book> books) {
        super(context, 0, books);
        mBooks = books;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Book book = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_book, parent, false);
        }
        TextView bookTitleTextView = (TextView) convertView.findViewById(R.id.book_title_text_view);
        bookTitleTextView.setText(book.getBookTitle());
        TextView bookDescriptionTextView = (TextView) convertView.findViewById(R.id.book_description_text_view);
        bookDescriptionTextView.setText(book.getBookDescription());
        TextView bookAuthorsTextView = (TextView) convertView.findViewById(R.id.book_authors_text_view);
        bookAuthorsTextView.setText(book.getFormattedAuthors());
        TextView bookPublishedDateTextView = (TextView) convertView.findViewById(R.id.book_published_date_text_view);
        bookPublishedDateTextView.setText(book.getFormattedPublishedDate());
        return convertView;
    }

    /**
     * Gets the instances of the Book class.
     *
     * @return The instances of the Book class.
     */
    public ArrayList<Book> getBooks() {
        return mBooks;
    }
}
