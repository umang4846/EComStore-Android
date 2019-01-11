package com.appprocessors.ecomstore.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Trending {

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

}
