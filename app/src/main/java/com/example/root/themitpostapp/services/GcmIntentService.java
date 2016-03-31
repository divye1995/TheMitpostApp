package com.example.root.themitpostapp.services;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.widget.Toast;

import com.example.root.themitpostapp.app.Config;
import com.google.android.gms.gcm.GcmPubSub;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;

import com.example.root.themitpostapp.R;

import java.io.IOException;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class GcmIntentService extends IntentService {

    private static final String TAG=GcmIntentService.class.getSimpleName();

    public static final String KEY = "key";
    public static final String TOPIC = "topic";
    public static final String SUBSCRIBE = "subscribe";
    public static final String UNSUBSCRIBE = "unsubscribe";
    // TODO: Rename actions, choose action names that describe tasks that this
    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    private static final String ACTION_FOO = "com.example.root.themitpostapp.services.action.FOO";
    private static final String ACTION_BAZ = "com.example.root.themitpostapp.services.action.BAZ";

    // TODO: Rename parameters
    private static final String EXTRA_PARAM1 = "com.example.root.themitpostapp.services.extra.PARAM1";
    private static final String EXTRA_PARAM2 = "com.example.root.themitpostapp.services.extra.PARAM2";

    public GcmIntentService() {
        super(TAG);
    }

    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    @Override
    protected void onHandleIntent(Intent intent) {

        if (intent != null) {
            String key=intent.getStringExtra(KEY);
            switch (key){
                case SUBSCRIBE:
                    String topic=intent.getStringExtra(TOPIC);
                    subscribeToTopic(topic);
                    break;
                case UNSUBSCRIBE:
                    String topic1=intent.getStringExtra(TOPIC);
                    break;
                default:
                    registerGCM();
            }
        }
    }

    private void registerGCM(){
        SharedPreferences sharedPreferences= PreferenceManager.getDefaultSharedPreferences(this);
        String token=null;
        try {
            InstanceID instanceID=InstanceID.getInstance(this);
            token=instanceID.getToken(getString(R.string.gcm_defaultSenderId),
                    GoogleCloudMessaging.INSTANCE_ID_SCOPE,null);
            Log.e(TAG,"GCM Registration Token"+token);

            //send registration token to server
            sendRegistrationTokentoServer(token);
            sharedPreferences.edit().putBoolean(Config.SENT_TOKEN_TO_SERVER,true).apply();
        }catch (Exception e){
            Log.e(TAG,"Failed to Refresh token",e);
            sharedPreferences.edit().putBoolean(Config.SENT_TOKEN_TO_SERVER,false).apply();
        }
        Intent registrationComplete = new Intent(Config.REGISTRATION_COMPLETE);
        registrationComplete.putExtra("token", token);
        LocalBroadcastManager.getInstance(this).sendBroadcast(registrationComplete);
    }
    public void sendRegistrationTokentoServer(String token){
        //send token to server to store the value
    }

    /**
     * Subscribe to a topic
     */
    public void subscribeToTopic(String topic) {
        GcmPubSub pubSub = GcmPubSub.getInstance(getApplicationContext());
        InstanceID instanceID = InstanceID.getInstance(getApplicationContext());
        String token = null;
        try {
            token = instanceID.getToken(getString(R.string.gcm_defaultSenderId),
                    GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);
            if (token != null) {
                pubSub.subscribe(token, "/topics/" + topic, null);
                Log.e(TAG, "Subscribed to topic: " + topic);
            } else {
                Log.e(TAG, "error: gcm registration id is null");
            }
        } catch (IOException e) {
            Log.e(TAG, "Topic subscribe error. Topic: " + topic + ", error: " + e.getMessage());
            Toast.makeText(getApplicationContext(), "Topic subscribe error. Topic: " + topic + ", error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void unsubscribeFromTopic(String topic) {
        GcmPubSub pubSub = GcmPubSub.getInstance(getApplicationContext());
        InstanceID instanceID = InstanceID.getInstance(getApplicationContext());
        String token = null;
        try {
            token = instanceID.getToken(getString(R.string.gcm_defaultSenderId),
                    GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);
            if (token != null) {
                pubSub.unsubscribe(token, "");
                Log.e(TAG, "Unsubscribed from topic: " + topic);
            } else {
                Log.e(TAG, "error: gcm registration id is null");
            }
        } catch (IOException e) {
            Log.e(TAG, "Topic unsubscribe error. Topic: " + topic + ", error: " + e.getMessage());
            Toast.makeText(getApplicationContext(), "Topic subscribe error. Topic: " + topic + ", error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }


}
