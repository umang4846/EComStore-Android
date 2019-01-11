package com.appprocessors.ecomstore.model;


import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TrendingSubCategory implements Parcelable {



    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("productName")
    @Expose
    private String productName;
    @SerializedName("mrp")
    @Expose
    private String mrp;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("imageMain")
    @Expose
    private String imageMain;
    @SerializedName("menuid")
    @Expose
    private String menuid;
    @SerializedName("soldBy")
    @Expose
    private String soldBy;
    @SerializedName("productAverageRating")
    @Expose
    private String productAverageRating;
    @SerializedName("productNoOfRatings")
    @Expose
    private String productNoOfRatings;
    @SerializedName("sellerName")
    @Expose
    private String sellerName;
    @SerializedName("createdDate")
    @Expose
    private String createdDate;
    @SerializedName("productCode")
    @Expose
    private String productCode;

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getMrp() {
        return mrp;
    }

    public void setMrp(String mrp) {
        this.mrp = mrp;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getImageMain() {
        return imageMain;
    }

    public void setImageMain(String imageMain) {
        this.imageMain = imageMain;
    }

    public String getMenuid() {
        return menuid;
    }

    public void setMenuid(String menuid) {
        this.menuid = menuid;
    }

    public String getSoldBy() {
        return soldBy;
    }

    public void setSoldBy(String soldBy) {
        this.soldBy = soldBy;
    }

    public String getProductAverageRating() {
        return productAverageRating;
    }

    public void setProductAverageRating(String productAverageRating) {
        this.productAverageRating = productAverageRating;
    }

    public String getProductNoOfRatings() {
        return productNoOfRatings;
    }

    public void setProductNoOfRatings(String productNoOfRatings) {
        this.productNoOfRatings = productNoOfRatings;
    }

    public String getSellerName() {
        return sellerName;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.productName);
        dest.writeString(this.mrp);
        dest.writeString(this.price);
        dest.writeString(this.imageMain);
        dest.writeString(this.menuid);
        dest.writeString(this.soldBy);
        dest.writeString(this.productAverageRating);
        dest.writeString(this.productNoOfRatings);
        dest.writeString(this.sellerName);
        dest.writeString(this.createdDate);
        dest.writeString(this.productCode);
    }

    public TrendingSubCategory() {
    }

    protected TrendingSubCategory(Parcel in) {
        this.id = in.readString();
        this.productName = in.readString();
        this.mrp = in.readString();
        this.price = in.readString();
        this.imageMain = in.readString();
        this.menuid = in.readString();
        this.soldBy = in.readString();
        this.productAverageRating = in.readString();
        this.productNoOfRatings = in.readString();
        this.sellerName = in.readString();
        this.createdDate = in.readString();
        this.productCode = in.readString();
    }

    public static final Parcelable.Creator<TrendingSubCategory> CREATOR = new Parcelable.Creator<TrendingSubCategory>() {
        @Override
        public TrendingSubCategory createFromParcel(Parcel source) {
            return new TrendingSubCategory(source);
        }

        @Override
        public TrendingSubCategory[] newArray(int size) {
            return new TrendingSubCategory[size];
        }
    };
}
