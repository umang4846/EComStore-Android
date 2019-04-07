package com.appprocessors.ecomstore.model.customer;

import com.google.gson.annotations.SerializedName;

import org.joda.time.DateTime;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class ShippingAddress {

    @SerializedName("createdOnUtc")
    private String createdOnUtc;

    @SerializedName("customAttributes")
    private String customAttributes;

    @SerializedName("faxNumber")
    private String faxNumber;

    @SerializedName("phoneNumber")
    private String phoneNumber;
    @SerializedName("zipPostalCode")
    private String zipPostalCode;

    @SerializedName("address2")
    private String address2;

    @SerializedName("vatNumber")
    private String vatNumber;

    @SerializedName("address1")
    private String address1;

    @SerializedName("city")
    private String city;

    @SerializedName("stateProvinceId")
    private String stateProvinceId;

    @SerializedName("countryId")
    private String countryId;

    @SerializedName("company")
    private String company;

    @SerializedName("email")
    private String email;

    @SerializedName("lastName")
    private String lastName;

    @SerializedName("firstName")
    private String firstName;

    @SerializedName("genericAttributes")
    private List<String> genericAttributes;

    @SerializedName("_id")
    private String _id;


    public String get_id() {
        return _id;
    }


}
