package com.example.android.newsapp;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Rushi Patel on 8/4/2016.
 */
public class NewsAdapter extends ArrayAdapter<News>
{
    static class ViewHolder
    {
        TextView articleSection;
        TextView articleTitle;
        TextView articleDetail;
    }

    //Create a new ViewHolder object
    private ViewHolder holder = new ViewHolder();

    public NewsAdapter(Activity context, List<News> news)
    {
        super(context, 0, news);
    }

    public View getView(int position, View convertView, ViewGroup parent)
    {
        if (convertView == null)
        {
            convertView = LayoutInflater.from(getContext()).inflate
                    (R.layout.list_item, parent, false);

            holder.articleSection = (TextView) convertView.findViewById(R.id.article_section);
            holder.articleTitle = (TextView) convertView.findViewById(R.id.article_title);
            holder.articleDetail = (TextView) convertView.findViewById(R.id.article_details);
        }

        News currentNews = getItem(position);

        holder.articleSection.setText(currentNews.getSection());

        holder.articleTitle.setText(currentNews.getTitle());

        holder.articleDetail.setText((currentNews.getDetail()));

        return convertView;
    }
}
