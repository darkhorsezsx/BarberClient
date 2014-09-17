package com.example.shixu.barberclient;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import cn.jpush.android.api.JPushInterface;
import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardHeader;
import it.gmariotti.cardslib.library.view.CardView;


public class MyActivity extends Activity {

    boolean isFirstIn;
    @Override
    protected void onResume() {
        super.onResume();
        JPushInterface.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        JPushInterface.onPause(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        // To ensure firt in to show the welcome page


        SharedPreferences preferences = getSharedPreferences("com.cuthead.app.sp",MODE_PRIVATE);
        isFirstIn = preferences.getBoolean("isFirstIn",true);
        if (isFirstIn){
        /* for testing
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean("isFirstIn",false);
            editor.commit();
            */
            Intent intent = new Intent(this,WelcomeActivity.class);
            startActivity(intent);

        }




    }



}
