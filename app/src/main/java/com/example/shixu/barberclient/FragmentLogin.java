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
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;

/**
 * Created by shixu on 2014/8/18.
 */
public class FragmentLogin extends Fragment {
    private View mView;
    private Button btn_login;
    private Button btn_register;
    private EditText et_login_phone;
    private EditText et_login_password;
    String userID;
    String password;
    String ip = null;
    String url;
    RequestQueue queue;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_login, container, false);
        et_login_phone = (EditText)mView.findViewById(R.id.et_login_phone);
        et_login_password = (EditText)mView.findViewById(R.id.et_login_password);


        FragmentManager fm = getFragmentManager();
        final FragmentTransaction ft = fm.beginTransaction();
        btn_register = (Button) mView.findViewById(R.id.btn_register);
        btn_login = (Button) mView.findViewById(R.id.btn_login);
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {                                                       //to register
                FragmentInfoBasic fragmentInfoBasic = new FragmentInfoBasic();
                ft.replace(R.id.register_container,fragmentInfoBasic).addToBackStack(null).commit();
            }
        });
        btn_login.setOnClickListener(new View.OnClickListener() {                                      //to log in
            @Override
            public void onClick(View v) {
                userID = et_login_phone.getText().toString();
                password = et_login_password.getText().toString();
                if(userID.equals(null)) {
                    Toast.makeText(getActivity(), "用户名不能为空哦！", Toast.LENGTH_SHORT).show();
                    return;
                }else if(password.equals(null)){
                    Toast.makeText(getActivity(),"密码不能为空哦！",Toast.LENGTH_SHORT).show();;
                    return;
                }
                /*
                Map<String, String> paras = new HashMap<String, String>();
                paras.put("userID",userID);
                paras.put("password",password);
                CustomRequest req = new CustomRequest(Request.Method.POST, ip+url, paras, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject json) {
                        Intent intent = new Intent(getActivity(),MyActivity.class);
                        startActivity(intent);

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                    }
                });
                queue = Volley.newRequestQueue(getActivity());
                queue.add(req);
                */
                Intent intent = new Intent(getActivity(),MyActivity.class);
                startActivity(intent);
            }
        });

        return mView;
    }
}
