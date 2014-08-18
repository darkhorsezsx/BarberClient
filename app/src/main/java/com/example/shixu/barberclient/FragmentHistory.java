package com.example.shixu.barberclient;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardArrayAdapter;
import it.gmariotti.cardslib.library.view.CardListView;

/**
 * Created by shixu on 2014/8/18.
 */
public class FragmentHistory extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Context context = getActivity();
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_history, container, false);

        ArrayList<Card> cards = new ArrayList<Card>();
        for (int i =0;i<5;i++) {
            HistoryCard card= new HistoryCard(context,R.layout.history_card);

            cards.add(card);
        }
        CardArrayAdapter mCardArrayAdapter = new CardArrayAdapter(getActivity(),cards);

        CardListView listView = (CardListView) view.findViewById(R.id.history_list);

        listView.setAdapter(mCardArrayAdapter);
        Log.d("test", "adapter set");


        return view;
    }

}
