package com.example.shixu.barberclient;

import android.app.Activity;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;

/**
 * Created by shixu on 2014/8/17.
 */
public class WelcomeActivity extends Activity{
    ViewPager welcomePage;
    WelcomePagerAdapter myPageAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcomepager);

        FragmentManager manager = getFragmentManager();
        welcomePage = (ViewPager)findViewById(R.id.welcome_pager);
        myPageAdapter = new WelcomePagerAdapter(manager);
        welcomePage.setAdapter(myPageAdapter);

    }


}
