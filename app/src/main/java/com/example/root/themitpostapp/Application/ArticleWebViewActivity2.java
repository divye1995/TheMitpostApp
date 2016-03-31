package com.example.root.themitpostapp.Application;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NavUtils;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.root.themitpostapp.Constants.Constant;
import com.example.root.themitpostapp.R;

public class ArticleWebViewActivity2 extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    WebView articleView;
    String URL;
    SwipeRefreshLayout swipeLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_web_view2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent=getIntent();
        URL=intent.getStringExtra(Constant.JSON_OBJECT_URL);

        String category=intent.getStringExtra(Constant.CATEGORY_KEY);
        if(category==null)
        setTitle(Constant.TITLE_MAIN_ACTIVITY);
        else
        setTitle(category);

        articleView=(WebView)findViewById(R.id.article_web_view);
        swipeLayout=(SwipeRefreshLayout)findViewById(R.id.articleWebViewSwipeLayout);
        swipeLayout.setOnRefreshListener(this);
        WebSettings webSettings=articleView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setBuiltInZoomControls(true);
        articleView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                swipeLayout.setRefreshing(true);
                return super.shouldOverrideUrlLoading(view, url);
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                swipeLayout.setRefreshing(true);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                swipeLayout.setRefreshing(false);

            }
        });
        swipeLayout.post(new Runnable() {
            @Override
            public void run() {
                articleView.loadUrl(URL);
                swipeLayout.setRefreshing(false);
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // Check if the key event was the Back button and if there's history
        if ((keyCode == KeyEvent.KEYCODE_BACK) && articleView.canGoBack()) {
            articleView.goBack();
            return true;
        }
        // If it wasn't the Back key or there's no web page history, bubble up to the default
        // system behavior (probably exit the activity)
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onRefresh() {
        articleView.reload();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                if(articleView.canGoBack()){
                    articleView.goBack();
                    // return true;
                }
                else
                    NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
