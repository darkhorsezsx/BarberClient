package com.example.shixu.controller;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.shixu.barberclient.R;
import com.example.shixu.modles.NormalOrderPush;
import com.example.shixu.modles.Order;

import com.example.shixu.modles.QuickOrderPush;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by Jiaqi Ning on 2014/8/26.
 */
public class JpushReceiver extends BroadcastReceiver {
    Order order;
    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        Log.d("JP", "[JPushReciver] onReceive " + intent.getAction() + printBundle(bundle));
        if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())){
            Log.d("JP","reg sucess");
        }if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())){
            Log.d("JP","Recevie msg");
            String jsonstr = bundle.getString("cn.jpush.android.MESSAGE");

            try {
                Gson gson = new Gson();
                String type = "";
                JSONObject json = new JSONObject(jsonstr);
                if (json.has("distance")) {
                    order = gson.fromJson(String.valueOf(json), QuickOrderPush.class);
                    type = "quick";
                } else {
                    order = gson.fromJson(String.valueOf(json), NormalOrderPush.class);
                    type = "normal";
                }

                Intent i = new Intent();
                i.putExtra("type", type);
                i.putExtra("order", order);
                i.setClassName("com.example.shixu.barberclient", "com.example.shixu.barberclient.ConfirmActivity");
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                long when = System.currentTimeMillis();
                Notification notification = new Notification(R.drawable.ic_notification, "Hello", when);
                CharSequence contentTitle = "wtf";
                CharSequence contentText = "wtf!";
                PendingIntent contentIntent = PendingIntent.getActivity(context, 0, i, PendingIntent.FLAG_CANCEL_CURRENT);
                notification.setLatestEventInfo(context, contentTitle, contentText, contentIntent);
                notification.defaults |= Notification.DEFAULT_SOUND;
                mNotificationManager.notify(1, notification);

                context.startActivity(i);

            } catch (JSONException e) {
                Log.d("Phase json", "Pharse exception!");
            }
        }
    }

    private static String printBundle(Bundle bundle) {
        StringBuilder sb = new StringBuilder();
        for (String key : bundle.keySet()) {
            if (key.equals(JPushInterface.EXTRA_NOTIFICATION_ID)) {
                sb.append("\nkey:" + key + ", value:" + bundle.getInt(key));
            }else if(key.equals(JPushInterface.EXTRA_CONNECTION_CHANGE)){
                sb.append("\nkey:" + key + ", value:" + bundle.getBoolean(key));
            }
            else {
                sb.append("\nkey:" + key + ", value:" + bundle.getString(key));
            }
        }
        return sb.toString();
    }
}
