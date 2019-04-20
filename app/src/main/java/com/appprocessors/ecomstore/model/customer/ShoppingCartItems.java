package com.appprocessors.ecomstore.model.customer;

import android.os.Parcel;
import android.os.Parcelable;

import com.appprocessors.ecomstore.model.picture.Picture;
import com.appprocessors.ecomstore.model.product.Product;
import com.appprocessors.ecomstore.model.product.ProductPictures;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class ShoppingCartItems implements Parcelable {

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
    @SerializedName("productDetails")
    private Product productDetails = null;
    @SerializedName("pictureDetails")
    private List<Picture> pictureDetails = new ArrayList<>();

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this._id);
        dest.writeString(this.storeId);
        dest.writeInt(this.shoppingCartTypeId);
        dest.writeString(this.productId);
        dest.writeString(this.attributesXml);
        dest.writeDouble(this.customerEnteredPrice);
        dest.writeInt(this.quantity);
        dest.writeString(this.rentalStartDateUtc);
        dest.writeString(this.rentalEndDateUtc);
        dest.writeString(this.createdOnUtc);
        dest.writeString(this.updatedOnUtc);
        dest.writeByte(this.isFreeShipping ? (byte) 1 : (byte) 0);
        dest.writeByte(this.isGiftCard ? (byte) 1 : (byte) 0);
        dest.writeByte(this.isShipEnabled ? (byte) 1 : (byte) 0);
        dest.writeDouble(this.additionalShippingChargeProduct);
        dest.writeByte(this.isTaxExempt ? (byte) 1 : (byte) 0);
        dest.writeByte(this.isRecurring ? (byte) 1 : (byte) 0);
        dest.writeString(this.reservationId);
        dest.writeString(this.parameter);
        dest.writeString(this.duration);
        dest.writeParcelable(this.productDetails, flags);
        dest.writeTypedList(this.pictureDetails);
    }

    protected ShoppingCartItems(Parcel in) {
        this._id = in.readString();
        this.storeId = in.readString();
        this.shoppingCartTypeId = in.readInt();
        this.productId = in.readString();
        this.attributesXml = in.readString();
        this.customerEnteredPrice = in.readDouble();
        this.quantity = in.readInt();
        this.rentalStartDateUtc = in.readString();
        this.rentalEndDateUtc = in.readString();
        this.createdOnUtc = in.readString();
        this.updatedOnUtc = in.readString();
        this.isFreeShipping = in.readByte() != 0;
        this.isGiftCard = in.readByte() != 0;
        this.isShipEnabled = in.readByte() != 0;
        this.additionalShippingChargeProduct = in.readDouble();
        this.isTaxExempt = in.readByte() != 0;
        this.isRecurring = in.readByte() != 0;
        this.reservationId = in.readString();
        this.parameter = in.readString();
        this.duration = in.readString();
        this.productDetails = in.readParcelable(Product.class.getClassLoader());
        this.pictureDetails = in.createTypedArrayList(Picture.CREATOR);
    }

    public static final Parcelable.Creator<ShoppingCartItems> CREATOR = new Parcelable.Creator<ShoppingCartItems>() {
        @Override
        public ShoppingCartItems createFromParcel(Parcel source) {
            return new ShoppingCartItems(source);
        }

        @Override
        public ShoppingCartItems[] newArray(int size) {
            return new ShoppingCartItems[size];
        }
    };
}
