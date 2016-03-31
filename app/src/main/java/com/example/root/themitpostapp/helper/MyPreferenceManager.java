package com.example.root.themitpostapp.helper;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by root on 21/3/16.
 */
public class MyPreferenceManager {


        private String TAG = MyPreferenceManager.class.getSimpleName();
        private static final String DEFAULT_NOTIFICATIONS="No Notification Text Available";
        // Shared Preferences
        SharedPreferences pref;

        // Editor for Shared preferences
        SharedPreferences.Editor editor;

        // Context
        Context _context;

        // Shared pref mode
        int PRIVATE_MODE = 0;

        // Sharedpref file name
        private static final String PREF_NAME = "mitpost_push_notification";

        // All Shared Preferences Keys
        private static final String KEY_USER_ID = "user_id";
        private static final String KEY_USER_NAME = "user_name";
        private static final String KEY_USER_EMAIL = "user_email";
        private static final String KEY_NOTIFICATIONS = "notifications";

        // Constructor
        public MyPreferenceManager(Context context) {
            this._context = context;
            pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
            editor = pref.edit();
        }

        public void addNotification(String notification) {

            // get old notifications
            String oldNotifications = getNotifications();

            if (oldNotifications != null) {
                oldNotifications += "|" + notification;
            } else {
                oldNotifications = notification;
            }

            editor.putString(KEY_NOTIFICATIONS, oldNotifications);
            editor.commit();
        }

        public String getNotifications() {
            return pref.getString(KEY_NOTIFICATIONS,DEFAULT_NOTIFICATIONS);
        }

        public void clear() {
            editor.clear();
            editor.commit();
        }
}

