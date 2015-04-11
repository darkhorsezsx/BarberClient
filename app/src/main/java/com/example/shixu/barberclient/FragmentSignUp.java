package com.example.shixu.barberclient;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.example.shixu.controller.CustomRequest;
import com.example.shixu.controller.LocationUtil;
import com.example.shixu.controller.VollyErrorHelper;
import com.example.shixu.modles.UrlConstance;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

/**
 * Created by shixu on 2014/8/18.
 */
public class FragmentSignUp extends Fragment {
    private View mView;
    private Button btn_next;
    private EditText et_phone;
    private EditText et_name;
    private EditText et_password;
    private EditText et_password_confirm;
    Spinner spinner;
    String name;
    String phone;
    String password = null;
    String passwordconfirm = null;
    String sex;
    double longitude;
    double latitude;
    boolean isFirst = true;
    String url = UrlConstance.IP + UrlConstance.IS_REGISTER;
    RequestQueue mRequestQueue;


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.info_basic, container, false);
        FragmentManager fm = getFragmentManager();
        final FragmentTransaction ft = fm.beginTransaction();
        mRequestQueue = Volley.newRequestQueue(getActivity());

        //set spinner
        spinner = (Spinner) mView.findViewById(R.id.spiner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),R.array.sex_array,android.R.layout.simple_dropdown_item_1line);
        spinner.setAdapter(adapter);
        btn_next = (Button) mView.findViewById(R.id.btn_basicinfo_next);
        et_phone = (EditText) mView.findViewById(R.id.et_user_phone_submit);
        et_name = (EditText) mView.findViewById(R.id.et_user_name_submit);
        et_password = (EditText) mView.findViewById(R.id.et_password_submit);
        et_password_confirm = (EditText) mView.findViewById(R.id.et_password_confirm);

         latitude = LocationUtil.getLatitude(getActivity());
         longitude = LocationUtil.getLongitude(getActivity());

        btn_next.setEnabled(false);


        et_phone.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (isFirst){
                    isFirst = true;
                    return;
                }
                phone = et_phone.getText().toString();
                if (isPhoneValid(phone)) {
                    getActivity().setProgressBarIndeterminateVisibility(true);
                    Map<String, String> para = new HashMap<String, String>();
                    para.put("phone", phone);
                    CustomRequest req = new CustomRequest(Request.Method.POST, url, para, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject json) {
                            try {
                                int code = json.getInt("code");
                                if (code == 504) {
                                    Toast.makeText(getActivity(), "电话号码已注册,请登录or换一个号码", Toast.LENGTH_LONG).show();
                                    et_phone.setText("");
                                } else if (code == 100){
                                    setJpushAlias(phone);
                                    Toast.makeText(getActivity(),"注册成功",Toast.LENGTH_LONG).show();
                                    getActivity().finish();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            } finally {
                                getActivity().setProgressBarIndeterminateVisibility(false);
                            }
                            isFirst = true;

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError volleyError) {
                            String errorMsg = VollyErrorHelper.getMessage(volleyError);
                            Toast.makeText(getActivity(), errorMsg, Toast.LENGTH_LONG).show();
                            getActivity().setProgressBarIndeterminateVisibility(false);
                        }
                    });
                    mRequestQueue.add(req);
                }else {
                    Toast.makeText(getActivity(),"不正确的号码",Toast.LENGTH_LONG).show();
                    et_phone.setText("");
                }
            }
        });

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = et_name.getText().toString();

                phone = et_phone.getText().toString();
                password = et_password.getText().toString();
                passwordconfirm = et_password_confirm.getText().toString();

                if (spinner.getSelectedItem().toString().equals("先生"))
                    sex = "Male";
                else
                    sex = "Female";
                if(!checkPassword(password,passwordconfirm))
                    return;

                Bundle bundle = new Bundle();
                bundle.putString("name",name);
                bundle.putString("phone",phone);
                bundle.putString("password",password);
                bundle.putString("sex",sex);
                bundle.putDouble("longitude",longitude);
                bundle.putDouble("latitude",latitude);
                FragmentInfoShop fragmentInfoShop = new FragmentInfoShop();
                fragmentInfoShop.setArguments(bundle);
                ft.replace(R.id.login_container,fragmentInfoShop).addToBackStack(null).commit();
            }
        });
        et_password.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (isFirst){
                    Toast.makeText(getActivity(),"请注意密码必须有8位以上,且包含字母数字",Toast.LENGTH_SHORT).show();
                    isFirst = false;
                }
                int code = isPasswordValid(et_password.getText().toString());
                switch (code){
                    case 0:
                        btn_next.setEnabled(true);
                        break;
                    case 1:
                        Toast.makeText(getActivity(), "密码至少8位", Toast.LENGTH_LONG).show();
                        et_password.setText("");
                        break;
                    case 2:
                        Toast.makeText(getActivity(),"密码太简单,必须包含字母和数字",Toast.LENGTH_LONG).show();
                        et_password.setText("");
                        break;
                }
            }
        });



        return mView;
    }

    private boolean isPhoneValid(String phone){
        if (phone.length() != 11)
            return false;
        String[] prefix  = {"130", "131", "132",
                "133", "134", "135",
                "136", "137", "138",
                "139", "150", "151",
                "152", "153", "155",
                "156", "157", "158",
                "159", "170", "180",
                "181", "182", "183",
                "184", "185", "186",
                "187", "188", "189"};
        for (String pre : prefix){
            if (phone.startsWith(pre))
                return true;
        }
        return false;
    }

    /** Check the password validation : length longer than 8 ,must contain number and character
     * return 0 mean the password is valid
     * return 1 mean password too short
     * return 2 mean password too weak */
    private int isPasswordValid(String password){
        if (password.length() < 8)
            return 1;

        boolean containNumber = false;
        boolean containChar = false;
        for (int i = 0;i < password.length();i++){
            char c = password.charAt(i);
            if(((c>='a'&&c<='z')   ||   (c>='A'&&c<='Z')))
                containChar = true;
            if (Character.isDigit(c))
                containNumber = true;
            if (containChar && containNumber)
                return 0;
        }


        return 2;
    }
    boolean checkPassword(String password,String passwordconfirm)
    {
        if(!password.equals(passwordconfirm)) {
            Toast.makeText(getActivity(),"密码输入不一致",Toast.LENGTH_SHORT).show();
            return false;
        }
        if(passwordconfirm == null || passwordconfirm.isEmpty()) {
            Toast.makeText(getActivity(),"密码不能为空",Toast.LENGTH_SHORT).show();
            return false;
        }


        return true;
    }

    private void setJpushAlias(String phone){
        JPushInterface.setAlias(getActivity(), phone, new TagAliasCallback() {
            @Override
            public void gotResult(int code, String alias, Set<String> tags) {
                if (code == 60020) {
                    Toast.makeText(getActivity(),"Jpush alias set error",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    void test(){
        et_phone.setText("18558701653");
        et_name.setText("宁嘉琦2");
        et_password.setText("Wednesday114");
        et_password_confirm.setText("Wednesday114");
        latitude = 43.819627;
        longitude = 125.277334;
    }


}
