package com.appprocessors.ecomstore.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SubDistrict {

    @SerializedName("_id")
    @Expose
    public String id;
    @SerializedName("subDistrict")
    @Expose
    public String subDistrict;
    @SerializedName("villages")
    @Expose
    public List<Village> villages = null;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSubDistrict() {
        return subDistrict;
    }

    public void setSubDistrict(String subDistrict) {
        this.subDistrict = subDistrict;
    }

    public List<Village> getVillages() {
        return villages;
    }

    public void setVillages(List<Village> villages) {
        this.villages = villages;
    }

}
