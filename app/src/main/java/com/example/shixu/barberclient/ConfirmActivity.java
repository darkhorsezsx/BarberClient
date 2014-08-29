package com.example.shixu.barberclient;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;

import com.example.shixu.modles.OrderAccept;

/**
 * Created by shixu on 2014/8/28.
 */
public class ConfirmActivity extends Activity{

    String name;
    String phone;
    String sex;
    String distance;
    String orderID;
    String hair;
    String time;
    String remark;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.neworder_confirm);
        //  add data
        Intent intent = getIntent();
        OrderAccept orderAccept = intent.getParcelableExtra("order");
        name = orderAccept.getName();
        orderID = orderAccept.getOrderID();
        phone = orderAccept.getPhone();
        sex = orderAccept.getSex();
        hair = orderAccept.getHair();
        time = orderAccept.getTime();
        distance = Double.toHexString(orderAccept.getDistance());
        remark = orderAccept.getRemark();




        if(distance.equals("0"))
        {//       normal request
            Bundle bundle = new Bundle();
            bundle.putString("oederID",orderID);
            bundle.putString("name",name);
            bundle.putString("phone",phone);
            bundle.putString("sex",sex);
            bundle.putString("hair",hair);
            bundle.putString("time",time);
            bundle.putString("remark",remark);
            bundle.putBoolean("isnormal",true);
            FragmentOrderSuccess fragmentOrderSuccess = new FragmentOrderSuccess();
            fragmentOrderSuccess.setArguments(bundle);
            FragmentManager fm = getFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.add(R.id.confirm_container,fragmentOrderSuccess).commit();
        }
        else
        {//        quick request
            Bundle bundle = new Bundle();
            bundle.putString("oederID",orderID);
            bundle.putString("name",name);
            bundle.putString("phone",phone);
            bundle.putString("sex",sex);
            bundle.putString("distance",distance);
            bundle.putBoolean("isnormal",false);
            FragmentNewOrder fragmentNewOrder = new FragmentNewOrder();
            fragmentNewOrder.setArguments(bundle);
            FragmentManager fm = getFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.add(R.id.confirm_container,fragmentNewOrder).commit();
        }

    }
}
