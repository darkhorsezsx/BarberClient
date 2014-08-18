package com.example.shixu.barberclient;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

/**
 * Created by shixu on 2014/8/18.
 */
public class FragmentInfoBasic extends Fragment {
    private View mView;
    private Button btn_next;
    Spinner spinner;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.info_basic, container, false);
        FragmentManager fm = getFragmentManager();
        final FragmentTransaction ft = fm.beginTransaction();
        btn_next = (Button) mView.findViewById(R.id.btn_basicinfo_next);
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentInfoShop fragmentInfoShop = new FragmentInfoShop();
                ft.replace(R.id.register_container,fragmentInfoShop).addToBackStack(null).commit();
            }
        });
        //set spinner
        spinner = (Spinner) mView.findViewById(R.id.spiner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),R.array.sex_array,android.R.layout.simple_dropdown_item_1line);
        spinner.setAdapter(adapter);



        return mView;
    }
}
