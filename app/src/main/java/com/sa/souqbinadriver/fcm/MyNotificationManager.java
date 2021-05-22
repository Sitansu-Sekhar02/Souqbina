package com.sa.souqbinadriver.fcm;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;


import androidx.core.app.NotificationCompat;

import com.sa.souqbinadriver.Activity.AppController;
import com.sa.souqbinadriver.Activity.MainActivity;
import com.sa.souqbinadriver.R;
import com.sa.souqbinadriver.global.GlobalVariables;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * Created by Saad on 1/8/2018.
 */

public class MyNotificationManager {

    private Context mCtx;
    private static MyNotificationManager mInstance;

    GlobalVariables globalVariables;

    private MyNotificationManager(Context context) {
        mCtx = context;
        globalVariables = AppController.getInstance().getGlobalVariables();
    }

    public static synchronized MyNotificationManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new MyNotificationManager(context);
        }
        return mInstance;
    }

    public void displayNotification(String title, String body) {

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(mCtx, globalVariables.CHANNEL_ID)
                        .setSmallIcon(R.mipmap.app_icon)
                        .setContentTitle(title)
                        .setContentText(body);


        /*
        *  Clicking on the notification will take us to this intent
        *  Right now we are using the MainActivity as this is the only activity we have in our application
        *  But for your project you can customize it as you want
        * */

        Intent resultIntent = new Intent(mCtx, MainActivity.class);

        /*
        *  Now we will create a pending intent
        *  The method getActivity is taking 4 parameters
        *  All paramters are describing themselves
        *  0 is the request code (the second parameter)
        *  We can detect this code in the activity that will open by this we can get
        *  Which notification opened the activity
        * */
        PendingIntent pendingIntent = PendingIntent.getActivity(mCtx, 0, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        /*
        *  Setting the pending intent to notification builder
        * */

        mBuilder.setContentIntent(pendingIntent);

        mBuilder.setContentIntent(pendingIntent);

        NotificationManager mNotifyMgr =
                (NotificationManager) mCtx.getSystemService(NOTIFICATION_SERVICE);

        /*
        * The first parameter is the notification id
        * better don't give a literal here (right now we are giving a int literal)
        * because using this id we can modify it later
        * */
        if (mNotifyMgr != null) {
            mNotifyMgr.notify(1, mBuilder.build());
        }
    }

}