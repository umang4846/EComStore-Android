package com.appprocessors.ecomstore.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SpecificationList implements Parcelable {

    @SerializedName("key")
    @Expose
    private String key;
    @SerializedName("values")
    @Expose
    private List<Value> values = null;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public List<Value> getValues() {
        return values;
    }

    public void setValues(List<Value> values) {
        this.values = values;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.key);
        dest.writeList(this.values);
    }

    public SpecificationList() {
    }

    protected SpecificationList(Parcel in) {
        this.key = in.readString();
        this.values = new ArrayList<Value>();
        in.readList(this.values, Value.class.getClassLoader());
    }

    public static final Parcelable.Creator<SpecificationList> CREATOR = new Parcelable.Creator<SpecificationList>() {
        @Override
        public SpecificationList createFromParcel(Parcel source) {
            return new SpecificationList(source);
        }

        @Override
        public SpecificationList[] newArray(int size) {
            return new SpecificationList[size];
        }
    };
}
