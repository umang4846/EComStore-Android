package com.appprocessors.ecomstore.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Address implements Parcelable {

    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("fullName")
    @Expose
    private String fullName;
    @SerializedName("subDistrict")
    @Expose
    private String subDistrict;
    @SerializedName("cityTown")
    @Expose
    private String cityTown;
    @SerializedName("homeNoBuildingName")
    @Expose
    private String homeNoBuildingName;
    @SerializedName("localityAreaStreet")
    @Expose
    private String localityAreaStreet;
    @SerializedName("mobileNo")
    @Expose
    private String mobileNo;
    @SerializedName("alternateMobileNumber")
    @Expose
    private String alternateMobileNumber;
    @SerializedName("addressType")
    @Expose
    private String addressType;
    @SerializedName("isDefaultAddress")
    @Expose
    private Boolean isDefaultAddress;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getSubDistrict() {
        return subDistrict;
    }

    public void setSubDistrict(String subDistrict) {
        this.subDistrict = subDistrict;
    }

    public String getCityTown() {
        return cityTown;
    }

    public void setCityTown(String cityTown) {
        this.cityTown = cityTown;
    }

    public String getHomeNoBuildingName() {
        return homeNoBuildingName;
    }

    public void setHomeNoBuildingName(String homeNoBuildingName) {
        this.homeNoBuildingName = homeNoBuildingName;
    }

    public String getLocalityAreaStreet() {
        return localityAreaStreet;
    }

    public void setLocalityAreaStreet(String localityAreaStreet) {
        this.localityAreaStreet = localityAreaStreet;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getAlternateMobileNumber() {
        return alternateMobileNumber;
    }

    public void setAlternateMobileNumber(String alternateMobileNumber) {
        this.alternateMobileNumber = alternateMobileNumber;
    }

    public String getAddressType() {
        return addressType;
    }

    public void setAddressType(String addressType) {
        this.addressType = addressType;
    }

    public Boolean getIsDefaultAddress() {
        return isDefaultAddress;
    }

    public void setIsDefaultAddress(Boolean isDefaultAddress) {
        this.isDefaultAddress = isDefaultAddress;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.fullName);
        dest.writeString(this.subDistrict);
        dest.writeString(this.cityTown);
        dest.writeString(this.homeNoBuildingName);
        dest.writeString(this.localityAreaStreet);
        dest.writeString(this.mobileNo);
        dest.writeString(this.alternateMobileNumber);
        dest.writeString(this.addressType);
        dest.writeValue(this.isDefaultAddress);
    }

    public Address() {
    }

    protected Address(Parcel in) {
        this.id = in.readString();
        this.fullName = in.readString();
        this.subDistrict = in.readString();
        this.cityTown = in.readString();
        this.homeNoBuildingName = in.readString();
        this.localityAreaStreet = in.readString();
        this.mobileNo = in.readString();
        this.alternateMobileNumber = in.readString();
        this.addressType = in.readString();
        this.isDefaultAddress = (Boolean) in.readValue(Boolean.class.getClassLoader());
    }

    public static final Parcelable.Creator<Address> CREATOR = new Parcelable.Creator<Address>() {
        @Override
        public Address createFromParcel(Parcel source) {
            return new Address(source);
        }

        @Override
        public Address[] newArray(int size) {
            return new Address[size];
        }
    };
}
