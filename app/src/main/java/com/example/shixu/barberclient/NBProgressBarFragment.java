package com.example.shixu.barberclient;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.example.shixu.controller.CustomRequest;
import com.example.shixu.controller.ProgressWheel;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

//import com.example.shixu.barberclient.VollyErrorHelper;


/**
 * A simple {@link android.app.Fragment} subclass.
 *
 */
public class NBProgressBarFragment extends Fragment {
    private RequestQueue mRequestQueue;
    final String ip = "http://204.152.218.52";
    final String normal_url = "/appointment/normal/";
    String name;
    String phone;
    String password;
    String sex;
    String shop;
    public NBProgressBarFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_nbprogress_bar, container, false);
        final ProgressWheel progressbar = (ProgressWheel)view.findViewById(R.id.progress_wheel);
        progressbar.spin();

        Bundle bundle = getArguments();
        name = bundle.getString("name");
        phone = bundle.getString("phone");
        password = bundle.getString("password");
        sex = bundle.getString("sex");
        shop = bundle.getString("shop");

        mRequestQueue = Volley.newRequestQueue(getActivity());
        Map<String,String> para = new HashMap<String, String>();
        // add data
        para.put("name",name);
        para.put("phone",phone);
        para.put("password",password);
        para.put("sex",sex);
        para.put("shop",shop);

        CustomRequest req = new CustomRequest(Request.Method.POST,ip+normal_url,para,new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject json) {
                //  need to identify weather is the new barber

                //   new register barber
                Toast toast = Toast.makeText(getActivity(),"注册成功",Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER,0,0);
                toast.show();
                Intent intent = new Intent(getActivity(),MyActivity.class);
                startActivity(intent);

                //  old barber
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                FragmentLogin fragmentLogin = new FragmentLogin();
                ft.replace(R.id.register_container,fragmentLogin).addToBackStack(null).commit();
            }
        },new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        });
        mRequestQueue.add(req);
        return view;
    }


}