package com.example.shixu.controller;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.shixu.barberclient.R;

import it.gmariotti.cardslib.library.internal.Card;

/**
 * Created by shixu on 2014/8/18.
 */

public class HistoryCard extends Card {
    ImageView mIcon;
    TextView mCustomerName;
    TextView mHairStyle;
    TextView mLocation;
    RatingBar mRatingBar;


    public HistoryCard(Context context) {
        super(context, R.layout.history_card);
    }

    public HistoryCard(Context context,int innerLayout){
        super(context,innerLayout);

    }

    @Override
    public void setupInnerViewElements(ViewGroup parent, View view) {
        super.setupInnerViewElements(parent, view);

        mCustomerName = (TextView)parent.findViewById(R.id.tv_history_customer);
        mHairStyle = (TextView)parent.findViewById(R.id.tv_history_hairstyle);
        mLocation = (TextView)parent.findViewById(R.id.tv_history_address);
        mIcon = (ImageView)parent.findViewById(R.id.iv_history_icon);
        mRatingBar = (RatingBar)parent.findViewById(R.id.ratingBar_history);

    }


}
