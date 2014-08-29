package com.example.shixu.barberclient;


import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


/**
 * A simple {@link Fragment} subclass.
 *
 */
public class WelcomePagerTwo extends Fragment{
    public WelcomePagerTwo() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_welcome_pager_two, container, false);
        Button btnUp = (Button)view.findViewById(R.id.btn_signup);
        Button btnIn = (Button)view.findViewById(R.id.btn_signin);

        btnIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), LoginSignUpActivity.class);
                i.putExtra("flag",0);
                startActivity(i);
            }
        });
        btnUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), LoginSignUpActivity.class);
                startActivity(i);
            }
        });

        return view;
    }



}
