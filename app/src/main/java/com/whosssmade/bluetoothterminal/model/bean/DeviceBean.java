/*
package com.whosssmade.bluetoothterminal.model.bean;

import android.os.Parcel;
import android.os.Parcelable;

*/
/**
 * Created by Administrator on 2019/5/24.
 *//*


public class DeviceBean implements Parcelable {
    private String name;
    private String mac;

    public DeviceBean(String name, String mac) {
        this.name = name;
        this.mac = mac;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.mac);
    }

    protected DeviceBean(Parcel in) {
        this.name = in.readString();
        this.mac = in.readString();
    }

    public static final Creator<DeviceBean> CREATOR = new Creator<DeviceBean>() {
        @Override
        public DeviceBean createFromParcel(Parcel source) {
            return new DeviceBean(source);
        }

        @Override
        public DeviceBean[] newArray(int size) {
            return new DeviceBean[size];
        }
    };
}
*/
