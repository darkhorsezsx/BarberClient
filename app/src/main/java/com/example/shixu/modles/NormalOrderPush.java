package com.example.shixu.modles;

import android.os.Parcel;

/**
 * Created by Jiaqi Ning on 2014/8/31.
 */
public class NormalOrderPush extends Order implements android.os.Parcelable {

    String hairstyle;
    String time;

    public String getHairstyle() {
        return hairstyle;
    }

    public String getTime() {
        return time;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.hairstyle);
        dest.writeString(this.time);
        dest.writeString(this.orderID);
        dest.writeString(this.cusphone);
        dest.writeString(this.cusname);
        dest.writeString(this.sex);
    }

    public NormalOrderPush() {
    }

    private NormalOrderPush(Parcel in) {
        this.hairstyle = in.readString();
        this.time = in.readString();
        this.orderID = in.readString();
        this.cusphone = in.readString();
        this.cusname = in.readString();
        this.sex = in.readString();
    }

    public static final Creator<NormalOrderPush> CREATOR = new Creator<NormalOrderPush>() {
        public NormalOrderPush createFromParcel(Parcel source) {
            return new NormalOrderPush(source);
        }

        public NormalOrderPush[] newArray(int size) {
            return new NormalOrderPush[size];
        }
    };
}
