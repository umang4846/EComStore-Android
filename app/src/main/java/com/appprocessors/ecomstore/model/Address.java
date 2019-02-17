package com.appprocessors.ecomstore.model;

import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Address implements Parcelable {

    @SerializedName("_id")
    @Expose
    private String _id;
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

    public Address() {
    }

    public Address(String _id, String fullName, String subDistrict, String cityTown, String homeNoBuildingName, String localityAreaStreet, String mobileNo, String alternateMobileNumber, String addressType, boolean isDefaultAddress) {
        this._id = _id;
        this.fullName = fullName;
        this.subDistrict = subDistrict;
        this.cityTown = cityTown;
        this.homeNoBuildingName = homeNoBuildingName;
        this.localityAreaStreet = localityAreaStreet;
        this.mobileNo = mobileNo;
        this.alternateMobileNumber = alternateMobileNumber;
        this.addressType = addressType;
        this.isDefaultAddress = isDefaultAddress;
    }

    protected Address(Parcel in) {
        _id = in.readString();
        fullName = in.readString();
        subDistrict = in.readString();
        cityTown = in.readString();
        homeNoBuildingName = in.readString();
        localityAreaStreet = in.readString();
        mobileNo = in.readString();
        alternateMobileNumber = in.readString();
        addressType = in.readString();
        byte tmpIsDefaultAddress = in.readByte();
        isDefaultAddress = tmpIsDefaultAddress == 0 ? null : tmpIsDefaultAddress == 1;
    }

    public static final Creator<Address> CREATOR = new Creator<Address>() {
        @Override
        public Address createFromParcel(Parcel in) {
            return new Address(in);
        }

        @Override
        public Address[] newArray(int size) {
            return new Address[size];
        }
    };

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
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

    public void setIsDefaultAddress(Boolean defaultAddress) {
        isDefaultAddress = defaultAddress;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(_id);
        dest.writeString(fullName);
        dest.writeString(subDistrict);
        dest.writeString(cityTown);
        dest.writeString(homeNoBuildingName);
        dest.writeString(localityAreaStreet);
        dest.writeString(mobileNo);
        dest.writeString(alternateMobileNumber);
        dest.writeString(addressType);
        dest.writeByte((byte) (isDefaultAddress == null ? 0 : isDefaultAddress ? 1 : 2));
    }
}
