package com.appprocessors.ecomstore.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Village implements Parcelable {

    @SerializedName("_id")
    @Expose
    public String id;
    @SerializedName("villageName")
    @Expose
    public String villageName;
    @SerializedName("isDliveryAvailable")
    @Expose
    public Boolean isDliveryAvailable;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVillageName() {
        return villageName;
    }

    public void setVillageName(String villageName) {
        this.villageName = villageName;
    }

    public Boolean getIsDliveryAvailable() {
        return isDliveryAvailable;
    }

    public void setIsDliveryAvailable(Boolean isDliveryAvailable) {
        this.isDliveryAvailable = isDliveryAvailable;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.villageName);
        dest.writeValue(this.isDliveryAvailable);
    }

    public Village() {
    }

    protected Village(Parcel in) {
        this.id = in.readString();
        this.villageName = in.readString();
        this.isDliveryAvailable = (Boolean) in.readValue(Boolean.class.getClassLoader());
    }

    public static final Parcelable.Creator<Village> CREATOR = new Parcelable.Creator<Village>() {
        @Override
        public Village createFromParcel(Parcel source) {
            return new Village(source);
        }

        @Override
        public Village[] newArray(int size) {
            return new Village[size];
        }
    };
}
