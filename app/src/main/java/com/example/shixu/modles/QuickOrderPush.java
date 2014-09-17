package com.example.shixu.modles;

import android.os.Parcel;

/**
 * Created by Jiaqi Ning on 2014/8/31.
 */
public class QuickOrderPush extends Order implements android.os.Parcelable {
    String distance;

    public String getDistance() {
        return distance;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.distance);
        dest.writeString(this.orderID);
        dest.writeString(this.cusphone);
        dest.writeString(this.cusname);
        dest.writeString(this.sex);
    }

    public QuickOrderPush() {
    }

    private QuickOrderPush(Parcel in) {
        this.distance = in.readString();
        this.orderID = in.readString();
        this.cusphone = in.readString();
        this.cusname = in.readString();
        this.sex = in.readString();
    }

    public static final Creator<QuickOrderPush> CREATOR = new Creator<QuickOrderPush>() {
        public QuickOrderPush createFromParcel(Parcel source) {
            return new QuickOrderPush(source);
        }

        public QuickOrderPush[] newArray(int size) {
            return new QuickOrderPush[size];
        }
    };
}
