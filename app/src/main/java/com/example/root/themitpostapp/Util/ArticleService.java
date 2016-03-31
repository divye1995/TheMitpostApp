package com.example.root.themitpostapp.Util;

import android.os.Bundle;
import android.util.Log;

import com.example.root.themitpostapp.Constants.Constant;
import com.example.root.themitpostapp.Model.Article;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by root on 3/3/16.
 */
public class ArticleService {


    public static Bundle getArticleBundle(Article article){
        if(article==null){
            Log.i(Constant.TAG_ARTICLE_SERVICE,"****Article Null Value ****");
        }
        else
        Log.i(Constant.TAG_ARTICLE_SERVICE,article.getArticle_title());

        Bundle bundle=new Bundle();
        bundle.putString(Constant.KEY_ARTICLE_TITLE,article.getArticle_title());
        bundle.putString(Constant.KEY_ARTICLE_FEATURE_IMAGE,article.getArticleImageRef());
       // bundle.putString(Constant.KEY_ARTICLE_CONTENT,article.getArticle_content());
    return bundle;
}
    public static String[] getSectionList(){
        //TODO get category list from prefs
        String[] list=new String[]{Constant.CATEGORY_CAMPUS_COMMUNITY,Constant.CATEGORY_NATION_WORLD
        ,Constant.CATEGORY_ART_CULTURE};
        return list;
    }
    public static ArrayList<Article> fromJSON(JSONArray jsonObjects){
        ArrayList<Article> list=new ArrayList<>();
        for(int i=0;i<jsonObjects.length();i++){
            try{
                list.add(new Article(jsonObjects.getJSONObject(i)));
            }catch(JSONException e)
            {
                e.printStackTrace();
            }
        }

        return list;
    }
    public static String formatArticleDate(String article_date){
        Date today=new Date();
        if(!today.equals(article_date)){
            return article_date.substring(0,10);
        }
        return "Today";
    }
    //return the query URL
    public static String getRequestURL(String category){
        String URL = Constant.SAMPLE_URL + Constant.REQUEST_URL_FIELDS+Constant.REQUEST_URL_QUERY;
        if(!category.equals(Constant.CATEGORY_LATEST)) {
            URL=URL+Constant.REQUEST_URL_CATEGORY+Constant.CATEGORY_MAP.get(category);
        }
        return URL;
    }
    }
