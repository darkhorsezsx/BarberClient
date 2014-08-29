package com.example.shixu.barberclient;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by shixu on 2014/8/28.
 */
public class ProgressBarFragment extends Fragment {
    private RequestQueue mRequestQueue;
    final String ip = "http://204.152.218.52";
    final String url = "/appointment/quick/order-accepted/";
    String name;
    String phone;
    String sex;
    String distance;
    String orderID;
    Boolean isNormal;
    public ProgressBarFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_nbprogress_bar, container, false);
        final ProgressWheel progressbar = (ProgressWheel)view.findViewById(R.id.progress_wheel);
        progressbar.spin();

        Bundle bundleget = getArguments();
        name = bundleget.getString("name");
        phone = bundleget.getString("phone");
        sex = bundleget.getString("sex");
        distance = bundleget.getString("distance");
        orderID = bundleget.getString("orderID");
        isNormal = bundleget.getBoolean("isnormal");

        final Bundle bundle = new Bundle();
        bundle.putString("oederID",orderID);
        bundle.putString("name",name);
        bundle.putString("phone",phone);
        bundle.putString("sex",sex);
        bundle.putString("distance",distance);
        bundle.putBoolean("isnormal",isNormal);

        mRequestQueue = Volley.newRequestQueue(getActivity());
        Map<String,String> para = new HashMap<String, String>();

        para.put("oederID",orderID);
        para.put("phone",phone);
        para.put("distance",distance);

        CustomRequest req = new CustomRequest(Request.Method.POST,ip+url,para,new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject json) {
                int code = 0;
                try {
                    code = json.getInt("code");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if(code == 100){
                    FragmentManager fm = getFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();
                    FragmentOrderSuccess fragmentOrderSuccess = new FragmentOrderSuccess();
                    fragmentOrderSuccess.setArguments(bundle);
                    ft.replace(R.id.confirm_container,fragmentOrderSuccess).addToBackStack(null).commit();
                }else{
                    switch (code){
                        case 503: Toast.makeText(getActivity(),"订单编号错误，不存在这样的订单",Toast.LENGTH_SHORT).show();break;
                        case 504: Toast.makeText(getActivity(),"电话号码错误，检查电话号码格式是否正确",Toast.LENGTH_SHORT).show();break;
                        case 505: Toast.makeText(getActivity(),"理发师尚未注册",Toast.LENGTH_SHORT).show();break;
                        case 506: Toast.makeText(getActivity(),"订单已被接受",Toast.LENGTH_SHORT).show();break;
                        case 507: Toast.makeText(getActivity(),"距离数据错误，类型错误或是数值不合理",Toast.LENGTH_SHORT).show();break;
                    }
                }

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
