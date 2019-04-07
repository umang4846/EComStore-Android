package com.appprocessors.ecomstore.model.customer;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class Customer {

    @SerializedName("_id")
    private String _id;

    @SerializedName("genericAttributes")
    private List<GenericAttributes> GenericAttributes = new ArrayList<>();

    @SerializedName("customerGuid")
    private String customerGuid;

    @SerializedName("username")
    private String username;

    @SerializedName("email")
    private String email;

    @SerializedName("password")
    private String password;

    @SerializedName("passwordFormatId")
    private int passwordFormatId;

    @SerializedName("passwordSalt")
    private String passwordSalt;

    @SerializedName("adminComment")
    private String adminComment;

    @SerializedName("isTaxExempt")
    private boolean isTaxExempt;

    @SerializedName("freeShipping")
    private boolean freeShipping;

    @SerializedName("affiliateId")
    private String affiliateId;

    @SerializedName("vendorId")
    private String vendorId;

    @SerializedName("storeId")
    private String storeId;

    @SerializedName("active")
    private boolean active;

    @SerializedName("deleted")
    private boolean deleted;

    @SerializedName("isSystemAccount")
    private boolean isSystemAccount;

    @SerializedName("hasContributions")
    private boolean hasContributions;

    @SerializedName("failedLoginAttempts")
    private int failedLoginAttempts;

    @SerializedName("cannotLoginUntilDateUtc")
    private String cannotLoginUntilDateUtc;

    @SerializedName("systemName")
    private String systemName;

    @SerializedName("lastIpAddress")
    private String lastIpAddress;

    @SerializedName("urlReferrer")
    private String urlReferrer;

    @SerializedName("createdOnUtc")
    private String createdOnUtc;

    @SerializedName("lastLoginDateUtc")
    private String lastLoginDateUtc;

    @SerializedName("lastActivityDateUtc")
    private String lastActivityDateUtc;

    @SerializedName("lastPurchaseDateUtc")
    private String lastPurchaseDateUtc;

    @SerializedName("lastUpdateCartDateUtc")
    private String lastUpdateCartDateUtc;

    @SerializedName("lastUpdateWishListDateUtc")
    private String lastUpdateWishListDateUtc;

    @SerializedName("passwordChangeDateUtc")
    private String passwordChangeDateUtc;

    @SerializedName("customerRoles")
    private List<CustomerRoles> customerRoles;

    @SerializedName("shoppingCartItems")
    private List<ShoppingCartItems> shoppingCartItems;

    @SerializedName("billingAddress")
    private BillingAddress billingAddress;

    @SerializedName("shippingAddress")
    private ShippingAddress shippingAddress;

    @SerializedName("addresses")
    private List<Addresses> addresses ;

    @SerializedName("customerTags")
    private List<String> customerTags;

    public String getCreatedOnUtc() {
        return String.valueOf(createdOnUtc);
    }

    public String getLastLoginDateUtc() {
        return String.valueOf(lastLoginDateUtc);
    }

    public String getLastActivityDateUtc() {
        return String.valueOf(lastActivityDateUtc);
    }

    public String getLastPurchaseDateUtc() {
        return String.valueOf(lastPurchaseDateUtc);
    }

    public String getLastUpdateCartDateUtc() {
        return String.valueOf(lastUpdateCartDateUtc);
    }

    public String getLastUpdateWishListDateUtc() {
        return String.valueOf(lastUpdateWishListDateUtc);
    }

    public String getPasswordChangeDateUtc() {
        return String.valueOf(passwordChangeDateUtc);
    }


    public String get_id() {
        return _id;
    }
}
