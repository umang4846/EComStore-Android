package com.appprocessors.ecomstore.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public  class SubCategoryProducts implements Parcelable {

    @Expose
    @SerializedName("_id")
    public String _id;
    @Expose
    @SerializedName("name")
    public String name;
    @Expose
    @SerializedName("link")
    public String link;
    @Expose
    @SerializedName("menuid")
    public String menuid;

    public SubCategoryProducts(String _id, String name, String link, String menuid) {
        this._id = _id;
        this.name = name;
        this.link = link;
        this.menuid = menuid;
    }

    public SubCategoryProducts() {
    }

    protected SubCategoryProducts(Parcel in) {
        _id = in.readString();
        name = in.readString();
        link = in.readString();
        menuid = in.readString();
    }

    public static final Creator<SubCategoryProducts> CREATOR = new Creator<SubCategoryProducts>() {
        @Override
        public SubCategoryProducts createFromParcel(Parcel in) {
            return new SubCategoryProducts(in);
        }

        @Override
        public SubCategoryProducts[] newArray(int size) {
            return new SubCategoryProducts[size];
        }
    };

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getMenuid() {
        return menuid;
    }

    public void setMenuid(String menuid) {
        this.menuid = menuid;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(_id);
        dest.writeString(name);
        dest.writeString(link);
        dest.writeString(menuid);
    }
}