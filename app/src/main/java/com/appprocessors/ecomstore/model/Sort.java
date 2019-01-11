package com.appprocessors.ecomstore.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Sort implements Parcelable {

    @SerializedName("sorted")
    @Expose
    public Boolean sorted;
    @SerializedName("unsorted")
    @Expose
    public Boolean unsorted;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.sorted);
        dest.writeValue(this.unsorted);
    }

    public Sort() {
    }

    protected Sort(Parcel in) {
        this.sorted = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.unsorted = (Boolean) in.readValue(Boolean.class.getClassLoader());
    }

    public static final Parcelable.Creator<Sort> CREATOR = new Parcelable.Creator<Sort>() {
        @Override
        public Sort createFromParcel(Parcel source) {
            return new Sort(source);
        }

        @Override
        public Sort[] newArray(int size) {
            return new Sort[size];
        }
    };
}
