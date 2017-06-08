package com.example.android.newsapp;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dawid on 2017-05-15.
 */

public class ArticleActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<List<Article>>,
        SharedPreferences.OnSharedPreferenceChangeListener {

    private static final String GUARDIAN_REQUEST_URL =
            "http://content.guardianapis.com/search";

    private static final String guardianApiKey = "59d38f40-9517-4e3c-ae24-590ada0d1e08";

    private static final int ARTICLE_LOADER_ID = 1;

    private TextView mEmptyStateTextView;

    private RecyclerView mRecyclerView;
    private ArticleRecycleAdapter mRecyclerAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.article_activity);

        // Find a reference to the {@link ListView} in the layout
        mEmptyStateTextView = (TextView) findViewById(R.id.empty_view);

        mRecyclerView = (RecyclerView) findViewById(R.id.list_view);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // Create a new adapter that takes an empty list of articles as input
        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface

        // Obtain a reference to the SharedPreferences file for this app
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        // And register to be notified of preference changes
        // So we know when the user has adjusted the query settings
        prefs.registerOnSharedPreferenceChangeListener(this);

        // Set an item click listener on the ListView, which sends an intent to a web browser
        // to open a website with more information about the selected article.

        // Get a reference to the ConnectivityManager to check state of network connectivity
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);

        // Get details on the currently active default data network
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        // If there is a network connection, fetch data
        if (networkInfo != null && networkInfo.isConnected()) {
            mEmptyStateTextView.setVisibility(View.GONE);

            // Get a reference to the LoaderManager, in order to interact with loaders.
            mRecyclerAdapter = new ArticleRecycleAdapter(ArticleActivity.this, new ArrayList<Article>());
            mRecyclerView.setAdapter(mRecyclerAdapter);
            LoaderManager loaderManager = getLoaderManager();
            loaderManager.initLoader(1, null, ArticleActivity.this);
        } else {
            // Otherwise, display error
            // First, hide loading indicator so error message will be visible
            View loadingIndicator = findViewById(R.id.loading_indicator);
            loadingIndicator.setVisibility(View.GONE);

            // Update empty state with no connection error message
            mEmptyStateTextView.setText(R.string.no_internet_connection);
        }
    }

    //method to start webView - for ArticleRecycleAdapter
    public void startWebView(String url) {
        Uri articleUri = Uri.parse(url);
        Intent websiteIntent = new Intent(Intent.ACTION_VIEW, articleUri);
        websiteIntent.setData(Uri.parse(url));
        startActivity(websiteIntent);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences prefs, String key) {
        if (key.equals(getString(R.string.settings_search_query_key)) ||
                key.equals(getString(R.string.settings_list_size_key))) {
            // Clear the RecyclerView as a new query will be kicked off
            mRecyclerAdapter = new ArticleRecycleAdapter(ArticleActivity.this, new ArrayList<Article>());

            // Hide the empty state text view as the loading indicator will be displayed
            mEmptyStateTextView.setVisibility(View.GONE);

            // Show the loading indicator while new data is being fetched
            View loadingIndicator = findViewById(R.id.loading_indicator);
            loadingIndicator.setVisibility(View.VISIBLE);

            // Restart the loader to requery the GUARDIAN_REQUEST_URL as the query settings have been updated
            getLoaderManager().restartLoader(ARTICLE_LOADER_ID, null, this);
        }
    }

    @Override
    public Loader<List<Article>> onCreateLoader(int i, Bundle bundle) {

        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        String listSize = sharedPrefs.getString(
                getString(R.string.settings_list_size_key),
                getString(R.string.settings_list_size_default));

        String searchQuery = sharedPrefs.getString(
                getString(R.string.settings_search_query_key),
                getString(R.string.settings_search_query)
        );

        Uri baseUri = Uri.parse(GUARDIAN_REQUEST_URL);
        Uri.Builder uriBuilder = baseUri.buildUpon();

        uriBuilder.appendQueryParameter("q", searchQuery);
        uriBuilder.appendQueryParameter("page-size", listSize);
        uriBuilder.appendQueryParameter("api-key", guardianApiKey);

        return new ArticleLoader(this, uriBuilder.toString());
    }

    @Override
    public void onLoadFinished(Loader<List<Article>> loader, List<Article> articles) {
        // Hide loading indicator because the data has been loaded
        View loadingIndicator = findViewById(R.id.loading_indicator);
        loadingIndicator.setVisibility(View.GONE);

        // Set empty state text to display "No articles found."
        mEmptyStateTextView.setText(R.string.no_articles);

        mRecyclerAdapter = new ArticleRecycleAdapter(ArticleActivity.this, new ArrayList<Article>());

        if(articles != null && !articles.isEmpty()){

            mRecyclerAdapter = new ArticleRecycleAdapter(ArticleActivity.this, articles);
            mRecyclerView.setAdapter(mRecyclerAdapter);
        }

        // Clear the adapter of previous article data
    }

    @Override
    public void onLoaderReset(Loader<List<Article>> loader) {
        // Loader reset, so we can clear out our existing data.
        mRecyclerAdapter = new ArticleRecycleAdapter(ArticleActivity.this, new ArrayList<Article>());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent settingsIntent = new Intent(this, SettingsActivity.class);
            startActivity(settingsIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}