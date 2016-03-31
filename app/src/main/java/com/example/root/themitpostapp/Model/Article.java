package com.example.root.themitpostapp.Model;

import android.util.Log;

import com.example.root.themitpostapp.Constants.Constant;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by root on 27/2/16.
 */
public class Article {

    String article_id;
    String article_title;
    //String article_content;
    String article_image_url;
    String article_date;
    String article_preview;
    String article_url;
    //String category_slug;

    //String article_preview;
    public final static String JSON_OBJECT_TITLE="title";
    public final static String JSON_OBJECT_CONTENT="content";
    public final static String JSON_OBJECT_IMAGE="featured_image";
    public final static String JSON_OBJECT_PREVIEW="excerpt";
    public final static String JSON_OBJECT_URL="URL";
    public final static String JSON_OBJECT_ID="ID";
    public final static String JSON_OBJECT_DATE="date";
    //public final static String JSON_OBJECT_SLUG="slug";


    public static String READ_MORE="...read more";

    public Article(String title,String article_content,String date,String image_reference){
        //this.article_content=article_content;
        this.article_title=title;
        article_date=date;
        this.article_image_url=image_reference;
    }
    public Article(JSONObject object) {
        try {
            article_preview = object.getString(JSON_OBJECT_PREVIEW);
            article_title = object.getString(JSON_OBJECT_TITLE);
            //article_content = object.getString(JSON_OBJECT_CONTENT);
            article_date=object.getString(JSON_OBJECT_DATE);
            article_image_url=object.getString(JSON_OBJECT_IMAGE);
            article_url=object.getString(JSON_OBJECT_URL)+ Constant.AMP_URL;
            article_id=object.getString(JSON_OBJECT_ID);
         //   category_slug=object.getString(JS)

        }
        catch (JSONException e) {
            e.printStackTrace();
            Log.i("ARTICLE_MODEL", "*****JSON EXCEPTION****");
        }
    }

    //FACTORY METHOD TO GET LIST OF ARTICLES


    public String getArticlePreview(){
        int length;
        //length=article_content.length();
        return article_preview;
    }
    public String getArticleImageRef(){
        return article_image_url;
    }
    public String getArticle_title(){return article_title;}
   // public String getArticle_content(){return article_content;}
    public String getArticle_date(){return article_date;}
    public String getArticle_url(){return article_url;}
    //public String getCategory_slug(){return category_slug;}
}
