package com.example.arieldiax.booklistingapp;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

public class Book implements Parcelable {

    /**
     * @var mBookTitle Title of the book.
     */
    private String mBookTitle;

    /**
     * @var mBookDescription Description of the book.
     */
    private String mBookDescription;

    /**
     * @var mBookAuthors Authors of the book.
     */
    private String[] mBookAuthors;

    /**
     * @var mBookPublishedDate Published date of the book.
     */
    private String mBookPublishedDate;

    /**
     * @var mBookInfoLink Info link of the book.
     */
    private String mBookInfoLink;

    /**
     * Creates a new Book object.
     *
     * @param bookTitle         Title of the book.
     * @param bookDescription   Description of the book.
     * @param bookAuthors       Authors of the book.
     * @param bookPublishedDate Published date of the book.
     * @param bookInfoLink      Info link of the book.
     */
    public Book(String bookTitle, String bookDescription, String[] bookAuthors, String bookPublishedDate, String bookInfoLink) {
        mBookTitle = bookTitle;
        mBookDescription = bookDescription;
        mBookAuthors = bookAuthors;
        mBookPublishedDate = bookPublishedDate;
        mBookInfoLink = bookInfoLink;
    }

    /**
     * Creates a new Book object.
     *
     * @param in Instance of the Parcel class.
     */
    protected Book(Parcel in) {
        mBookTitle = in.readString();
        mBookDescription = in.readString();
        mBookAuthors = in.createStringArray();
        mBookPublishedDate = in.readString();
        mBookInfoLink = in.readString();
    }

    /**
     * Gets the title of the book.
     *
     * @return The title of the book.
     */
    public String getBookTitle() {
        return mBookTitle;
    }

    /**
     * Gets the description of the book.
     *
     * @return The description of the book.
     */
    public String getBookDescription() {
        return mBookDescription;
    }

    /**
     * Gets the authors of the book.
     *
     * @return The authors of the book.
     */
    public String[] getBookAuthors() {
        return mBookAuthors;
    }

    /**
     * Gets the formatted version of the authors.
     *
     * @return The formatted version of the authors.
     */
    public String getFormattedAuthors() {
        return TextUtils.join(", ", mBookAuthors);
    }

    /**
     * Gets the published date of the book.
     *
     * @return The published date of the book.
     */
    public String getBookPublishedDate() {
        return mBookPublishedDate;
    }

    /**
     * Gets the formatted version of the published date.
     *
     * @return The formatted version of the published date.
     */
    public String getFormattedPublishedDate() {
        return Utils.formatDate(mBookPublishedDate, "yyyy-MM-dd", "yyyy");
    }

    /**
     * Gets the info link of the book.
     *
     * @return The info link of the book.
     */
    public String getBookInfoLink() {
        return mBookInfoLink;
    }

    /**
     * @const CREATOR Instance of the Creator class.
     */
    public static final Creator<Book> CREATOR = new Creator<Book>() {

        @Override
        public Book createFromParcel(Parcel in) {
            return new Book(in);
        }

        @Override
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mBookTitle);
        dest.writeString(mBookDescription);
        dest.writeStringArray(mBookAuthors);
        dest.writeString(mBookPublishedDate);
        dest.writeString(mBookInfoLink);
    }
}
