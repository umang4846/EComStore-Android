package com.appprocessors.ecomstore.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Order {

    @SerializedName("_id")
    @Expose
    private String _id;
    @SerializedName("productName")
    @Expose
    private String productName;
    @SerializedName("productCode")
    @Expose
    private String productCode;
    @SerializedName("productPrice")
    @Expose
    private String productPrice;
    @SerializedName("shippingFee")
    @Expose
    private String shippingFee;
    @SerializedName("sellerName")
    @Expose
    private String sellerName;
    @SerializedName("productQuanity")
    @Expose
    private String productQuanity;
    @SerializedName("totalAmount")
    @Expose
    private String totalAmount;
    @SerializedName("paymentMode")
    @Expose
    private String paymentMode;
    @SerializedName("address")
    @Expose
    private Address address;
    @SerializedName("orderedAccountMobileNo")
    @Expose
    private String orderedAccountMobileNo;
    @SerializedName("orderDateTime")
    @Expose
    private String orderDateTime;
    @SerializedName("orderStatus")
    @Expose
    private String orderStatus;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
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

    public String getSellerName() {
        return sellerName;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
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

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
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

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }
}
