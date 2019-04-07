package com.appprocessors.ecomstore.model.product;

import android.os.Parcel;
import android.os.Parcelable;

import com.appprocessors.ecomstore.model.Content;
import com.appprocessors.ecomstore.model.picture.Picture;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@NoArgsConstructor
@Getter
@Setter
public class ProductList implements Parcelable {

    @SerializedName("_id")
    private String _id;

    @SerializedName("productTags")
    private List<String> ProductTags;

    @SerializedName("productPictures")
    private List<ProductPictures> ProductPictures;

    @SerializedName("productManufacturers")
    private List<String> ProductManufacturers;

    @SerializedName("productCategories")
    private List<ProductCategories> ProductCategories;

    @SerializedName("onSale")
    private int OnSale;

    @SerializedName("viewed")
    private long Viewed;

    @SerializedName("sold")
    private int Sold;

    @SerializedName("published")
    private boolean Published;

    @SerializedName("displayOrderManufacturer")
    private int DisplayOrderManufacturer;

    @SerializedName("displayOrderCategory")
    private int DisplayOrderCategory;

    @SerializedName("displayOrder")
    private int DisplayOrder;

    @SerializedName("auctionEnded")
    private boolean AuctionEnded;

    @SerializedName("markAsNew")
    private boolean MarkAsNew;

    @SerializedName("productCost")
    private double ProductCost;

    @SerializedName("catalogPrice")
    private double CatalogPrice;

    @SerializedName("oldPrice")
    private double OldPrice;

    @SerializedName("price")
    private double Price;

    @SerializedName("notReturnable")
    private boolean NotReturnable;

    @SerializedName("orderMaximumQuantity")
    private int OrderMaximumQuantity;

    @SerializedName("orderMinimumQuantity")
    private int OrderMinimumQuantity;

    @SerializedName("allowBackInStockSubscriptions")
    private boolean AllowBackInStockSubscriptions;

    @SerializedName("backorderModeId")
    private int BackorderModeId;

    @SerializedName("notifyAdminForQuantityBelow")
    private int NotifyAdminForQuantityBelow;

    @SerializedName("lowStockActivityId")
    private int LowStockActivityId;

    @SerializedName("lowStock")
    private boolean LowStock;

    @SerializedName("minStockQuantity")
    private int MinStockQuantity;

    @SerializedName("displayStockQuantity")
    private boolean DisplayStockQuantity;

    @SerializedName("displayStockAvailability")
    private boolean DisplayStockAvailability;

    @SerializedName("stockQuantity")
    private int StockQuantity;

    @SerializedName("useMultipleWarehouses")
    private boolean UseMultipleWarehouses;

    @SerializedName("additionalShippingCharge")
    private double AdditionalShippingCharge;

    @SerializedName("shipSeparately")
    private boolean ShipSeparately;

    @SerializedName("isFreeShipping")
    private boolean IsFreeShipping;

    @SerializedName("isShipEnabled")
    private boolean IsShipEnabled;

    @SerializedName("sku")
    private String Sku;

    @SerializedName("customerRoles")
    private List<String> CustomerRoles;

    @SerializedName("notApprovedTotalReviews")
    private int NotApprovedTotalReviews;

    @SerializedName("approvedTotalReviews")
    private int ApprovedTotalReviews;

    @SerializedName("notApprovedRatingSum")
    private int NotApprovedRatingSum;

    @SerializedName("approvedRatingSum")
    private int ApprovedRatingSum;

    @SerializedName("allowCustomerReviews")
    private boolean AllowCustomerReviews;

    @SerializedName("showOnHomePage")
    private boolean ShowOnHomePage;

    @SerializedName("pictureDetails")
    private List<Picture> PictureDetails = null;

    @SerializedName("productTemplateId")
    private String ProductTemplateId;

    @SerializedName("seName")
    private String SeName;

    @SerializedName("name")
    private String Name;

    @SerializedName("visibleIndividually")
    private boolean VisibleIndividually;

    @SerializedName("productTypeId")
    private int ProductTypeId;

    @SerializedName("genericAttributes")
    private List<String> GenericAttributes;

