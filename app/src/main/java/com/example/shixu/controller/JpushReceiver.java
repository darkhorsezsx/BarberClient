package com.example.shixu.controller;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by Jiaqi Ning on 2014/8/26.
 */
public class JpushReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        Log.d("JP", "[JPushReciver] onReceive " + intent.getAction() + printBundle(bundle));
        String jsonstr = bundle.getString("cn.jpush.android.MESSAGE");
        /*
        try {
            JSONObject json = new JSONObject(jsonstr);
            OrderAccept order = new OrderAccept();
            order.setOrderID("orderID");
            order.setPhone(json.getString("cusphone"));
            order.setTime(json.getString("date "));
            order.setSex(json.getString("sex"));
            order.setName(json.getString("name"));
            order.setHair(json.getString("hairstyle"));
            order.setTime(json.getString("time"));
            order.setRemark(json.getString("remark"));


            Intent i = new Intent();
            i.putExtra("flag",true);
            i.putExtra("order",order);
            i.setClassName("com.example.shixu.barberclient","com.example.shixu.barberclient.ConfirmActivity");
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            long when = System.currentTimeMillis();
            Notification notification = new Notification(R.drawable.ic_notification, "Hello", when);
            CharSequence contentTitle = "wtf";
            CharSequence contentText = "wtf!";
            PendingIntent contentIntent = PendingIntent.getActivity(context, 0, i, PendingIntent.FLAG_CANCEL_CURRENT);
            notification.setLatestEventInfo(context, contentTitle, contentText, contentIntent);
            notification.defaults |= Notification.DEFAULT_SOUND;
            mNotificationManager.notify(1,notification);


            //context.startActivity(i);
        } catch (JSONException e) {
            Log.d("Phase json","Pharse exception!");
        }*/
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
