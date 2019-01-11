package com.appprocessors.ecomstore.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ProductList implements Parcelable {



    @SerializedName("content")
    @Expose
    private List<Content> content = null;
    @SerializedName("pageable")
    @Expose
    private Pageable pageable;
    @SerializedName("totalPages")
    @Expose
    private Integer totalPages;
    @SerializedName("last")
    @Expose
    private Boolean last;
    @SerializedName("totalElements")
    @Expose
    private Integer totalElements;
    @SerializedName("size")
    @Expose
    private Integer size;
    @SerializedName("number")
    @Expose
    private Integer number;
    @SerializedName("sort")
    @Expose
    private Sort sort;
    @SerializedName("first")
    @Expose
    private Boolean first;
    @SerializedName("numberOfElements")
    @Expose
    private Integer numberOfElements;

    public List<Content> getContent() {
        return content;
    }

    public void setContent(List<Content> content) {
        this.content = content;
    }

    public Pageable getPageable() {
        return pageable;
    }

    public void setPageable(Pageable pageable) {
        this.pageable = pageable;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    public Boolean getLast() {
        return last;
    }

    public void setLast(Boolean last) {
        this.last = last;
    }

    public Integer getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(Integer totalElements) {
        this.totalElements = totalElements;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Sort getSort() {
        return sort;
    }

    public void setSort(Sort sort) {
        this.sort = sort;
    }

    public Boolean getFirst() {
        return first;
    }

    public void setFirst(Boolean first) {
        this.first = first;
    }

    public Integer getNumberOfElements() {
        return numberOfElements;
    }

    public void setNumberOfElements(Integer numberOfElements) {
        this.numberOfElements = numberOfElements;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.content);
        dest.writeParcelable(this.pageable, flags);
        dest.writeValue(this.totalPages);
        dest.writeValue(this.last);
        dest.writeValue(this.totalElements);
        dest.writeValue(this.size);
        dest.writeValue(this.number);
        dest.writeParcelable(this.sort, flags);
        dest.writeValue(this.first);
        dest.writeValue(this.numberOfElements);
    }

    public ProductList() {
    }

    protected ProductList(Parcel in) {
        this.content = in.createTypedArrayList(Content.CREATOR);
        this.pageable = in.readParcelable(Pageable.class.getClassLoader());
        this.totalPages = (Integer) in.readValue(Integer.class.getClassLoader());
        this.last = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.totalElements = (Integer) in.readValue(Integer.class.getClassLoader());
        this.size = (Integer) in.readValue(Integer.class.getClassLoader());
        this.number = (Integer) in.readValue(Integer.class.getClassLoader());
        this.sort = in.readParcelable(Sort.class.getClassLoader());
        this.first = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.numberOfElements = (Integer) in.readValue(Integer.class.getClassLoader());
    }

    public static final Parcelable.Creator<ProductList> CREATOR = new Parcelable.Creator<ProductList>() {
        @Override
        public ProductList createFromParcel(Parcel source) {
            return new ProductList(source);
        }

        @Override
        public ProductList[] newArray(int size) {
            return new ProductList[size];
        }
    };
}
