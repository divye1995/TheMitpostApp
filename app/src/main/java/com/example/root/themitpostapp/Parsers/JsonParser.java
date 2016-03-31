package com.example.root.themitpostapp.Parsers;

import android.content.Context;
import android.provider.DocumentsContract;
import android.util.Log;

import com.example.root.themitpostapp.Constants.Constant;
import com.example.root.themitpostapp.Model.Article;
import com.example.root.themitpostapp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.io.InputStream;

import static org.jsoup.Jsoup.*;

/**
 * Created by root on 1/3/16.
 */
public class JsonParser {

    private final static String TAG="Services.JsonParser";
    //Utility class to Parse and handle JSON objects
    public static JSONArray loadJSONFromAssets(Context context,String fileName,String objectName){
        String json=null;
        JSONArray jsonArray=null;
        try{
            InputStream inputStream=context.getAssets().open(fileName);
            int size=inputStream.available();
            byte[] buffer=new byte[size];
            inputStream.read(buffer);
            //for logging purposes
            json=new String(buffer,"UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
            Log.i(TAG,context.getString(R.string.log_message_io_exception));
            return null;
        }
        try{
            jsonArray=new JSONObject(json).getJSONArray(objectName);
        }
        catch(JSONException e){
            e.printStackTrace();
            Log.i(TAG,context.getString(R.string.log_message_json_object_exception));
            return  null;
        }
        return jsonArray;
    }

    public static Article getArticleFromRequest(JSONObject request){
        Article article;
        try {
            String title = request.getString(Constant.RESPONSE_KEY_TITLE);
            String content=request.getString(Constant.RESPONSE_KEY_CONTENT);
            String date=request.getString(Constant.RESPONSE_KEY_DATE);
            String feature_image=request.getString(Constant.RESPONSE_KEY_FEATURE_IMAGE);
            //String author;
            article = new Article(title,content,date,feature_image);
        }
        catch (JSONException e){
            Log.e(TAG,"******JSON_PARSER_getArticleFromRequest****");
            e.printStackTrace();
            return null;
        }
    return article;
    }
}
