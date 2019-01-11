package com.appprocessors.ecomstore.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public  class CategoryBanner implements Parcelable {

    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("link")
    @Expose
    private String link;
    @SerializedName("menuid")
    @Expose
    private String menuid;
    @SerializedName("productCode")
    @Expose
    private String productCode;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.name);
        dest.writeString(this.link);
        dest.writeString(this.menuid);
        dest.writeString(this.productCode);
    }

    public CategoryBanner() {
    }

    protected CategoryBanner(Parcel in) {
        this.id = in.readString();
        this.name = in.readString();
        this.link = in.readString();
        this.menuid = in.readString();
        this.productCode = in.readString();
    }

    public static final Parcelable.Creator<CategoryBanner> CREATOR = new Parcelable.Creator<CategoryBanner>() {
        @Override
        public CategoryBanner createFromParcel(Parcel source) {
            return new CategoryBanner(source);
        }

        @Override
        public CategoryBanner[] newArray(int size) {
            return new CategoryBanner[size];
        }
    };
}

