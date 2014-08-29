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
import android.widget.TextView;

/**
 * Created by shixu on 2014/8/28.
 */
public class FragmentNewOrder extends Fragment{
    View mView;
    TextView tv_name;
    TextView tv_phone;
    TextView tv_sex;
    TextView tv_distance;
    Button btn_sure;
    Button btn_cancle;
    String name;
    String phone;
    String sex;
    String distance;
    String orderID;
    Boolean isNormal;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.neworder_confirm, container, false);
        tv_name = (TextView)  mView.findViewById(R.id.tv_quick_cusname);
        tv_phone = (TextView) mView.findViewById(R.id.tv_phone);
        tv_sex = (TextView) mView.findViewById(R.id.tv_sex);
        tv_distance = (TextView) mView.findViewById(R.id.tv_distance);
        btn_sure = (Button) mView.findViewById(R.id.btn_quick_receive);
        btn_cancle = (Button) mView.findViewById(R.id.btn_quick_reject);

        Bundle bundleget = getArguments();
        name = bundleget.getString("name");
        phone = bundleget.getString("phone");
        sex = bundleget.getString("sex");
        distance = bundleget.getString("distance");
        orderID = bundleget.getString("orderID");
        isNormal = bundleget.getBoolean("isnormal");

        tv_name.setText(name);
        tv_phone.setText(phone);
        tv_distance.setText(distance);
        tv_sex.setText(sex);

        btn_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("oederID",orderID);
                bundle.putString("name",name);
                bundle.putString("phone",phone);
                bundle.putString("sex",sex);
                bundle.putString("distance",distance);
                bundle.putBoolean("isnormal",isNormal);
                ProgressBarFragment progressBarFragment = new ProgressBarFragment();
                progressBarFragment.setArguments(bundle);
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.confirm_container,progressBarFragment).addToBackStack(null).commit();
            }
        });
        btn_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),MyActivity.class);
                startActivity(intent);
            }
        });

        return mView;
    }
}
