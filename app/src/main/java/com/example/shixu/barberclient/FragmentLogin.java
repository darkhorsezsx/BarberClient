package com.example.shixu.barberclient;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by shixu on 2014/8/18.
 */
public class FragmentLogin extends Fragment {
    private View mView;
    private Button btn_login;
    private Button btn_register;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.login, container, false);
        FragmentManager fm = getFragmentManager();
        final FragmentTransaction ft = fm.beginTransaction();
        btn_register = (Button) mView.findViewById(R.id.btn_register);
        btn_login = (Button) mView.findViewById(R.id.btn_login);
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentInfoBasic fragmentInfoBasic = new FragmentInfoBasic();
                ft.replace(R.id.register_container,fragmentInfoBasic).addToBackStack(null).commit();
            }
        });
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),MyActivity.class);
                startActivity(intent);
            }
        });

        return mView;
    }
}
