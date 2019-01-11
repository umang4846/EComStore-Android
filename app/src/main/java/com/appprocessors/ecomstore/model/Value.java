package com.appprocessors.ecomstore.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Value implements Parcelable {

    @SerializedName("key")
    @Expose
    private String key;
    @SerializedName("value")
    @Expose
    private List<String> value = null;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public List<String> getValue() {
        return value;
    }

    public void setValue(List<String> value) {
        this.value = value;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.key);
        dest.writeStringList(this.value);
    }

    public Value() {
    }

    protected Value(Parcel in) {
        this.key = in.readString();
        this.value = in.createStringArrayList();
    }

    public static final Parcelable.Creator<Value> CREATOR = new Parcelable.Creator<Value>() {
        @Override
        public Value createFromParcel(Parcel source) {
            return new Value(source);
        }

        @Override
        public Value[] newArray(int size) {
            return new Value[size];
        }
    };
}
