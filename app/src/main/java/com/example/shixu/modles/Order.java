package com.example.shixu.modles;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Jiaqi Ning on 2014/8/31.
 */
public class Order implements Parcelable {
    String orderID;
    String cusphone;
    String cusname;
    String sex;

    public String getOrderID() {
        return orderID;
    }

    public String getCusphone() {
        return cusphone;
    }

    public String getCusname() {
        return cusname;
    }

    public String getSex() {
        return sex;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.orderID);
        dest.writeString(this.cusphone);
        dest.writeString(this.cusname);
        dest.writeString(this.sex);
    }

    public Order() {
    }

    private Order(Parcel in) {
        this.orderID = in.readString();
        this.cusphone = in.readString();
        this.cusname = in.readString();
        this.sex = in.readString();
    }

}
