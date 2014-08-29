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
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean("isFirstIn",false);
            editor.commit();
            Intent intent = new Intent(this,WelcomeActivity.class);
            startActivity(intent);

        }

        Card timecard = new Card(MyActivity.this,R.layout.activity_card_time);
        CardHeader timeheader = new CardHeader(MyActivity.this);
        timecard.addCardHeader(timeheader);
        CardView timecardView = (CardView) this.findViewById(R.id.card_time);
        timecardView.setCard(timecard);
        timecardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyActivity.this,SetTimeActivity.class);
                startActivity(intent);
            }
        });

        Card historycard = new Card(MyActivity.this,R.layout.activity_card_histroy);
        CardHeader hisheader = new CardHeader(MyActivity.this);
        timecard.addCardHeader(hisheader);
        CardView hiscardView = (CardView) this.findViewById(R.id.card_history);
        hiscardView.setCard(historycard);
        hiscardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyActivity.this,HistoryActivity.class);
                startActivity(intent);
            }
        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
