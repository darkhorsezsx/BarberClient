package com.example.shixu.barberclient;

import android.app.Activity;
import android.os.Bundle;

import com.example.shixu.modles.PickerLayout;

public class SetTimeActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new PickerLayout(this));
    }



}
