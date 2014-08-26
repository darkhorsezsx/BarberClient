package com.example.shixu.barberclient;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;

/**
 * Created by shixu on 2014/8/18.
 */
public class HistoryActivity extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        FragmentManager manager = getFragmentManager();
        Fragment fragment = manager.findFragmentById(R.id.fragment_container_userinfo);

        if (fragment == null) {
            fragment = new FragmentHistory();
            manager.beginTransaction().add(R.id.fragment_container_userinfo, fragment)
                    .commit();
        }

    }
}
