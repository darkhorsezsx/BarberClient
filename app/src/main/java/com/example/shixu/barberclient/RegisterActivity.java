package com.example.shixu.barberclient;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;

/**
 * Created by shixu on 2014/8/18.
 */
public class RegisterActivity extends Activity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        FragmentManager fragmentManager = getFragmentManager();
        final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        FragmentLogin fragmentLogin = new FragmentLogin();
        fragmentTransaction.replace(R.id.register_container,fragmentLogin).commit();

    }

}
