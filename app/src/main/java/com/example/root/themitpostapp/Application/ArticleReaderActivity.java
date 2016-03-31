package com.example.root.themitpostapp.Application;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Cache;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.root.themitpostapp.Constants.Constant;
import com.example.root.themitpostapp.Model.Article;
import com.example.root.themitpostapp.Parsers.JsonParser;
import com.example.root.themitpostapp.R;
import com.example.root.themitpostapp.Util.Singleton;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class ArticleReaderActivity extends AppCompatActivity {

    //components
    TextView articleTitle,articleSubTitle;
    TextView articleContent;

    ProgressDialog progressDialog;
        //
    RequestQueue requestQueue;
    String URL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_reader);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        URL=Constant.SAMPLE_URL;
        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage(Constant.PROGRESS_PLEASE_WAIT);
        progressDialog.setCancelable(false);

       requestQueue= Singleton.getInstance(this).getRequestQueue();
        Intent intent=getIntent();

        articleTitle=(TextView)findViewById(R.id.article_reader_title);
        articleSubTitle=(TextView)findViewById(R.id.article_reader_date);
        articleContent=(TextView)findViewById(R.id.article_reader_content);

        //Article Bundle from calling Activity
        Bundle bundle=intent.getExtras().getBundle(Constant.ARTICLE_BUNDLE);

        //setViewContent from here

            Singleton.getInstance(this).addToRequestQueue(getJsonRequest());

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

    //testing if volley is already present TODO:Delete this method
    public JsonObjectRequest getJsonRequest(){
        showPDialog();
    JsonObjectRequest request=new JsonObjectRequest(Request.Method.GET,URL, null,
            new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Article article = JsonParser.getArticleFromRequest(response);
                    if (article == null) {
                    }
                    //handle empty articles
                    setViewContent(article);
                  hidePDialog();
                }
            },
            new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e(Constant.TAG_ARTICLE_READER_ACTIVITY,"***Error in Volley Network Request***");
                    //Execute generation from cache data
                    Cache.Entry entry=requestQueue.getCache().get(URL);
                    if(entry!=null){
                        try {
                            String data = new String(entry.data, "UTF-8");
                            JSONObject response=new JSONObject(data);
                            Article article=JsonParser.getArticleFromRequest(response);
                            setViewContent(article);
                            hidePDialog();
                        }
                        catch (UnsupportedEncodingException | JSONException e){
                            Log.e(Constant.TAG_ARTICLE_READER_ACTIVITY,"***Error in Volley Cache Request***");
                            e.printStackTrace();
                            hidePDialog();
                        }
                    }
                    hidePDialog();
                    Log.e(Constant.TAG_ARTICLE_READER_ACTIVITY, "***Empty Cache entry for the URL"+URL+"***");

                }
            }
    );
    return request;
    }


    public void setViewContent(Article article){
        articleTitle.setText(article.getArticle_title());
        Log.i(Constant.TAG_ARTICLE_READER_ACTIVITY,article.getArticle_title());
       // articleContent.setText(Html.fromHtml(article.getArticle_content()));
        articleSubTitle.setText(article.getArticle_date());
    }

    public void setViewContent(Bundle bundle){
        if(bundle!=null&&bundle.getString(Constant.KEY_ARTICLE_TITLE)!=null) {
            articleTitle.setText(bundle.getString(Constant.KEY_ARTICLE_TITLE));
            //articleContent.setText(bundle.getString(Constant.KEY_ARTICLE_CONTENT));
        }
        else
            Toast.makeText(getApplicationContext(),"Problem in rendering intent",Toast.LENGTH_SHORT).show();
    }

    public void showPDialog(){
        if(!progressDialog.isShowing()){
            progressDialog.show();
        }
    }
    public void hidePDialog(){
        if(progressDialog.isShowing()){
            progressDialog.dismiss();
        }
    }
}
