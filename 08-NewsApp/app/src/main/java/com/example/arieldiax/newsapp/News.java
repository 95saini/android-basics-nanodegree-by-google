package com.example.arieldiax.newsapp;

import android.os.Parcel;
import android.os.Parcelable;

public class News implements Parcelable {

    /**
     * @var mNewsWebTitle Web title of the news.
     */
    private String mNewsWebTitle;

    /**
     * @var mNewsSectionName Section name of the news.
     */
    private String mNewsSectionName;

    /**
     * @var mNewsType Type of the news.
     */
    private String mNewsType;

    /**
     * @var mNewsWebPublicationDate Web publication date of the news.
     */
    private String mNewsWebPublicationDate;

    /**
     * @var mNewsWebUrl Web URL of the news.
     */
    private String mNewsWebUrl;

    /**
     * Creates a new News object.
     *
     * @param newsWebTitle           Web title of the news.
     * @param newsSectionName        Section name of the news.
     * @param newsType               Type of the news.
     * @param newsWebPublicationDate Web publication date of the news.
     * @param newsWebUrl             Web URL of the news.
     */
    public News(String newsWebTitle, String newsSectionName, String newsType, String newsWebPublicationDate, String newsWebUrl) {
        mNewsWebTitle = newsWebTitle;
        mNewsSectionName = newsSectionName;
        mNewsType = newsType;
        mNewsWebPublicationDate = newsWebPublicationDate;
        mNewsWebUrl = newsWebUrl;
    }

    /**
     * Creates a new News object.
     *
     * @param in Instance of the Parcel class.
     */
    protected News(Parcel in) {
        mNewsWebTitle = in.readString();
        mNewsSectionName = in.readString();
        mNewsType = in.readString();
        mNewsWebPublicationDate = in.readString();
        mNewsWebUrl = in.readString();
    }

    /**
     * Gets the web title of the news.
     *
     * @return The web title of the news.
     */
    public String getNewsWebTitle() {
        return mNewsWebTitle;
    }

    /**
     * Gets the section name of the news.
     *
     * @return The section name of the news.
     */
    public String getNewsSectionName() {
        return mNewsSectionName;
    }

    /**
     * Gets the type of the news.
     *
     * @return The type of the news.
     */
    public String getNewsType() {
        return mNewsType;
    }

    /**
     * Gets the formatted version of the type.
     *
     * @return The formatted version of the type.
     */
    public String getFormattedType() {
        return Utils.capitalizeText(mNewsType);
    }

    /**
     * Gets the web publication date of the news.
     *
     * @return The web publication date of the news.
     */
    public String getNewsWebPublicationDate() {
        return mNewsWebPublicationDate;
    }

    /**
     * Gets the formatted version of the web publication date.
     *
     * @return The formatted version of the web publication date.
     */
    public String getFormattedWebPublicationDate() {
        return Utils.formatDate(mNewsWebPublicationDate, "yyyy-MM-dd'T'HH:mm:ss'Z'", "yyyy-MM-dd");
    }

    /**
     * Gets the web URL of the news.
     *
     * @return The web URL of the news.
     */
    public String getNewsWebUrl() {
        return mNewsWebUrl;
    }

    /**
     * @const CREATOR Instance of the Creator class.
     */
    public static final Creator<News> CREATOR = new Creator<News>() {

        @Override
        public News createFromParcel(Parcel in) {
            return new News(in);
        }

        @Override
        public News[] newArray(int size) {
            return new News[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mNewsWebTitle);
        dest.writeString(mNewsSectionName);
        dest.writeString(mNewsType);
        dest.writeString(mNewsWebPublicationDate);
        dest.writeString(mNewsWebUrl);
    }
}
