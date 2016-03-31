package com.example.root.themitpostapp.app;

import android.app.Application;

import com.example.root.themitpostapp.helper.MyPreferenceManager;

/**
 * Created by root on 21/3/16.
 */
public class MyApplication extends Application {

    public static final String TAG = MyApplication.class
            .getSimpleName();

    private static MyApplication mInstance;

    private MyPreferenceManager pref;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }

    public static synchronized MyApplication getInstance() {
        return mInstance;
    }


    public MyPreferenceManager getPrefManager() {
        if (pref == null) {
            pref = new MyPreferenceManager(this);
        }

        return pref;
    }
}

