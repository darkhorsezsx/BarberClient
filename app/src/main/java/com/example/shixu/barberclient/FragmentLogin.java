package com.example.shixu.barberclient;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
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
 * Created by shixu on 2014/8/18.
 */
public class FragmentLogin extends Fragment {
    private View mView;
    private Button btn_login;
    private EditText et_login_phone;
    private EditText et_login_password;
    private ViewGroup editLayout;
    private ProgressWheel mProgressWheel;
    String phone;
    String password;

    String url = UrlConstance.IP+UrlConstance.LOGIN;
    RequestQueue queue;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_login, container, false);
        et_login_phone = (EditText)mView.findViewById(R.id.et_login_phone);
        et_login_password = (EditText)mView.findViewById(R.id.et_login_password);
        btn_login = (Button) mView.findViewById(R.id.btn_login);
        editLayout = (RelativeLayout)mView.findViewById(R.id.login_edit_layout);
        mProgressWheel = (ProgressWheel)mView.findViewById(R.id.pw_login);


        btn_login.setOnClickListener(new View.OnClickListener() {                                      //to log in
            @Override
            public void onClick(View v) {
                phone = et_login_phone.getText().toString();
                password = et_login_password.getText().toString();

                if(phone.equals(null) || phone.isEmpty()) {
                    Toast.makeText(getActivity(), "用户名不能为空哦！", Toast.LENGTH_SHORT).show();
                    return;
                }else if(password.equals(null) || phone.isEmpty()){
                    Toast.makeText(getActivity(),"密码不能为空哦！",Toast.LENGTH_SHORT).show();;
                    return;
                }

                editLayout.setVisibility(View.GONE);
                mProgressWheel.setVisibility(View.VISIBLE);
                mProgressWheel.spin();


                Map<String, String> paras = new HashMap<String, String>();
                paras.put("phone",phone);
                paras.put("password",password);

                CustomRequest req = new CustomRequest(Request.Method.POST,url, paras, new Response.Listener<JSONObject>() {
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
                            mProgressWheel.stopSpinning();
                            Toast.makeText(getActivity(),"登陆成功",Toast.LENGTH_LONG).show();
                           // Intent intent = new Intent(getActivity(),MyActivity.class);
                            //startActivity(intent);
                            getActivity().finish();

                        }else if (code == 202){
                            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                            builder.setTitle("您尚未注册！").setMessage("该号码尚未注册，是否跳转注册页面？").setPositiveButton("跳转",new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Intent intent = new Intent(getActivity(), LoginSignUpActivity.class);
                                    intent.putExtra("flag", 0);
                                    startActivity(intent);
                                }
                            }).setNegativeButton("取消",new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    et_login_phone.setText("");
                                    et_login_password.setText("");
                                }
                            }).show();
                        }
                        else{
                            switch (code) {
                                case 803:
                                    Toast.makeText(getActivity(), "电话号码有误，检查格式", Toast.LENGTH_SHORT).show();
                                    editLayout.setVisibility(View.VISIBLE);
                                    mProgressWheel.setVisibility(View.GONE);
                                    break;
                                case 804:
                                    Toast.makeText(getActivity(), "理发师尚未注册", Toast.LENGTH_SHORT).show();
                                    editLayout.setVisibility(View.VISIBLE);
                                    mProgressWheel.setVisibility(View.GONE);
                                    break;
                                case 805:
                                    Toast.makeText(getActivity(), "密码不匹配，登陆失败", Toast.LENGTH_SHORT).show();
                                    editLayout.setVisibility(View.VISIBLE);
                                    mProgressWheel.setVisibility(View.GONE);
                                    break;
                            }
                            mProgressWheel.stopSpinning();
                            mProgressWheel.setVisibility(View.GONE);
                            editLayout.setVisibility(View.VISIBLE);
                        }
                    }
                }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError volleyError) {
                            Log.d("NET",volleyError.toString());
                            Toast.makeText(getActivity(),"出现未知错误，抱歉！",Toast.LENGTH_LONG).show();
                            getActivity().finish();
                        }
                    });
                queue = Volley.newRequestQueue(getActivity());
                queue.add(req);
            }
        });

        return mView;
    }
}
