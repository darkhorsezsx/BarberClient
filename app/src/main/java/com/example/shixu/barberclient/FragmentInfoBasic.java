package com.example.shixu.barberclient;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

/**
 * Created by shixu on 2014/8/18.
 */
public class FragmentInfoBasic extends Fragment {
    private View mView;
    private Button btn_next;
    private EditText et_phone;
    private EditText et_name;
    private EditText et_password;
    private EditText et_password_confirm;
    Spinner spinner;
    String name;
    String phone;
    String password = null;
    String passwordconfirm = null;
    String sex;


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.info_basic, container, false);
        FragmentManager fm = getFragmentManager();
        final FragmentTransaction ft = fm.beginTransaction();
        //set spinner
        spinner = (Spinner) mView.findViewById(R.id.spiner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),R.array.sex_array,android.R.layout.simple_dropdown_item_1line);
        spinner.setAdapter(adapter);
        btn_next = (Button) mView.findViewById(R.id.btn_basicinfo_next);
        et_phone = (EditText) mView.findViewById(R.id.et_user_phone_submit);
        et_name = (EditText) mView.findViewById(R.id.et_user_name_submit);
        et_password = (EditText) mView.findViewById(R.id.et_password_submit);
        et_password_confirm = (EditText) mView.findViewById(R.id.et_password_confirm);



        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = et_name.getText().toString();

                phone = et_phone.getText().toString();
                password = et_password.getText().toString();
                passwordconfirm = et_password_confirm.getText().toString();
                Log.e("Lod password :", password);
                Log.e("Lod passwordconfirm :",passwordconfirm);
                if (spinner.getSelectedItem().toString().equals("先生"))
                   sex = "Male";
                else
                   sex = "Female";
                if(!checkPassword(password,passwordconfirm))
                {
                    return;
                }
                Bundle bundle = new Bundle();
                bundle.putString("name",name);
                bundle.putString("phone",phone);
                bundle.putString("password",password);
                bundle.putString("sex",sex);
                FragmentInfoShop fragmentInfoShop = new FragmentInfoShop();
                fragmentInfoShop.setArguments(bundle);
                ft.replace(R.id.register_container,fragmentInfoShop).addToBackStack(null).commit();
            }
        });



        return mView;
    }
    boolean checkPassword(String password,String passwordconfirm)
    {
        Log.d("Lod password :", password);
        Log.d("Lod passwordconfirm :",passwordconfirm);

        if(!password.equals(passwordconfirm))
        {
            Toast.makeText(getActivity(),"密码输入不一致",Toast.LENGTH_SHORT).show();
            return false;
        }
        if(passwordconfirm == null)
        {
            Toast.makeText(getActivity(),"密码不能为空",Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}
