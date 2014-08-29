package com.example.shixu.controller;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.shixu.barberclient.R;
import com.example.shixu.modles.Shop;

import it.gmariotti.cardslib.library.internal.Card;

/**
 * Created by Jiaqi Ning on 2014/8/28.
 */
public class ShopCard extends Card {
    Shop mShop;
    TextView name;
    TextView address;
    public ShopCard(Context context,Shop shop) {
        super(context, R.layout.barbershop_card);
        mShop = shop;
    }

    @Override
    public void setupInnerViewElements(ViewGroup parent, View view) {

        name = (TextView)parent.findViewById(R.id.tv_shopname);
        address = (TextView)parent.findViewById(R.id.tv_address);

        name.setText(mShop.getName());
        name.setText(mShop.getAddress());
    }
}
