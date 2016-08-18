package com.example.android.newsapp;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

/**
 * Created by Rushi Patel on 8/4/2016.
 */
public class NewsLoader extends AsyncTaskLoader<List<News>>
{
    private String mUrl;

    public NewsLoader(Context context, String url)
    {
        super(context);
        mUrl = url;
    }

    @Override
    public void onStartLoading()
    {
        forceLoad();
    }

    @Override
    public List<News> loadInBackground()
    {
        if (mUrl == null)
        {
            return null;
        }

        List<News> news = Utils.fetchNewsData(mUrl);
        return news;
    }
}
