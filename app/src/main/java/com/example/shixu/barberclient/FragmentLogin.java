package com.example.shixu.barberclient;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.example.shixu.controller.CustomRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by shixu on 2014/8/18.
 */
public class FragmentLogin extends Fragment {
    private View mView;
    private Button btn_login;
    private EditText et_login_phone;
    private EditText et_login_password;
    String phone;
    String password;
    String ip = "http://204.152.218.52";
    String url = "/barber/login/";
    RequestQueue queue;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_login, container, false);
        et_login_phone = (EditText)mView.findViewById(R.id.et_login_phone);
        et_login_password = (EditText)mView.findViewById(R.id.et_login_password);
        btn_login = (Button) mView.findViewById(R.id.btn_login);

        FragmentManager fm = getFragmentManager();
        final FragmentTransaction ft = fm.beginTransaction();

        btn_login.setOnClickListener(new View.OnClickListener() {                                      //to log in
            @Override
            public void onClick(View v) {
                phone = et_login_phone.getText().toString();
                password = et_login_password.getText().toString();
                /*
                if(phone.equals(null) || phone.isEmpty()) {
                    Toast.makeText(getActivity(), "用户名不能为空哦！", Toast.LENGTH_SHORT).show();
                    return;
                }else if(password.equals(null) || phone.isEmpty()){
                    Toast.makeText(getActivity(),"密码不能为空哦！",Toast.LENGTH_SHORT).show();;
                    return;
                }*/

                Map<String, String> paras = new HashMap<String, String>();
                paras.put("phone",phone);
                paras.put("password",password);

                CustomRequest req = new CustomRequest(Request.Method.POST, ip+url, paras, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject json) {
                        int code = 0;
                        try {
                            code = json.getInt("code");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Log.d("NET", json.toString());
                        if(code == 100){
                            Intent intent = new Intent(getActivity(),MyActivity.class);
                            startActivity(intent);
                        }else{
                            switch (code) {
                                case 803:
                                    Toast.makeText(getActivity(), "电话号码有误，检查格式", Toast.LENGTH_SHORT).show();
                                    break;
                                case 804:
                                    Toast.makeText(getActivity(), "理发师尚未注册", Toast.LENGTH_SHORT).show();
                                    break;
                                case 805:
                                    Toast.makeText(getActivity(), "密码不匹配，登陆失败", Toast.LENGTH_SHORT).show();
                                    break;
                            }
                        }
                    }
                }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError volleyError) {
                            Log.d("NET",volleyError.toString());
                        }
                    });
                queue = Volley.newRequestQueue(getActivity());
                queue.add(req);
            }
        });

        return mView;
    }
}
