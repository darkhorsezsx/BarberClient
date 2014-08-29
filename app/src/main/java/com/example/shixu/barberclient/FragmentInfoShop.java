package com.example.shixu.barberclient;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.example.shixu.controller.CustomRequest;
import com.example.shixu.controller.ShopCard;
import com.example.shixu.modles.Shop;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardArrayAdapter;
import it.gmariotti.cardslib.library.view.CardListView;

/**
 * Created by shixu on 2014/8/18.
 */
public class FragmentInfoShop extends Fragment {
    private View mView;
    String name;
    String phone;
    String password;
    String sex;
    double longitude;
    double latitude;
    RequestQueue queue;
    String url  = "/get-near-shop/";
    String ip = "http://204.152.218.52";
    ArrayList<Card> cards;
    CardListView list;


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.info_shop, container, false);
        list = (CardListView)mView.findViewById(R.id.shop_list);


        Bundle bundle = getArguments();
        name = bundle.getString("name");
        phone = bundle.getString("phone");
        password = bundle.getString("password");
        sex = bundle.getString("sex");
        latitude = bundle.getDouble("latitude");
        longitude = bundle.getDouble("longitude");
        queue = Volley.newRequestQueue(getActivity());                                                                          //request the shop around here
        Map<String, String> paras = new HashMap<String, String>();
        paras.put("latitude",Double.toString(latitude));
        paras.put("longitude",Double.toString(longitude));
        CustomRequest req = new CustomRequest(Request.Method.POST, ip+url, paras, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject json) {
                try {
                    int code = json.getInt("code");
                    if (code == 100){
                        phraseShopList(json.getString("data"));
                        list.setAdapter(new CardArrayAdapter(getActivity(),cards));
                    }else {
                        Log.d("NET",json.toString());
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.d("NET",volleyError.toString());
            }
        });

        queue.add(req);
        return mView;
    }

    void phraseShopList(String str){
        try {
            cards = new ArrayList<Card>();
            JSONArray json = new JSONArray(str);
            for (int i =0;i < json.length();i++){
                Gson gson = new Gson();
                Shop shop = gson.fromJson(String.valueOf(json.getJSONObject(i)),Shop.class);
                Card card = new ShopCard(getActivity(),shop);
                card.setOnClickListener(new Card.OnCardClickListener() {
                    @Override
                    public void onClick(Card card, View view) {
                        TextView tvName = (TextView)view.findViewById(R.id.tv_shopname);
                        String shopName = tvName.getText().toString();

                        FragmentManager fm = getFragmentManager();
                        FragmentTransaction ft = fm.beginTransaction();
                        Bundle bundle = new Bundle();
                        bundle.putString("name",name);
                        bundle.putString("phone",phone);
                        bundle.putString("password",password);
                        bundle.putString("sex",sex);
                        bundle.putString("shop",shopName);
                        Fragment fragment = new SetTimeFragment();
                        fragment.setArguments(bundle);
                        ft.replace(R.id.login_container, fragment).addToBackStack(null).commit();
                    }
                });
                cards.add(card);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
