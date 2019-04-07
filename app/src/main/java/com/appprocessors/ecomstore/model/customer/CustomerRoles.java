package com.appprocessors.ecomstore.model.customer;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CustomerRoles {

    @SerializedName("_id")
    private String _id;

    @SerializedName("genericAttributes")
    private List<GenericAttributes> genericAttributes;

    @SerializedName("name")
    private String name;

    @SerializedName("freeShipping")
    private boolean freeShipping;

    @SerializedName("taxExempt")
    private boolean taxExempt;

    @SerializedName("active")
    private boolean active;

    @SerializedName("isSystemRole")
    private boolean isSystemRole;

    @SerializedName("systemName")
    private String systemName;

    @SerializedName("enablePasswordLifetime")
    private boolean enablePasswordLifetime;

    @SerializedName("purchasedWithProductId")
    private String purchasedWithProductId;

    public String get_id() {
        return _id;
    }

}
