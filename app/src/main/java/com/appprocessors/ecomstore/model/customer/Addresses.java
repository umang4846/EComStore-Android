package com.appprocessors.ecomstore.model.customer;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Addresses implements Parcelable {

    @SerializedName("_id")
    private String _id;

    @SerializedName("genericAttributes")
    private List<GenericAttributes> genericAttributes = new ArrayList<>();

    @SerializedName("firstName")
    private String firstName;

    @SerializedName("lastName")
    private String lastName;

    @SerializedName("email")
    private String email;

    @SerializedName("company")
    private String company;

    @SerializedName("vatNumber")
    private String vatNumber;

    @SerializedName("countryId")
    private String countryId;

    @SerializedName("stateProvinceId")
    private String stateProvinceId;

    @SerializedName("city")
    private String city;

    @SerializedName("address1")
    private String address1;

    @SerializedName("address2")
    private String address2;

    @SerializedName("zipPostalCode")
    private String zipPostalCode;

    @SerializedName("phoneNumber")
    private String phoneNumber;

    @SerializedName("faxNumber")
    private String faxNumber;

    @SerializedName("customAttributes")
    private String customAttributes;

    @SerializedName("createdOnUtc")
    private String createdOnUtc;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this._id);
        dest.writeList(this.genericAttributes);
        dest.writeString(this.firstName);
        dest.writeString(this.lastName);
        dest.writeString(this.email);
        dest.writeString(this.company);
        dest.writeString(this.vatNumber);
        dest.writeString(this.countryId);
        dest.writeString(this.stateProvinceId);
        dest.writeString(this.city);
        dest.writeString(this.address1);
        dest.writeString(this.address2);
        dest.writeString(this.zipPostalCode);
        dest.writeString(this.phoneNumber);
        dest.writeString(this.faxNumber);
        dest.writeString(this.customAttributes);
        dest.writeString(this.createdOnUtc);
    }

    protected Addresses(Parcel in) {
        this._id = in.readString();
        this.genericAttributes = new ArrayList<com.appprocessors.ecomstore.model.customer.GenericAttributes>();
        in.readList(this.genericAttributes, com.appprocessors.ecomstore.model.customer.GenericAttributes.class.getClassLoader());
        this.firstName = in.readString();
        this.lastName = in.readString();
        this.email = in.readString();
        this.company = in.readString();
        this.vatNumber = in.readString();
        this.countryId = in.readString();
        this.stateProvinceId = in.readString();
        this.city = in.readString();
        this.address1 = in.readString();
        this.address2 = in.readString();
        this.zipPostalCode = in.readString();
        this.phoneNumber = in.readString();
        this.faxNumber = in.readString();
        this.customAttributes = in.readString();
        this.createdOnUtc = in.readString();
    }

    public static final Parcelable.Creator<Addresses> CREATOR = new Parcelable.Creator<Addresses>() {
        @Override
        public Addresses createFromParcel(Parcel source) {
            return new Addresses(source);
        }

        @Override
        public Addresses[] newArray(int size) {
            return new Addresses[size];
        }
    };
}
