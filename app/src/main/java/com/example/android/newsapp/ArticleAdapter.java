//package com.example.android.newsapp;
//
//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ArrayAdapter;
//import android.widget.TextView;
//
//import java.util.List;
//
///**
// * Created by Dawid on 2017-05-15.
// */
//
//public class ArticleAdapter extends ArrayAdapter<Article> {
//
//    public ArticleAdapter(Context context, List<Article> articles) {
//        super(context, 0, articles);
//    }
//
//
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//        // Check if there is an existing list item view (called convertView) that we can reuse,
//        // otherwise, if convertView is null, then inflate a new list item layout.
//        View listItemView = convertView;
//        if (listItemView == null) {
//            listItemView = LayoutInflater.from(getContext()).inflate(
//                    R.layout.article_list_item, parent, false);
//        }
//
//        // Find the article at the given position in the list of articles
//        Article currentArticle = getItem(position);
//
//        TextView sectionView = (TextView) listItemView.findViewById(R.id.section);
//        String formattedMagnitude = currentArticle.getArticleSection();
//        sectionView.setText(formattedMagnitude);
//
//        TextView titleView = (TextView) listItemView.findViewById(R.id.title);
//        String articleTitle = currentArticle.getArticleName();
//        titleView.setText(articleTitle);
//
//        TextView dateView = (TextView) listItemView.findViewById(R.id.publish_date);
//        String articleDate = currentArticle.getArticleDate();
//        dateView.setText(articleDate);
//
//        return listItemView;
//    }
//
//}