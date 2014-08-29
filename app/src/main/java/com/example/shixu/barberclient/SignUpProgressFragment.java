package com.example.shixu.barberclient;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import com.example.shixu.controller.LocationUtil;
import com.example.shixu.controller.ProgressWheel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

//import com.example.shixu.barberclient.VollyErrorHelper;


/**
 * A simple {@link android.app.Fragment} subclass.
 *
 */
public class SignUpProgressFragment extends Fragment {
    private RequestQueue mRequestQueue;
    final String ip = "http://204.152.218.52";
    final String normal_url = "/barber/register/";
    String name;
    String phone;
    String password;
    String sex;
    String shop;
    String time;
    double longitude;
    double latitude;
    public SignUpProgressFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_nbprogress_bar, container, false);
        final ProgressWheel progressbar = (ProgressWheel)view.findViewById(R.id.progress_wheel);
        progressbar.spin();

        latitude = LocationUtil.getLatitude(getActivity());
        longitude = LocationUtil.getLongitude(getActivity());

        Bundle bundle = getArguments();
        name = bundle.getString("name");
        phone = bundle.getString("phone");
        password = bundle.getString("password");
        sex = bundle.getString("sex");
        shop = bundle.getString("shop");
        time = bundle.getString("time");

        mRequestQueue = Volley.newRequestQueue(getActivity());
        Map<String,String> para = new HashMap<String, String>();
        // add data
        para.put("name",name);
        para.put("phone",phone);
        para.put("password",password);
        para.put("sex",sex);
        para.put("shop",shop);
        para.put("time",time);

        CustomRequest req = new CustomRequest(Request.Method.POST,ip+normal_url,para,new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject json) {
                //  need to identify weather is the new barber

                //   new register barber
                int code = 0;
                try {
                    code = json.getInt("code");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if(code == 100){
                    Toast toast = Toast.makeText(getActivity(),"注册成功",Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER,0,0);
                    toast.show();
                    Intent intent = new Intent(getActivity(),MyActivity.class);
                    startActivity(intent);
                }
                else
                {
                    switch (code){
                        case 703:Toast.makeText(getActivity(),"包含有不合法字符或者是长度错误",Toast.LENGTH_SHORT).show();break;
                        case 704:Toast.makeText(getActivity(),"电话号码格式错误",Toast.LENGTH_SHORT).show();break;
                        case 705:Toast.makeText(getActivity(),"在“Female”或者是 “Male”之间",Toast.LENGTH_SHORT).show();break;
                        case 706:Toast.makeText(getActivity(),"密码强度不够",Toast.LENGTH_SHORT).show();break;
                        case 707:Toast.makeText(getActivity(),"不存在这样的理发店",Toast.LENGTH_SHORT).show();break;
                        case 708:Toast.makeText(getActivity(),"时间格式错误，或者时间设置不合理",Toast.LENGTH_SHORT).show();break;
                    }
                }

                Log.d("NET", json.toString());

            }
        },new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.d("NET",volleyError.toString());
            }
        });
        mRequestQueue.add(req);
        return view;
    }


}