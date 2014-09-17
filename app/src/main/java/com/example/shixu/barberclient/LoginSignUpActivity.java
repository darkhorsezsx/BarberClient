package com.example.shixu.barberclient;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.Window;

/**
 * Created by shixu on 2014/8/18.
 */
public class LoginSignUpActivity extends Activity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        requestWindowFeature(Window.FEATURE_PROGRESS);
        setContentView(R.layout.activity_login);

        int flag = getIntent().getIntExtra("flag",1);   // sign up is 1,login is 0

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment fragment;
        if (flag == 1)
            fragment = new FragmentLogin();
        else
            fragment = new FragmentSignUp();


        fragmentTransaction.replace(R.id.login_container,fragment).commit();

    }

}
