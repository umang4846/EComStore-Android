package com.appprocessors.ecomstore.model.customer;

import com.google.gson.annotations.SerializedName;

import org.joda.time.DateTime;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class ShoppingCartItems {

    @SerializedName("_id")
    private String _id;
    @SerializedName("storeId")
    private String storeId;
    @SerializedName("shoppingCartTypeId")
    private int shoppingCartTypeId;
    @SerializedName("productId")
    private String productId;
    @SerializedName("attributesXml")
    private String attributesXml;
    @SerializedName("customerEnteredPrice")
    private double customerEnteredPrice;
    @SerializedName("quantity")
    private int quantity;
    @SerializedName("rentalStartDateUtc")
    private String rentalStartDateUtc;
    @SerializedName("rentalEndDateUtc")
    private String rentalEndDateUtc;
    @SerializedName("createdOnUtc")
    private String createdOnUtc;
    @SerializedName("updatedOnUtc")
    private String updatedOnUtc;
    @SerializedName("isFreeShipping")
    private boolean isFreeShipping;
    @SerializedName("isGiftCard")
    private boolean isGiftCard;
    @SerializedName("isShipEnabled")
    private boolean isShipEnabled;
    @SerializedName("additionalShippingChargeProduct")
    private double additionalShippingChargeProduct;
    @SerializedName("isTaxExempt")
    private boolean isTaxExempt;
    @SerializedName("isRecurring")
    private boolean isRecurring;
    @SerializedName("reservationId")
    private String reservationId;
    @SerializedName("parameter")
    private String parameter;
    @SerializedName("duration")
    private String duration;

    public String get_id() {
        return _id;
    }



}
