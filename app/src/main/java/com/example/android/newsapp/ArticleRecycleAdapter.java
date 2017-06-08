package com.example.android.newsapp;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Dawid on 2017-06-08.
 */

public class ArticleRecycleAdapter extends RecyclerView.Adapter<ArticleRecycleAdapter.ViewHolder> {

    List<Article> mArticles;
    ArticleActivity mContext;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        protected TextView section;
        protected TextView title;

        public ViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
            section = (TextView) itemView.findViewById(R.id.section);

        }
    }

    public ArticleRecycleAdapter(ArticleActivity context, List<Article> articles) {
        this.mArticles = articles;
        this.mContext = context;
    }

    @Override
    public ArticleRecycleAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View listItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.article_list_item, parent, false);

        ViewHolder vh = new ViewHolder(listItem);
        return vh;
    }

    @Override
    public void onBindViewHolder(ArticleRecycleAdapter.ViewHolder holder, int position) {
     
    }

    @Override
    public int getItemCount() {
        return mArticles.size();
    }
}
