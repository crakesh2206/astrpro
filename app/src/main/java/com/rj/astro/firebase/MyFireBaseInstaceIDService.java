package com.rj.astro.firebase;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.rj.astro.databases.PrefManager;

/**
 * Created by Codefingers-1 on 15-05-2017.
 */

public class MyFireBaseInstaceIDService extends FirebaseInstanceIdService {
       String TAG = "mytag";
    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed token: " + refreshedToken);
        PrefManager pref = new PrefManager(getApplicationContext());
        pref.setToken(refreshedToken);
        // TODO: Implement this method to send any registration to your app's servers.
        sendRegistrationToServer(refreshedToken);
    }

    private void sendRegistrationToServer(String refreshedToken) {

    }
}
