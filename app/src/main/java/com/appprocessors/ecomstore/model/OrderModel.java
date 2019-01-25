package com.appprocessors.ecomstore.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OrderModel {

    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("productName")
    @Expose
    private String productName;
    @SerializedName("productPrice")
    @Expose
    private String productPrice;
    @SerializedName("shippingFee")
    @Expose
    private String shippingFee;
    @SerializedName("productQuanity")
    @Expose
    private String productQuanity;
    @SerializedName("totalAmount")
    @Expose
    private String totalAmount;
    @SerializedName("paymentMode")
    @Expose
    private String paymentMode;
    @SerializedName("productDetails")
    @Expose
    private Object productDetails;
    @SerializedName("deliveryAddress")
    @Expose
    private Object deliveryAddress;
    @SerializedName("orderedAccountMobileNo")
    @Expose
    private String orderedAccountMobileNo;
    @SerializedName("orderDateTime")
    @Expose
    private String orderDateTime;

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

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public String getShippingFee() {
        return shippingFee;
    }

    public void setShippingFee(String shippingFee) {
        this.shippingFee = shippingFee;
    }

    public String getProductQuanity() {
        return productQuanity;
    }

    public void setProductQuanity(String productQuanity) {
        this.productQuanity = productQuanity;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(String paymentMode) {
        this.paymentMode = paymentMode;
    }

    public Object getProductDetails() {
        return productDetails;
    }

    public void setProductDetails(Object productDetails) {
        this.productDetails = productDetails;
    }

    public Object getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(Object deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public String getOrderedAccountMobileNo() {
        return orderedAccountMobileNo;
    }

    public void setOrderedAccountMobileNo(String orderedAccountMobileNo) {
        this.orderedAccountMobileNo = orderedAccountMobileNo;
    }

    public String getOrderDateTime() {
        return orderDateTime;
    }

    public void setOrderDateTime(String orderDateTime) {
        this.orderDateTime = orderDateTime;
    }
}
