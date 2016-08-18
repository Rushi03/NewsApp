package com.example.android.newsapp;

import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderCallbacks<List<News>> {

    private static final String GUARDIAN_URL_REQUEST =
            "http://content.guardianapis.com/search?order-by=newest&q=football&api-key=test";

    private NewsAdapter adapter;

    private static final int NEWS_LOADER_ID = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView mEmptyView = (TextView) findViewById(R.id.empty_view);

        ListView newsView = (ListView) findViewById(R.id.list);
        adapter = new NewsAdapter(this, new ArrayList<News>());
        newsView.setAdapter(adapter);

        newsView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                News currentNews = adapter.getItem(position);

                Uri newsUri = Uri.parse(currentNews.getUrl());

                Intent websiteIntent = new Intent(Intent.ACTION_VIEW, newsUri);

                startActivity(websiteIntent);

            }
        });

        //Create object for ConnectivityManager
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        //Set up networkInfo object
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();

        //Check the network before starting the LoaderManager
        if (networkInfo != null && networkInfo.isConnected()) {
            LoaderManager loaderManager = getLoaderManager();
            loaderManager.initLoader(NEWS_LOADER_ID, null, this);
        } else {
            mEmptyView.setText(R.string.no_connection);
        }
    }

    @Override
    public Loader<List<News>> onCreateLoader(int i, Bundle bundle) {
        return new NewsLoader(this, GUARDIAN_URL_REQUEST);
    }

    @Override
    public void onLoadFinished(Loader<List<News>> loader, List<News> news) {
        adapter.clear();
        if (news != null && !news.isEmpty()) {
            adapter.addAll(news);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onLoaderReset(Loader<List<News>> loader) {
        adapter.clear();
    }
}
