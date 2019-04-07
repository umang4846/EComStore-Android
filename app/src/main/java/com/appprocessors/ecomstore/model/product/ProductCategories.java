package com.appprocessors.ecomstore.model.product;

import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class ProductCategories {

    @SerializedName("displayOrder")
    private int DisplayOrder;

    @SerializedName("isFeaturedProduct")
    private boolean IsFeaturedProduct;

    @SerializedName("categoryId")
    private String CategoryId;
    @SerializedName("_id")
    private String _id;


}
