package com.appprocessors.ecomstore.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Pageable implements Parcelable {


    @SerializedName("sort")
    @Expose
    private Sort sort;
    @SerializedName("offset")
    @Expose
    private Integer offset;
    @SerializedName("pageSize")
    @Expose
    private Integer pageSize;
    @SerializedName("pageNumber")
    @Expose
    private Integer pageNumber;
    @SerializedName("paged")
    @Expose
    private Boolean paged;
    @SerializedName("unpaged")
    @Expose
    private Boolean unpaged;

    public Sort getSort() {
        return sort;
    }

    public void setSort(Sort sort) {
        this.sort = sort;
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    public Boolean getPaged() {
        return paged;
    }

    public void setPaged(Boolean paged) {
        this.paged = paged;
    }

    public Boolean getUnpaged() {
        return unpaged;
    }

    public void setUnpaged(Boolean unpaged) {
        this.unpaged = unpaged;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.sort, flags);
        dest.writeValue(this.offset);
        dest.writeValue(this.pageSize);
        dest.writeValue(this.pageNumber);
        dest.writeValue(this.paged);
        dest.writeValue(this.unpaged);
    }

    public Pageable() {
    }

    protected Pageable(Parcel in) {
        this.sort = in.readParcelable(Sort.class.getClassLoader());
        this.offset = (Integer) in.readValue(Integer.class.getClassLoader());
        this.pageSize = (Integer) in.readValue(Integer.class.getClassLoader());
        this.pageNumber = (Integer) in.readValue(Integer.class.getClassLoader());
        this.paged = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.unpaged = (Boolean) in.readValue(Boolean.class.getClassLoader());
    }

    public static final Parcelable.Creator<Pageable> CREATOR = new Parcelable.Creator<Pageable>() {
        @Override
        public Pageable createFromParcel(Parcel source) {
            return new Pageable(source);
        }

        @Override
        public Pageable[] newArray(int size) {
            return new Pageable[size];
        }
    };
}