package com.example.root.themitpostapp.Util;

import android.app.Application;
import android.content.Context;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.ImageLoader;

import java.util.Objects;

/**
 * Created by root on 3/3/16.
 */
public class Singleton{

    private static Singleton mInstance;
    private RequestQueue mRequestQueue;
    private static Context context;
    Cache cache;
    Network network;
    ImageLoader imageLoader;
    //addJsonloaders or ImageLoaders or Custom

    public static final String TAG=Singleton.class.getSimpleName();

    public Singleton(Context context){
        this.context=context;
        mRequestQueue=getRequestQueue();

    }

    public static synchronized Singleton getInstance(Context context){
        if(mInstance==null){
            mInstance=new Singleton(context);
        }
        return mInstance;
    }


    public RequestQueue getRequestQueue(){
        if(mRequestQueue==null){
            cache=new DiskBasedCache(context.getCacheDir(),1024 * 1024);
            network=new BasicNetwork(new HurlStack());
            mRequestQueue=new RequestQueue(cache,network);
            mRequestQueue.start();
            //mRequestQueue= Volley.newRequestQueue(context.getApplicationContext());
        }
        return mRequestQueue;
    }
    public ImageLoader getImageLoader(){
        getRequestQueue();
        if(imageLoader==null){
            imageLoader=new ImageLoader(this.mRequestQueue,new LruBitmapCache(LruBitmapCache.getCacheSize(context)));
        }
        return this.imageLoader;
    }

    public <T> void addToRequestQueue(Request<T> request){
        request.setTag(TAG);
        getRequestQueue().add(request);
    }
    public <T> void addToRequestQueue(Request<T> request,String tag){
        request.setTag(Objects.equals(tag, "") ? TAG:tag);
        getRequestQueue().add(request);
    }

    //add methods to return ImageLoader or JSONLoaders


}
