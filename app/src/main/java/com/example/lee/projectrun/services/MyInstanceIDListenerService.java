package com.example.lee.projectrun.services;

import android.content.Intent;

import com.google.android.gms.iid.InstanceIDListenerService;
import com.oovoo.core.Utils.LogSdk;
import com.example.lee.projectrun.app.ApplicationSettings;
import com.example.lee.projectrun.app.ooVooSdkSampleShowApp;

/**
 * Created by oovoo on 9/8/15.
 */
public class MyInstanceIDListenerService extends InstanceIDListenerService {

    private static final String TAG = "MyInstanceIDListenerService";

    @Override
    public void onTokenRefresh() {
        LogSdk.d(TAG, "onTokenRefresh");

        try {
            ooVooSdkSampleShowApp application = (ooVooSdkSampleShowApp) getApplication();
            ApplicationSettings settings = application.getSettings();
            String username = settings.get(ApplicationSettings.Username);
            settings.remove(username);

            Intent intent = new Intent(this, RegistrationIntentService.class);
            startService(intent);
        } catch (Exception e) {
            LogSdk.e(TAG, "onTokenRefresh - Failed to complete token refresh", e);
        }
    }
}
