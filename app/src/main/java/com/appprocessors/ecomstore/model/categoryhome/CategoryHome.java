package com.appprocessors.ecomstore.model.categoryhome;

import android.os.Parcel;
import android.os.Parcelable;

import com.appprocessors.ecomstore.model.picture.Picture;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CategoryHome implements Parcelable {

    @SerializedName("_id")
    private String _id;

    @SerializedName("genericAttributes")
    private List<String> genericAttributes = null;

    @SerializedName("name")
    private String Name;

    @SerializedName("description")
    private String Description;

    @SerializedName("categoryTemplateId")
    private String CategoryTemplateId;
    @SerializedName("metaKeywords")

    private String MetaKeywords;
    @SerializedName("metaDescription")
    private String MetaDescription;

    @SerializedName("metaTitle")
    private String MetaTitle;

    @SerializedName("parentCategoryId")
    private String ParentCategoryId;

    @SerializedName("pictureId")
    private String PictureId;

    @SerializedName("pageSize")
    private Integer PageSize;

    @SerializedName("allowCustomersToSelectPageSize")
    private Boolean AllowCustomersToSelectPageSize;

    @SerializedName("pageSizeOptions")
    private String PageSizeOptions;

    @SerializedName("priceRanges")
    private String PriceRanges;

    @SerializedName("showOnHomePage")
    private Boolean ShowOnHomePage;

    @SerializedName("featuredProductsOnHomaPage")
    private Boolean FeaturedProductsOnHomaPage;

    @SerializedName("showOnSearchBox")
    private Boolean ShowOnSearchBox;

    @SerializedName("searchBoxDisplayOrder")
    private Integer SearchBoxDisplayOrder;

    @SerializedName("includeInTopMenu")
    private Boolean IncludeInTopMenu;

    @SerializedName("subjectToAcl")
    private Boolean SubjectToAcl;

    @SerializedName("customerRoles")
    private List<String> CustomerRoles = null;

    @SerializedName("limitedToStores")
    private Boolean LimitedToStores;

    @SerializedName("stores")
    private List<String> Stores = null;

    @SerializedName("seName")
    private String SeName;

    @SerializedName("published")
    private Boolean Published;

    @SerializedName("displayOrder")
    private Integer DisplayOrder;

    @SerializedName("flag")
    private String Flag;

    @SerializedName("flagStyle")
    private String FlagStyle;

    @SerializedName("icon")
    private String Icon;

    @SerializedName("hideOnCatalog")
    private Boolean HideOnCatalog;

    @SerializedName("createdOnUtc")
    private Date CreatedOnUtc;

    @SerializedName("updatedOnUtc")
    private Date UpdatedOnUtc;

    @SerializedName("locales")
    private List<String> Locales = null;

    @SerializedName("appliedDiscounts")
    private List<Object> AppliedDiscounts = null;

    @SerializedName("pictureDetails")
    private List<Picture> PictureDetails = null;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this._id);
        dest.writeStringList(this.genericAttributes);
        dest.writeString(this.Name);
        dest.writeString(this.Description);
        dest.writeString(this.CategoryTemplateId);
        dest.writeString(this.MetaKeywords);
        dest.writeString(this.MetaDescription);
        dest.writeString(this.MetaTitle);
        dest.writeString(this.ParentCategoryId);
        dest.writeString(this.PictureId);
        dest.writeValue(this.PageSize);
        dest.writeValue(this.AllowCustomersToSelectPageSize);
        dest.writeString(this.PageSizeOptions);
        dest.writeString(this.PriceRanges);
        dest.writeValue(this.ShowOnHomePage);
        dest.writeValue(this.FeaturedProductsOnHomaPage);
        dest.writeValue(this.ShowOnSearchBox);
        dest.writeValue(this.SearchBoxDisplayOrder);
        dest.writeValue(this.IncludeInTopMenu);
        dest.writeValue(this.SubjectToAcl);
        dest.writeStringList(this.CustomerRoles);
        dest.writeValue(this.LimitedToStores);
        dest.writeStringList(this.Stores);
        dest.writeString(this.SeName);
        dest.writeValue(this.Published);
        dest.writeValue(this.DisplayOrder);
        dest.writeString(this.Flag);
        dest.writeString(this.FlagStyle);
        dest.writeString(this.Icon);
        dest.writeValue(this.HideOnCatalog);
        dest.writeLong(this.CreatedOnUtc != null ? this.CreatedOnUtc.getTime() : -1);
        dest.writeLong(this.UpdatedOnUtc != null ? this.UpdatedOnUtc.getTime() : -1);
        dest.writeStringList(this.Locales);
        dest.writeList(this.AppliedDiscounts);
        dest.writeList(this.PictureDetails);
    }

    protected CategoryHome(Parcel in) {
        this._id = in.readString();
        this.genericAttributes = in.createStringArrayList();
        this.Name = in.readString();
        this.Description = in.readString();
        this.CategoryTemplateId = in.readString();
        this.MetaKeywords = in.readString();
        this.MetaDescription = in.readString();
        this.MetaTitle = in.readString();
        this.ParentCategoryId = in.readString();
        this.PictureId = in.readString();
        this.PageSize = (Integer) in.readValue(Integer.class.getClassLoader());
        this.AllowCustomersToSelectPageSize = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.PageSizeOptions = in.readString();
        this.PriceRanges = in.readString();
        this.ShowOnHomePage = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.FeaturedProductsOnHomaPage = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.ShowOnSearchBox = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.SearchBoxDisplayOrder = (Integer) in.readValue(Integer.class.getClassLoader());
        this.IncludeInTopMenu = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.SubjectToAcl = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.CustomerRoles = in.createStringArrayList();
        this.LimitedToStores = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.Stores = in.createStringArrayList();
        this.SeName = in.readString();
        this.Published = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.DisplayOrder = (Integer) in.readValue(Integer.class.getClassLoader());
        this.Flag = in.readString();
        this.FlagStyle = in.readString();
        this.Icon = in.readString();
        this.HideOnCatalog = (Boolean) in.readValue(Boolean.class.getClassLoader());
        long tmpCreatedOnUtc = in.readLong();
        this.CreatedOnUtc = tmpCreatedOnUtc == -1 ? null : new Date(tmpCreatedOnUtc);
        long tmpUpdatedOnUtc = in.readLong();
        this.UpdatedOnUtc = tmpUpdatedOnUtc == -1 ? null : new Date(tmpUpdatedOnUtc);
        this.Locales = in.createStringArrayList();
        this.AppliedDiscounts = new ArrayList<Object>();
        in.readList(this.AppliedDiscounts, Object.class.getClassLoader());
        this.PictureDetails = new ArrayList<Picture>();
        in.readList(this.PictureDetails, Picture.class.getClassLoader());
    }

    public static final Parcelable.Creator<CategoryHome> CREATOR = new Parcelable.Creator<CategoryHome>() {
        @Override
        public CategoryHome createFromParcel(Parcel source) {
            return new CategoryHome(source);
        }

        @Override
        public CategoryHome[] newArray(int size) {
            return new CategoryHome[size];
        }
    };
}
