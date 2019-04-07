package com.appprocessors.ecomstore.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SubDistrict {

    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("subDistrict")
    @Expose
    private String subDistrict;
    @SerializedName("villages")
    @Expose
    private List<Village> villages = new ArrayList<>();


}
