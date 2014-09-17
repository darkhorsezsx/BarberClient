package com.example.shixu.barberclient;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
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
import com.example.shixu.modles.UrlConstance;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by shixu on 2014/8/28.
 */
public class ProgressBarFragment extends Fragment {
    private RequestQueue mRequestQueue;

    final String url = UrlConstance.IP + UrlConstance.ORDER_ACCEPT;
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

        final Bundle bundle = getArguments();
        phone = bundle.getString("phone");
        distance = bundle.getString("distance");
        orderID = bundle.getString("orderID");



        mRequestQueue = Volley.newRequestQueue(getActivity());
        Map<String,String> para = new HashMap<String, String>();
        para.put("orderID",orderID);
        para.put("phone",phone);
        para.put("distance",distance);

        CustomRequest req = new CustomRequest(Request.Method.POST,url,para,new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject json) {
                int code = 0;
                Log.d("wtf",json.toString());
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
                    Toast.makeText(getActivity(),"嗷，我们出错了，抱歉",Toast.LENGTH_SHORT).show();
                    getActivity().finish();
                    /*
                    switch (code){
                        case 503: Toast.makeText(getActivity(),"订单编号错误，不存在这样的订单",Toast.LENGTH_SHORT).show();break;
                        case 504: Toast.makeText(getActivity(),"电话号码错误，检查电话号码格式是否正确",Toast.LENGTH_SHORT).show();break;
                        case 505: Toast.makeText(getActivity(),"理发师尚未注册",Toast.LENGTH_SHORT).show();break;
                        case 506: Toast.makeText(getActivity(),"订单已被接受",Toast.LENGTH_SHORT).show();break;
                        case 507: Toast.makeText(getActivity(),"距离数据错误，类型错误或是数值不合理",Toast.LENGTH_SHORT).show();break;
                    }*/
                }

            }
        },new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.d("wtf",volleyError.toString());
            }
        });
        mRequestQueue.add(req);
        return view;
    }
}
