package com.example.shixu.barberclient;

import android.app.Fragment;
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
public class FragmentOrderSuccess extends Fragment {
    View mView;
    TextView tv_name;
    TextView tv_phone;
    TextView tv_sex;
    TextView tv_time;
    TextView tv_distance;
    TextView tv_remark;
    TextView tv_quick_distance_show;
    TextView tv_quick_time_show;
    TextView tv_quick_remark_show;
    Button btn_next;
    String name;
    String phone;
    String sex;
    String time;
    String distance;
    String remark;
    String hair;
    String orderID;
    Boolean isNormal = true;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.ordersuccess_confirm, container, false);
        tv_name = (TextView) mView.findViewById(R.id.tv_success_cusname);
        tv_sex = (TextView) mView.findViewById(R.id.tv_success_sex);
        tv_phone = (TextView) mView.findViewById(R.id.tv_success_phone);
        tv_time = (TextView) mView.findViewById(R.id.tv_success_time);
        tv_distance = (TextView) mView.findViewById(R.id.tv_success_distance);
        tv_remark = (TextView) mView.findViewById(R.id.tv_success_remark);
        tv_quick_distance_show = (TextView) mView.findViewById(R.id.tv_quick_distance_show);
        tv_quick_time_show = (TextView) mView.findViewById(R.id.tv_quick_time_show);
        tv_quick_remark_show = (TextView) mView.findViewById(R.id.tv_quick_remark_show);
        btn_next = (Button) mView.findViewById(R.id.btn_success_next);


        Bundle bundle = getArguments();
        isNormal = bundle.getBoolean("isnoemal");
        if(isNormal)
        {            //quick request
            name = bundle.getString("name");
            phone = bundle.getString("phone");
            sex = bundle.getString("sex");
            orderID = bundle.getString("orderID");
            hair = bundle.getString("hair");
            time = bundle.getString("time");
            remark = bundle.getString("remark");

            tv_name.setText(name);
            tv_sex.setText(sex);
            tv_phone.setText(phone);
            tv_time.setText(time);
            tv_remark.setText(remark);
            tv_quick_distance_show.setVisibility(View.GONE);
            tv_distance.setVisibility(View.GONE);
            tv_quick_time_show.setVisibility(View.GONE);
            tv_time.setVisibility(View.GONE);
            tv_quick_remark_show.setVisibility(View.GONE);
            tv_remark.setVisibility(View.GONE);
        }
        else
        {            //normal request
            name = bundle.getString("name");
            phone = bundle.getString("phone");
            sex = bundle.getString("sex");
            orderID = bundle.getString("orderID");
            distance = bundle.getString("distance");

            tv_name.setText(name);
            tv_sex.setText(sex);
            tv_phone.setText(phone);
            tv_distance.setText(distance);

            tv_quick_distance_show.setVisibility(View.GONE);
        }


        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),MyActivity.class);
                startActivity(intent);
            }
        });

        return mView;
    }
}
