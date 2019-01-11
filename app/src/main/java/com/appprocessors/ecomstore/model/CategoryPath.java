package com.appprocessors.ecomstore.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CategoryPath implements Parcelable {

    @SerializedName("title")
    @Expose
    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
    }

    public CategoryPath() {
    }

    protected CategoryPath(Parcel in) {
        this.title = in.readString();
    }

    public static final Parcelable.Creator<CategoryPath> CREATOR = new Parcelable.Creator<CategoryPath>() {
        @Override
        public CategoryPath createFromParcel(Parcel source) {
            return new CategoryPath(source);
        }

        @Override
        public CategoryPath[] newArray(int size) {
            return new CategoryPath[size];
        }
    };
}
