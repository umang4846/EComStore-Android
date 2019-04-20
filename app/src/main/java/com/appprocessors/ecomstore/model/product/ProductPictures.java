package com.appprocessors.ecomstore.model.product;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class ProductPictures implements Parcelable {

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.pictureId);
        dest.writeLong(this.displayOrder);
        dest.writeString(this.mimeType);
        dest.writeString(this.seoFilename);
        dest.writeString(this.altAttribute);
        dest.writeString(this.titleAttribute);
    }

    protected ProductPictures(Parcel in) {
        this.id = in.readString();
        this.pictureId = in.readString();
        this.displayOrder = in.readLong();
        this.mimeType = in.readString();
        this.seoFilename = in.readString();
        this.altAttribute = in.readString();
        this.titleAttribute = in.readString();
    }

    public static final Parcelable.Creator<ProductPictures> CREATOR = new Parcelable.Creator<ProductPictures>() {
        @Override
        public ProductPictures createFromParcel(Parcel source) {
            return new ProductPictures(source);
        }

        @Override
        public ProductPictures[] newArray(int size) {
            return new ProductPictures[size];
        }
    };
}
