package com.appprocessors.ecomstore.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LifeStyleInfo implements Parcelable {


    @SerializedName("sleeve")
    @Expose
    private String sleeve;
    @SerializedName("neck")
    @Expose
    private String neck;
    @SerializedName("idealFor")
    @Expose
    private String idealFor;
    @SerializedName("specificationList")
    @Expose
    private List<SpecificationList> specificationList = null;

    protected LifeStyleInfo(Parcel in) {
        sleeve = in.readString();
        neck = in.readString();
        idealFor = in.readString();
    }

    public static final Creator<LifeStyleInfo> CREATOR = new Creator<LifeStyleInfo>() {
        @Override
        public LifeStyleInfo createFromParcel(Parcel in) {
            return new LifeStyleInfo(in);
        }

        @Override
        public LifeStyleInfo[] newArray(int size) {
            return new LifeStyleInfo[size];
        }
    };

    public String getSleeve() {
        return sleeve;
    }

    public void setSleeve(String sleeve) {
        this.sleeve = sleeve;
    }

    public String getNeck() {
        return neck;
    }

    public void setNeck(String neck) {
        this.neck = neck;
    }

    public String getIdealFor() {
        return idealFor;
    }

    public void setIdealFor(String idealFor) {
        this.idealFor = idealFor;
    }

    public List<SpecificationList> getSpecificationList() {
        return specificationList;
    }

    public void setSpecificationList(List<SpecificationList> specificationList) {
        this.specificationList = specificationList;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(sleeve);
        dest.writeString(neck);
        dest.writeString(idealFor);
    }
}