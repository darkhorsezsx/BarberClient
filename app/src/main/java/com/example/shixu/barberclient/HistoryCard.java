package com.example.shixu.barberclient;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import it.gmariotti.cardslib.library.internal.Card;

/**
 * Created by shixu on 2014/8/18.
 */

public class HistoryCard extends Card {
    ImageView mIcon;
    TextView mBabarName;
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

        mBabarName = (TextView)parent.findViewById(R.id.tv_babaer_name_history);
        mHairStyle = (TextView)parent.findViewById(R.id.tv_hair_style_history);
        mLocation = (TextView)parent.findViewById(R.id.tv_history_address);
        mIcon = (ImageView)parent.findViewById(R.id.iv_history_icon);
        mRatingBar = (RatingBar)parent.findViewById(R.id.ratingBar_history);

    }


}
