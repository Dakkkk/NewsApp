package com.example.android.newsapp;

/**
 * Created by Dawid on 2017-05-15.
 */

public class Article {

    private String articleSection;
    private String articleTitle;
    private String articleUrl;
    private String articleDate;

    public Article(String articleTitle, String articleSection, String articleUrl, String articleDate) {
        this.articleTitle = articleTitle;
        this.articleSection = articleSection;
        this.articleUrl = articleUrl;
        this.articleDate = articleDate;
    }

    public String getArticleName() {
        return articleTitle;
    }

    public void setArticleName(String articleName) {
        this.articleTitle = articleName;
    }

    public String getArticleSection() {
        return articleSection;
    }

    public void setArticleSection(String articleSection) {
        this.articleSection = articleSection;
    }

    public String getArticleUrl() {
        return articleUrl;
    }

    public void setArticleUrl(String articleUrl) {
        this.articleUrl = articleUrl;
    }

    public String getArticleDate() {
        return articleDate;
    }

    public void setArticleDate(String articleDate) {
        this.articleDate = articleDate;
    }

    public String getArticleTitle() {
        return articleTitle;
    }

    public void setArticleTitle(String articleTitle) {
        this.articleTitle = articleTitle;
    }
}
