package com.example.shixu.modles;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Jiaqi Ning on 2014/7/30.
 */
public class OrderAccept implements Parcelable {
    String orderID;
    String phone;
    String name;
    String sex;
    double distance;
    String time;
    String hair;
    String remark;



    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setName(String name){this.name = name;}

    public String getName(){return name;}

    public void setSex(String sex){this.sex = sex;}

    public String getSex(){return sex;}

    public void setHair(String hair){this.hair = hair;}

    public String getHair(){return hair;}

    public void setRemark(String appendix){this.remark = remark;}

    public String getRemark(){return remark;}

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.orderID);
        dest.writeString(this.phone);
        dest.writeDouble(this.distance);
        dest.writeString(this.time);
        dest.writeString(this.name);
        dest.writeString(this.sex);
        dest.writeString(this.remark);
        dest.writeString(this.hair);
    }

    public OrderAccept() {
    }

    private OrderAccept(Parcel in) {
        this.orderID = in.readString();
        this.phone = in.readString();
        this.distance = in.readDouble();
        this.time = in.readString();
        this.name = in.readString();
        this.sex = in.readString();
        this.hair = in.readString();
        this.remark = in.readString();
    }

    public static final Creator<OrderAccept> CREATOR = new Creator<OrderAccept>() {
        public OrderAccept createFromParcel(Parcel source) {
            return new OrderAccept(source);
        }

        public OrderAccept[] newArray(int size) {
            return new OrderAccept[size];
        }
    };
}
