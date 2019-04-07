package com.appprocessors.ecomstore.model.product;

import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class ProductPictures {

    @SerializedName("_id")
    private String id;
    @SerializedName("pictureId")
    private String pictureId;
    @SerializedName("displayOrder")
    private long displayOrder;
    @SerializedName("mimeType")
    private String mimeType;
    @SerializedName("seoFilename")
    private String seoFilename;
    @SerializedName("altAttribute")
    private String altAttribute;
    @SerializedName("titleAttribute")
    private String titleAttribute;
}
