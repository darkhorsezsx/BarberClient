package com.example.shixu.barberclient;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.example.shixu.controller.CustomRequest;
import com.example.shixu.controller.LocationUtil;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by shixu on 2014/8/18.
 */
public class FragmentInfoShop extends Fragment {
    private View mView;
    private Button btn_next;
    String name;
    String phone;
    String password;
    String sex;
    String shop;
    double longitude;
    double latitude;
    RequestQueue queue;
    String url;
    String ip;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.info_shop, container, false);
        Bundle bundle = getArguments();
        name = bundle.getString("name");
        phone = bundle.getString("phone");
        password = bundle.getString("password");
        sex = bundle.getString("sex");
        latitude = LocationUtil.getLatitude(getActivity());
        longitude = LocationUtil.getLongitude(getActivity());
                                                                                                   //request the shop around here
        Map<String, String> paras = new HashMap<String, String>();
        paras.put("lagitude",Double.toString(latitude));
        paras.put("longitude",Double.toString(longitude));
        CustomRequest req = new CustomRequest(Request.Method.POST, ip+url, paras, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject json) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        });
        queue = Volley.newRequestQueue(getActivity());
        queue.add(req);

        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        btn_next = (Button) mView.findViewById(R.id.btn_shopinfo_next);
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                Bundle bundle = new Bundle();
                bundle.putString("name",name);
                bundle.putString("phone",phone);
                bundle.putString("password",password);
                bundle.putString("sex",sex);
                bundle.putString("shop",shop);
                NBProgressBarFragment nbProgressBarFragment = new NBProgressBarFragment();
                nbProgressBarFragment.setArguments(bundle);
                ft.replace(R.id.register_container,nbProgressBarFragment).addToBackStack(null).commit();
            }
        });



        return mView;
    }
}
