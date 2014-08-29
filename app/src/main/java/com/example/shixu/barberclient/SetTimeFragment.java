package com.example.shixu.barberclient;import android.app.Fragment;import android.os.Bundle;import android.util.Log;import android.view.LayoutInflater;import android.view.View;import android.view.ViewGroup;import android.widget.Button;import android.widget.Toast;import com.android.volley.Request;import com.android.volley.RequestQueue;import com.android.volley.Response;import com.android.volley.VolleyError;import com.android.volley.toolbox.Volley;import com.example.shixu.controller.CustomRequest;import com.example.shixu.modles.PickerLayout;import org.json.JSONException;import org.json.JSONObject;import java.util.HashMap;import java.util.Map;/** * Created by Jiaqi Ning on 2014/8/29. */public class SetTimeFragment extends Fragment {    Button btnNxt;    String name;    String phone;    String password;    String sex;    String shop;    private RequestQueue mRequestQueue;    final String ip = "http://204.152.218.52";    final String url = "/barber/set-time/";    @Override    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {        View view = inflater.inflate(R.layout.set_time_fragment,container,false);        final Bundle bundle = getArguments();        name = bundle.getString("name");        phone = bundle.getString("phone");        password = bundle.getString("password");        sex = bundle.getString("sex");        shop = bundle.getString("shop");        final PickerLayout picker = (PickerLayout)view.findViewById(R.id.my_picker);        btnNxt = (Button)view.findViewById(R.id.btn_nxt);        btnNxt.setOnClickListener(new View.OnClickListener() {            @Override            public void onClick(View view) {                final String time = picker.getTime();                mRequestQueue = Volley.newRequestQueue(getActivity());                Map<String,String> para = new HashMap<String, String>();                para.put("phone",phone);                para.put("time",time);                CustomRequest req = new CustomRequest(Request.Method.POST,ip+url,para,new Response.Listener<JSONObject>() {                    @Override                    public void onResponse(JSONObject json) {                        Log.d("NET",json.toString());                        int code = 0;                        try {                            code = json.getInt("code");                            if(code == 100){                                Bundle bundle = new Bundle();                                bundle.putString("name",name);                                bundle.putString("phone",phone);                                bundle.putString("password", password);                                bundle.putString("sex",sex);                                bundle.putString("shop",shop);                                bundle.putString("time",time);                                Fragment fragment = new SignUpProgressFragment();                                fragment.setArguments(bundle);                                getFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.login_container, fragment).commit();                            }else{                                switch (code){                                    case 903: Toast.makeText(getActivity(), "电话号码错误", Toast.LENGTH_SHORT).show();break;                                    case 904: Toast.makeText(getActivity(),"该理发师尚未注册",Toast.LENGTH_SHORT).show();break;                                    case 905: Toast.makeText(getActivity(),"时间设置错误，格式错误或者是设置不合理",Toast.LENGTH_SHORT).show();break;                                }                            }                        } catch (JSONException e) {                            e.printStackTrace();                        }                    }                },new Response.ErrorListener() {                    @Override                    public void onErrorResponse(VolleyError volleyError) {                        Log.d("NET",volleyError.toString());                    }                });                mRequestQueue.add(req);            }        });        return view;    }}