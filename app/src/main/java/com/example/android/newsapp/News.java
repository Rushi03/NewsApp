package com.example.android.newsapp;

/**
 * Created by Rushi Patel on 8/4/2016.
 */
public class News
{
    //Author of news article
    private String mSection;

    //Title of news article
    private String mTitle;

    //Details from the article
    private String mDetail;

    //URL to the article
    private String mUrl;

    public News(String section, String title, String detail, String url)
    {
        mSection = section;
        mTitle = title;
        mDetail = detail;
        mUrl = url;
    }

    public String getSection()
    {
        return mSection;
    }

    public String getTitle()
    {
        return mTitle;
    }

    public String getDetail()
    {
        return mDetail;
    }

    public String getUrl()
    {
        return mUrl;
    }
}
