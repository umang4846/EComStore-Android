package com.appprocessors.ecomstore.model.customer;


import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class GenericAttributes {
    @SerializedName("key")
    private String key;

    @SerializedName("value")
    private String value;

    @SerializedName("storeId")
    private String storeId;



}
