package com.example.shixu.barberclient;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.shixu.modles.NormalOrderPush;
import com.example.shixu.modles.Order;
import com.example.shixu.modles.QuickOrderPush;


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
        setContentView(R.layout.activity_confirm);
        //  add data
        Intent intent = getIntent();

        if (intent.getStringExtra("type").equals("quick")){
            QuickOrderPush order = intent.getParcelableExtra("order");
            orderID = order.getOrderID();
            name = order.getCusname();
            phone = order.getCusphone();
            sex = order.getSex();
            distance = order.getDistance();

            Log.d("wtf","Confirm activity "+orderID);

            Bundle bundle = new Bundle();
            bundle.putString("orderID",orderID);
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
        }else {
            NormalOrderPush order = intent.getParcelableExtra("order");
            orderID = order.getOrderID();
            name = order.getCusname();
            phone = order.getCusphone();
            sex = order.getSex();
            hair = order.getHairstyle();
            time = order.getTime();

            Bundle bundle = new Bundle();
            bundle.putString("oederID",orderID);
            bundle.putString("name",name);
            bundle.putString("phone",phone);
            bundle.putString("sex",sex);
            bundle.putString("hair",hair);
            bundle.putString("time",time);
            bundle.putBoolean("isnormal",true);
            FragmentOrderSuccess fragmentOrderSuccess = new FragmentOrderSuccess();
            fragmentOrderSuccess.setArguments(bundle);
            FragmentManager fm = getFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.add(R.id.confirm_container,fragmentOrderSuccess).commit();
        }



    }
}
