package com.example.root.themitpostapp.Util;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.DisplayMetrics;
import android.util.LruCache;

import com.android.volley.toolbox.ImageLoader;

/**
 * Created by root on 5/3/16.
 */
public class LruBitmapCache extends LruCache<String,Bitmap> implements ImageLoader.ImageCache {

    public LruBitmapCache(int maxSize) {
        super(maxSize);
    }
    public LruBitmapCache(Context ctx){
        this(getCacheSize(ctx));
    }
    public static int getCacheSize(Context ctx){
        final DisplayMetrics displayMetrics = ctx.getResources().
                getDisplayMetrics();
        final int screenWidth = displayMetrics.widthPixels;
        final int screenHeight = displayMetrics.heightPixels;
        // 4 bytes per pixel
        final int screenBytes = screenWidth * screenHeight * 4;

        return screenBytes * 3;
    }

    @Override
    protected int sizeOf(String key,Bitmap value){
        return value.getRowBytes()*value.getHeight();
    }


    @Override
    public Bitmap getBitmap(String url) {
        return get(url);
    }

    @Override
    public void putBitmap(String url, Bitmap bitmap) {
        put(url,bitmap);
    }
}
