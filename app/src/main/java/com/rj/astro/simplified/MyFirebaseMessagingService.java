package com.rj.astro.simplified;

import android.content.Intent;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.rj.astro.activities.Step;
import com.rj.astro.activities.admin.AdminText;

import org.json.JSONException;
import org.json.JSONObject;


public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private static final String TAG = "MyFirebaseMsgService";
    public static final String NEW_MESSAGE = "rj.takedata";
    public static final String QID ="qqid" ;
    public static final String CAT = "catgory" ;
    public static final String SUBCAT = "subcatgory" ;
    public static final String QUESS = "quess" ;
    public static final String USER_I_D = "us_id" ;
    public static final String USER_TY = "user_ty";
    public static final String TYM = "tym";


    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        if (remoteMessage.getData().size() > 0) {
            Log.e(TAG, "Data Payload: " + remoteMessage.getData().toString());
            try {
                JSONObject json = new JSONObject(remoteMessage.getData().toString());
                sendPushNotification(json);
            } catch (Exception e) {
                Log.e(TAG, "Exception: " + e.getMessage());
            }
        }
    }

    //this method will display the notification
    //We are passing the JSONObject that is received from
    //firebase cloud messaging
    private void sendPushNotification(JSONObject json) {
        //optionally we can display the json into log
        Log.e(TAG, "Notification JSON " + json.toString());
        try {
            //getting the json data
            JSONObject data = json.getJSONObject("data");

            //parsing json data
            String title = data.getString("title");
            String message = data.getString("message");
            String imageUrl = data.getString("image");

            final String cat = data.getString("catagory");
            final String sub_cat = data.getString("sub_catagoty");
            final String ques = data.getString("ques");
            final String user_id = data.getString("user_id");
            final String time_a = data.getString("time_a");
            final String usertype = data.getString("usertype");
            final String qid = String.valueOf(data.getJSONArray("qid").get(0));

            new Thread(new Runnable() {
                @Override
                public void run() {

                  Intent i = new Intent(NEW_MESSAGE);
                    i.putExtra(CAT,cat);
                    i.putExtra(SUBCAT,sub_cat);
                    i.putExtra(QUESS,ques);
                    i.putExtra(USER_I_D,user_id);
                    i.putExtra(TYM,time_a);
                    i.putExtra(USER_TY,usertype);
                    i.putExtra(QID,qid);
                    sendBroadcast(i);



                }
            }).start();

            //creating MyNotificationManager object
            MyNotificationManager mNotificationManager = new MyNotificationManager(getApplicationContext());

            //creating an intent for the notification
            Intent intent;
            if(usertype.equals("admin")) {
                intent = new Intent(getApplicationContext(), Step.class);
            }else{
                intent = new Intent(getApplicationContext(), AdminText.class);
                intent.putExtra("name",title);
                intent.putExtra("uid",user_id);
            }
            //if there is no image
            if(imageUrl.equals("null")){
                //displaying small notification
                mNotificationManager.showSmallNotification(title, message, intent);
            }else{
                //if there is an image
                //displaying a big notification
                mNotificationManager.showBigNotification(title, message, imageUrl, intent);
            }
        } catch (JSONException e) {
            Log.e(TAG, "Json Exception: " + e.getMessage());
        } catch (Exception e) {
            Log.e(TAG, "Exception: " + e.getMessage());
        }
    }


}
