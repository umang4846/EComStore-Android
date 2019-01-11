package com.appprocessors.ecomstore.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ColorVariant implements Parcelable {

    @SerializedName("key")
    @Expose
    private String key;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.key);
    }

    public ColorVariant() {
    }

    protected ColorVariant(Parcel in) {
        this.key = in.readString();
    }

    public static final Parcelable.Creator<ColorVariant> CREATOR = new Parcelable.Creator<ColorVariant>() {
        @Override
        public ColorVariant createFromParcel(Parcel source) {
            return new ColorVariant(source);
        }

        @Override
        public ColorVariant[] newArray(int size) {
            return new ColorVariant[size];
        }
    };
}
