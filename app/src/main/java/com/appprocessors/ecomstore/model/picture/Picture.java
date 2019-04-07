package com.appprocessors.ecomstore.model.picture;

import android.os.Parcel;
import android.os.Parcelable;

import com.appprocessors.ecomstore.model.customer.GenericAttributes;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor
public class Picture implements Parcelable {

    @SerializedName("_id")
    private String _id;

    @SerializedName("genericAttributes")
    private List<GenericAttributes> genericAttributes = null;

    @SerializedName("mimeType")
    private String mimeType;

    @SerializedName("seoFilename")
    private String seoFilename;

    @SerializedName("altAttribute")
    private String altAttribute;

    @SerializedName("titleAttribute")
    private String titleAttribute;

    @SerializedName("isNew")
    private Boolean isNew;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this._id);
        dest.writeList(this.genericAttributes);
        dest.writeString(this.mimeType);
        dest.writeString(this.seoFilename);
        dest.writeString(this.altAttribute);
        dest.writeString(this.titleAttribute);
        dest.writeValue(this.isNew);
    }

    protected Picture(Parcel in) {
        this._id = in.readString();
        this.genericAttributes = new ArrayList<GenericAttributes>();
        in.readList(this.genericAttributes, GenericAttributes.class.getClassLoader());
        this.mimeType = in.readString();
        this.seoFilename = in.readString();
        this.altAttribute = in.readString();
        this.titleAttribute = in.readString();
        this.isNew = (Boolean) in.readValue(Boolean.class.getClassLoader());
    }

    public static final Parcelable.Creator<Picture> CREATOR = new Parcelable.Creator<Picture>() {
        @Override
        public Picture createFromParcel(Parcel source) {
            return new Picture(source);
        }

        @Override
        public Picture[] newArray(int size) {
            return new Picture[size];
        }
    };
}
