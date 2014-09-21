package com.example.shixu.barberclient;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.opengl.Visibility;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import cn.jpush.android.api.JPushInterface;


public class MyActivity extends Activity {

    boolean isLogIn;




    boolean isEditMode = false;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    String name;
    String phone;

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
        preferences = getSharedPreferences("com.cuthead.app.sp", MODE_PRIVATE);
        editor = preferences.edit();
        isLogIn = preferences.getBoolean("isLogin", false);
        if (!isLogIn) {
            Intent intent = new Intent(this, WelcomeActivity.class);
            startActivity(intent);
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.my, menu);
        if (isEditMode){
            MenuItem completeItem = menu.findItem(R.id.complete);
            MenuItem editItem = menu.findItem(R.id.edit);
            editItem.setVisible(false);
            completeItem.setVisible(true);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.edit) {

            isEditMode = true;
            this.invalidateOptionsMenu();
        } else if(item.getItemId() == R.id.complete){

            isEditMode = false;
            this.invalidateOptionsMenu();

        }

        return super.onOptionsItemSelected(item);
    }

    public void saveInfo(){

        editor.putString("name",name);
        editor.putString("phone",phone);
        /*
        editor.putString("password",password);
        editor.putString("sex",sex);
        editor.putString("shop",shop);
        editor.putString("time",time);
        */
        editor.commit();
    }
}
