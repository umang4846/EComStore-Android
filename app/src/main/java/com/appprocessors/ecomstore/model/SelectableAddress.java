package com.appprocessors.ecomstore.model;

import android.os.Parcel;

public class SelectableAddress extends Address {

    private boolean isSelected = false;

    public SelectableAddress(Address address,boolean isSelected) {
        super(address.get_id(),
                address.getFullName(),
                address.getSubDistrict(),
                address.getCityTown(),
                address.getHomeNoBuildingName(),
                address.getLocalityAreaStreet(),
                address.getMobileNo(),
                address.getAlternateMobileNumber(),
                address.getAddressType(),
                address.getIsDefaultAddress());
        this.isSelected = isSelected;
    }


    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeByte(this.isSelected ? (byte) 1 : (byte) 0);
    }

    public SelectableAddress(Parcel in) {
        super(in);
        this.isSelected = in.readByte() != 0;
    }

    public static final Creator<SelectableAddress> CREATOR = new Creator<SelectableAddress>() {
        @Override
        public SelectableAddress createFromParcel(Parcel source) {
            return new SelectableAddress(source);
        }

        @Override
        public SelectableAddress[] newArray(int size) {
            return new SelectableAddress[size];
        }
    };
}