    @SerializedName("content")
    private List<Content> content;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this._id);
        dest.writeStringList(this.ProductTags);
        dest.writeList(this.ProductPictures);
        dest.writeStringList(this.ProductManufacturers);
        dest.writeList(this.ProductCategories);
        dest.writeInt(this.OnSale);
        dest.writeLong(this.Viewed);
        dest.writeInt(this.Sold);
        dest.writeByte(this.Published ? (byte) 1 : (byte) 0);
        dest.writeInt(this.DisplayOrderManufacturer);
        dest.writeInt(this.DisplayOrderCategory);
        dest.writeInt(this.DisplayOrder);
        dest.writeByte(this.AuctionEnded ? (byte) 1 : (byte) 0);
        dest.writeByte(this.MarkAsNew ? (byte) 1 : (byte) 0);
        dest.writeDouble(this.ProductCost);
        dest.writeDouble(this.CatalogPrice);
        dest.writeDouble(this.OldPrice);
        dest.writeDouble(this.Price);
        dest.writeByte(this.NotReturnable ? (byte) 1 : (byte) 0);
        dest.writeInt(this.OrderMaximumQuantity);
        dest.writeInt(this.OrderMinimumQuantity);
        dest.writeByte(this.AllowBackInStockSubscriptions ? (byte) 1 : (byte) 0);
        dest.writeInt(this.BackorderModeId);
        dest.writeInt(this.NotifyAdminForQuantityBelow);
        dest.writeInt(this.LowStockActivityId);
        dest.writeByte(this.LowStock ? (byte) 1 : (byte) 0);
        dest.writeInt(this.MinStockQuantity);
        dest.writeByte(this.DisplayStockQuantity ? (byte) 1 : (byte) 0);
        dest.writeByte(this.DisplayStockAvailability ? (byte) 1 : (byte) 0);
        dest.writeInt(this.StockQuantity);
        dest.writeByte(this.UseMultipleWarehouses ? (byte) 1 : (byte) 0);
        dest.writeDouble(this.AdditionalShippingCharge);
        dest.writeByte(this.ShipSeparately ? (byte) 1 : (byte) 0);
        dest.writeByte(this.IsFreeShipping ? (byte) 1 : (byte) 0);
        dest.writeByte(this.IsShipEnabled ? (byte) 1 : (byte) 0);
        dest.writeString(this.Sku);
        dest.writeStringList(this.CustomerRoles);
        dest.writeInt(this.NotApprovedTotalReviews);
        dest.writeInt(this.ApprovedTotalReviews);
        dest.writeInt(this.NotApprovedRatingSum);
        dest.writeInt(this.ApprovedRatingSum);
        dest.writeByte(this.AllowCustomerReviews ? (byte) 1 : (byte) 0);
        dest.writeByte(this.ShowOnHomePage ? (byte) 1 : (byte) 0);
        dest.writeTypedList(this.PictureDetails);
        dest.writeString(this.ProductTemplateId);
        dest.writeString(this.SeName);
        dest.writeString(this.Name);
        dest.writeByte(this.VisibleIndividually ? (byte) 1 : (byte) 0);
        dest.writeInt(this.ProductTypeId);
        dest.writeStringList(this.GenericAttributes);
        dest.writeTypedList(this.content);
    }

    protected ProductList(Parcel in) {
        this._id = in.readString();
        this.ProductTags = in.createStringArrayList();
        this.ProductPictures = new ArrayList<com.appprocessors.ecomstore.model.product.ProductPictures>();
        in.readList(this.ProductPictures, com.appprocessors.ecomstore.model.product.ProductPictures.class.getClassLoader());
        this.ProductManufacturers = in.createStringArrayList();
        this.ProductCategories = new ArrayList<com.appprocessors.ecomstore.model.product.ProductCategories>();
        in.readList(this.ProductCategories, com.appprocessors.ecomstore.model.product.ProductCategories.class.getClassLoader());
        this.OnSale = in.readInt();
        this.Viewed = in.readLong();
        this.Sold = in.readInt();
        this.Published = in.readByte() != 0;
        this.DisplayOrderManufacturer = in.readInt();
        this.DisplayOrderCategory = in.readInt();
        this.DisplayOrder = in.readInt();
        this.AuctionEnded = in.readByte() != 0;
        this.MarkAsNew = in.readByte() != 0;
        this.ProductCost = in.readDouble();
        this.CatalogPrice = in.readDouble();
        this.OldPrice = in.readDouble();
        this.Price = in.readDouble();
        this.NotReturnable = in.readByte() != 0;
        this.OrderMaximumQuantity = in.readInt();
        this.OrderMinimumQuantity = in.readInt();
        this.AllowBackInStockSubscriptions = in.readByte() != 0;
        this.BackorderModeId = in.readInt();
        this.NotifyAdminForQuantityBelow = in.readInt();
        this.LowStockActivityId = in.readInt();
        this.LowStock = in.readByte() != 0;
        this.MinStockQuantity = in.readInt();
        this.DisplayStockQuantity = in.readByte() != 0;
        this.DisplayStockAvailability = in.readByte() != 0;
        this.StockQuantity = in.readInt();
        this.UseMultipleWarehouses = in.readByte() != 0;
        this.AdditionalShippingCharge = in.readDouble();
        this.ShipSeparately = in.readByte() != 0;
        this.IsFreeShipping = in.readByte() != 0;
        this.IsShipEnabled = in.readByte() != 0;
        this.Sku = in.readString();
        this.CustomerRoles = in.createStringArrayList();
        this.NotApprovedTotalReviews = in.readInt();
        this.ApprovedTotalReviews = in.readInt();
        this.NotApprovedRatingSum = in.readInt();
        this.ApprovedRatingSum = in.readInt();
        this.AllowCustomerReviews = in.readByte() != 0;
        this.ShowOnHomePage = in.readByte() != 0;
        this.PictureDetails = in.createTypedArrayList(Picture.CREATOR);
        this.ProductTemplateId = in.readString();
        this.SeName = in.readString();
        this.Name = in.readString();
        this.VisibleIndividually = in.readByte() != 0;
        this.ProductTypeId = in.readInt();
        this.GenericAttributes = in.createStringArrayList();
        this.content = in.createTypedArrayList(Content.CREATOR);
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
