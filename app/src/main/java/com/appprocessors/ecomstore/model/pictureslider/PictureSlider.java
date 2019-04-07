package com.appprocessors.ecomstore.model.pictureslider;

import com.appprocessors.ecomstore.model.customer.GenericAttributes;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PictureSlider {

    @SerializedName("_id")
    private String _id;
    @SerializedName("genericAttributes")
    private List<GenericAttributes> genericAttributes ;
    @SerializedName("pictureId")
    private String pictureId;
    @SerializedName("name")
    private String name;
    @SerializedName("description")
    private String description;
    @SerializedName("link")
    private String link;
    @SerializedName("displayOrder")
    private long displayOrder;
    @SerializedName("published")
    private boolean published;
    @SerializedName("sliderTypeId")
    private long sliderTypeId;
    @SerializedName("objectEntry")
    private String objectEntry;
    @SerializedName("limitedToStores")
    private boolean limitedToStores;
    @SerializedName("stores")
    private List<String> stores = null;
    @SerializedName("locales")
    private List<String> locales = null;

}
