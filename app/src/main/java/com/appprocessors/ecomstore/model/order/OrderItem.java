package com.appprocessors.ecomstore.model.order;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import org.joda.time.DateTime;

import java.util.UUID;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class OrderItem implements Parcelable {

    @SerializedName("_id")
    private String _id;
    @SerializedName("orderItemGuid")
    private UUID orderItemGuid;
    @SerializedName("productId")
    private String productId;
    @SerializedName("vendorId")
    private String vendorId;
    @SerializedName("warehouseId")
    private String warehouseId;
    @SerializedName("quantity")
    private long quantity;
    @SerializedName("unitPriceWithoutDiscInclTax")
    private double unitPriceWithoutDiscInclTax;
    @SerializedName("unitPriceWithoutDiscExclTax")
    private double unitPriceWithoutDiscExclTax;
    @SerializedName("unitPriceInclTax")
    private double unitPriceInclTax;
    @SerializedName("unitPriceExclTax")
    private double unitPriceExclTax;
    @SerializedName("priceInclTax")
    private double priceInclTax;
    @SerializedName("priceExclTax")
    private double priceExclTax;
    @SerializedName("discountAmountInclTax")
    private double discountAmountInclTax;
    @SerializedName("discountAmountExclTax")
    private double discountAmountExclTax;
    @SerializedName("originalProductCost")
    private double originalProductCost;
    @SerializedName("attributeDescription")
    private String attributeDescription;
    @SerializedName("AttributesXml")
    private String attributesXml;
    @SerializedName("downloadCount")
    private long DownloadCount;
    @SerializedName("isDownloadActivated")
    private boolean isDownloadActivated;
    @SerializedName("licenseDownloadId")
    private String licenseDownloadId;
    @SerializedName("itemWeight")
    private long itemWeight;
    @SerializedName("rentalStartDateUtc")
    private String rentalStartDateUtc;
    @SerializedName("rentalEndDateUtc")
    private String rentalEndDateUtc;
    @SerializedName("createdOnUtc")
    private String createdOnUtc;

    public String get_id() {
        return _id;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this._id);
        dest.writeSerializable(this.orderItemGuid);
        dest.writeString(this.productId);
        dest.writeString(this.vendorId);
        dest.writeString(this.warehouseId);
        dest.writeLong(this.quantity);
        dest.writeDouble(this.unitPriceWithoutDiscInclTax);
        dest.writeDouble(this.unitPriceWithoutDiscExclTax);
        dest.writeDouble(this.unitPriceInclTax);
        dest.writeDouble(this.unitPriceExclTax);
        dest.writeDouble(this.priceInclTax);
        dest.writeDouble(this.priceExclTax);
        dest.writeDouble(this.discountAmountInclTax);
        dest.writeDouble(this.discountAmountExclTax);
        dest.writeDouble(this.originalProductCost);
        dest.writeString(this.attributeDescription);
        dest.writeString(this.attributesXml);
        dest.writeLong(this.DownloadCount);
        dest.writeByte(this.isDownloadActivated ? (byte) 1 : (byte) 0);
        dest.writeString(this.licenseDownloadId);
        dest.writeLong(this.itemWeight);
        dest.writeString(this.rentalStartDateUtc);
        dest.writeString(this.rentalEndDateUtc);
        dest.writeString(this.createdOnUtc);
    }

    protected OrderItem(Parcel in) {
        this._id = in.readString();
        this.orderItemGuid = (UUID) in.readSerializable();
        this.productId = in.readString();
        this.vendorId = in.readString();
        this.warehouseId = in.readString();
        this.quantity = in.readLong();
        this.unitPriceWithoutDiscInclTax = in.readDouble();
        this.unitPriceWithoutDiscExclTax = in.readDouble();
        this.unitPriceInclTax = in.readDouble();
        this.unitPriceExclTax = in.readDouble();
        this.priceInclTax = in.readDouble();
        this.priceExclTax = in.readDouble();
        this.discountAmountInclTax = in.readDouble();
        this.discountAmountExclTax = in.readDouble();
        this.originalProductCost = in.readDouble();
        this.attributeDescription = in.readString();
        this.attributesXml = in.readString();
        this.DownloadCount = in.readLong();
        this.isDownloadActivated = in.readByte() != 0;
        this.licenseDownloadId = in.readString();
        this.itemWeight = in.readLong();
        this.rentalStartDateUtc = in.readString();
        this.rentalEndDateUtc = in.readString();
        this.createdOnUtc = in.readString();
    }

    public static final Parcelable.Creator<OrderItem> CREATOR = new Parcelable.Creator<OrderItem>() {
        @Override
        public OrderItem createFromParcel(Parcel source) {
            return new OrderItem(source);
        }

        @Override
        public OrderItem[] newArray(int size) {
            return new OrderItem[size];
        }
    };
}
