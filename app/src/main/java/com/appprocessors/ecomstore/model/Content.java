package com.appprocessors.ecomstore.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Content implements Parcelable {


    @SerializedName("visibleIndividually")
    private boolean visibleIndividually;
    @SerializedName("viewed")
    private int viewed;
    @SerializedName("useMultipleWarehouses")
    private boolean useMultipleWarehouses;
    @SerializedName("stockQuantity")
    private int stockQuantity;
    @SerializedName("sold")
    private int sold;
    @SerializedName("sku")
    private String sku;
    @SerializedName("showOnHomePage")
    private boolean showOnHomePage;
    @SerializedName("shipSeparately")
    private boolean shipSeparately;
    @SerializedName("seName")
    private String seName;
    @SerializedName("published")
    private boolean published;
    @SerializedName("productTypeId")
    private int productTypeId;
    @SerializedName("productTemplateId")
    private String productTemplateId;
    @SerializedName("productTags")
    private List<String> productTags;
    @SerializedName("productPictures")
    private List<ProductPictures> productPictures;
    @SerializedName("productManufacturers")
    private List<String> productManufacturers;
    @SerializedName("productCost")
    private int productCost;
    @SerializedName("productCategories")
    private List<ProductCategories> productCategories;
    @SerializedName("price")
    private int price;
    @SerializedName("pictureDetails")
    private List<PictureDetails> pictureDetails;
    @SerializedName("orderMinimumQuantity")
    private int orderMinimumQuantity;
    @SerializedName("orderMaximumQuantity")
    private int orderMaximumQuantity;
    @SerializedName("onSale")
    private int onSale;
    @SerializedName("oldPrice")
    private int oldPrice;
    @SerializedName("notifyAdminForQuantityBelow")
    private int notifyAdminForQuantityBelow;
    @SerializedName("notReturnable")
    private boolean notReturnable;
    @SerializedName("notApprovedTotalReviews")
    private int notApprovedTotalReviews;
    @SerializedName("notApprovedRatingSum")
    private int notApprovedRatingSum;
    @SerializedName("name")
    private String name;
    @SerializedName("minStockQuantity")
    private int minStockQuantity;
    @SerializedName("markAsNew")
    private boolean markAsNew;
    @SerializedName("lowStockActivityId")
    private int lowStockActivityId;
    @SerializedName("lowStock")
    private boolean lowStock;
    @SerializedName("isShipEnabled")
    private boolean isShipEnabled;
    @SerializedName("isFreeShipping")
    private boolean isFreeShipping;
    @SerializedName("genericAttributes")
    private List<String> genericAttributes;
    @SerializedName("displayStockQuantity")
    private boolean displayStockQuantity;
    @SerializedName("displayStockAvailability")
    private boolean displayStockAvailability;
    @SerializedName("displayOrderManufacturer")
    private int displayOrderManufacturer;
    @SerializedName("displayOrderCategory")
    private int displayOrderCategory;
    @SerializedName("displayOrder")
    private int displayOrder;
    @SerializedName("customerRoles")
    private List<String> customerRoles;
    @SerializedName("catalogPrice")
    private int catalogPrice;
    @SerializedName("backorderModeId")
    private int backorderModeId;
    @SerializedName("auctionEnded")
    private boolean auctionEnded;
    @SerializedName("approvedTotalReviews")
    private int approvedTotalReviews;
    @SerializedName("approvedRatingSum")
    private int approvedRatingSum;
    @SerializedName("allowCustomerReviews")
    private boolean allowCustomerReviews;
    @SerializedName("allowBackInStockSubscriptions")
    private boolean allowBackInStockSubscriptions;
    @SerializedName("additionalShippingCharge")
    private int additionalShippingCharge;
    @SerializedName("_id")
    private String _id;

    public boolean getVisibleIndividually() {
        return visibleIndividually;
    }

    public void setVisibleIndividually(boolean visibleIndividually) {
        this.visibleIndividually = visibleIndividually;
    }

    public int getViewed() {
        return viewed;
    }

    public void setViewed(int viewed) {
        this.viewed = viewed;
    }

    public boolean getUseMultipleWarehouses() {
        return useMultipleWarehouses;
    }

    public void setUseMultipleWarehouses(boolean useMultipleWarehouses) {
        this.useMultipleWarehouses = useMultipleWarehouses;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(int stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public int getSold() {
        return sold;
    }

    public void setSold(int sold) {
        this.sold = sold;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public boolean getShowOnHomePage() {
        return showOnHomePage;
    }

    public void setShowOnHomePage(boolean showOnHomePage) {
        this.showOnHomePage = showOnHomePage;
    }

    public boolean getShipSeparately() {
        return shipSeparately;
    }

    public void setShipSeparately(boolean shipSeparately) {
        this.shipSeparately = shipSeparately;
    }

    public String getSeName() {
        return seName;
    }

    public void setSeName(String seName) {
        this.seName = seName;
    }

    public boolean getPublished() {
        return published;
    }

    public void setPublished(boolean published) {
        this.published = published;
    }

    public int getProductTypeId() {
        return productTypeId;
    }

    public void setProductTypeId(int productTypeId) {
        this.productTypeId = productTypeId;
    }

    public String getProductTemplateId() {
        return productTemplateId;
    }

    public void setProductTemplateId(String productTemplateId) {
        this.productTemplateId = productTemplateId;
    }

    public List<String> getProductTags() {
        return productTags;
    }

    public void setProductTags(List<String> productTags) {
        this.productTags = productTags;
    }

    public List<ProductPictures> getProductPictures() {
        return productPictures;
    }

    public void setProductPictures(List<ProductPictures> productPictures) {
        this.productPictures = productPictures;
    }

    public List<String> getProductManufacturers() {
        return productManufacturers;
    }

    public void setProductManufacturers(List<String> productManufacturers) {
        this.productManufacturers = productManufacturers;
    }

    public int getProductCost() {
        return productCost;
    }

    public void setProductCost(int productCost) {
        this.productCost = productCost;
    }

    public List<ProductCategories> getProductCategories() {
        return productCategories;
    }

    public void setProductCategories(List<ProductCategories> productCategories) {
        this.productCategories = productCategories;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public List<PictureDetails> getPictureDetails() {
        return pictureDetails;
    }

    public void setPictureDetails(List<PictureDetails> pictureDetails) {
        this.pictureDetails = pictureDetails;
    }

    public int getOrderMinimumQuantity() {
        return orderMinimumQuantity;
    }

    public void setOrderMinimumQuantity(int orderMinimumQuantity) {
        this.orderMinimumQuantity = orderMinimumQuantity;
    }

    public int getOrderMaximumQuantity() {
        return orderMaximumQuantity;
    }

    public void setOrderMaximumQuantity(int orderMaximumQuantity) {
        this.orderMaximumQuantity = orderMaximumQuantity;
    }

    public int getOnSale() {
        return onSale;
    }

    public void setOnSale(int onSale) {
        this.onSale = onSale;
    }

    public int getOldPrice() {
        return oldPrice;
    }

    public void setOldPrice(int oldPrice) {
        this.oldPrice = oldPrice;
    }

    public int getNotifyAdminForQuantityBelow() {
        return notifyAdminForQuantityBelow;
    }

    public void setNotifyAdminForQuantityBelow(int notifyAdminForQuantityBelow) {
        this.notifyAdminForQuantityBelow = notifyAdminForQuantityBelow;
    }

    public boolean getNotReturnable() {
        return notReturnable;
    }

    public void setNotReturnable(boolean notReturnable) {
        this.notReturnable = notReturnable;
    }

    public int getNotApprovedTotalReviews() {
        return notApprovedTotalReviews;
    }

    public void setNotApprovedTotalReviews(int notApprovedTotalReviews) {
        this.notApprovedTotalReviews = notApprovedTotalReviews;
    }

    public int getNotApprovedRatingSum() {
        return notApprovedRatingSum;
    }

    public void setNotApprovedRatingSum(int notApprovedRatingSum) {
        this.notApprovedRatingSum = notApprovedRatingSum;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMinStockQuantity() {
        return minStockQuantity;
    }

    public void setMinStockQuantity(int minStockQuantity) {
        this.minStockQuantity = minStockQuantity;
    }

    public boolean getMarkAsNew() {
        return markAsNew;
    }

    public void setMarkAsNew(boolean markAsNew) {
        this.markAsNew = markAsNew;
    }

    public int getLowStockActivityId() {
        return lowStockActivityId;
    }

    public void setLowStockActivityId(int lowStockActivityId) {
        this.lowStockActivityId = lowStockActivityId;
    }

    public boolean getLowStock() {
        return lowStock;
    }

    public void setLowStock(boolean lowStock) {
        this.lowStock = lowStock;
    }

    public boolean getIsShipEnabled() {
        return isShipEnabled;
    }

    public void setIsShipEnabled(boolean isShipEnabled) {
        this.isShipEnabled = isShipEnabled;
    }

    public boolean getIsFreeShipping() {
        return isFreeShipping;
    }

    public void setIsFreeShipping(boolean isFreeShipping) {
        this.isFreeShipping = isFreeShipping;
    }

    public List<String> getGenericAttributes() {
        return genericAttributes;
    }

    public void setGenericAttributes(List<String> genericAttributes) {
        this.genericAttributes = genericAttributes;
    }

    public boolean getDisplayStockQuantity() {
        return displayStockQuantity;
    }

    public void setDisplayStockQuantity(boolean displayStockQuantity) {
        this.displayStockQuantity = displayStockQuantity;
    }

    public boolean getDisplayStockAvailability() {
        return displayStockAvailability;
    }

    public void setDisplayStockAvailability(boolean displayStockAvailability) {
        this.displayStockAvailability = displayStockAvailability;
    }

    public int getDisplayOrderManufacturer() {
        return displayOrderManufacturer;
    }

    public void setDisplayOrderManufacturer(int displayOrderManufacturer) {
        this.displayOrderManufacturer = displayOrderManufacturer;
    }

    public int getDisplayOrderCategory() {
        return displayOrderCategory;
    }

    public void setDisplayOrderCategory(int displayOrderCategory) {
        this.displayOrderCategory = displayOrderCategory;
    }

    public int getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(int displayOrder) {
        this.displayOrder = displayOrder;
    }

    public List<String> getCustomerRoles() {
        return customerRoles;
    }

    public void setCustomerRoles(List<String> customerRoles) {
        this.customerRoles = customerRoles;
    }

    public int getCatalogPrice() {
        return catalogPrice;
    }

    public void setCatalogPrice(int catalogPrice) {
        this.catalogPrice = catalogPrice;
    }

    public int getBackorderModeId() {
        return backorderModeId;
    }

    public void setBackorderModeId(int backorderModeId) {
        this.backorderModeId = backorderModeId;
    }

    public boolean getAuctionEnded() {
        return auctionEnded;
    }

    public void setAuctionEnded(boolean auctionEnded) {
        this.auctionEnded = auctionEnded;
    }

    public int getApprovedTotalReviews() {
        return approvedTotalReviews;
    }

    public void setApprovedTotalReviews(int approvedTotalReviews) {
        this.approvedTotalReviews = approvedTotalReviews;
    }

    public int getApprovedRatingSum() {
        return approvedRatingSum;
    }

    public void setApprovedRatingSum(int approvedRatingSum) {
        this.approvedRatingSum = approvedRatingSum;
    }

    public boolean getAllowCustomerReviews() {
        return allowCustomerReviews;
    }

    public void setAllowCustomerReviews(boolean allowCustomerReviews) {
        this.allowCustomerReviews = allowCustomerReviews;
    }

    public boolean getAllowBackInStockSubscriptions() {
        return allowBackInStockSubscriptions;
    }

    public void setAllowBackInStockSubscriptions(boolean allowBackInStockSubscriptions) {
        this.allowBackInStockSubscriptions = allowBackInStockSubscriptions;
    }

    public int getAdditionalShippingCharge() {
        return additionalShippingCharge;
    }

    public void setAdditionalShippingCharge(int additionalShippingCharge) {
        this.additionalShippingCharge = additionalShippingCharge;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public static class ProductPictures {
        @SerializedName("mimeType")
        private String mimeType;
        @SerializedName("displayOrder")
        private int displayOrder;
        @SerializedName("pictureId")
        private String pictureId;
        @SerializedName("id")
        private String id;

        public String getMimeType() {
            return mimeType;
        }

        public void setMimeType(String mimeType) {
            this.mimeType = mimeType;
        }

        public int getDisplayOrder() {
            return displayOrder;
        }

        public void setDisplayOrder(int displayOrder) {
            this.displayOrder = displayOrder;
        }

        public String getPictureId() {
            return pictureId;
        }

        public void setPictureId(String pictureId) {
            this.pictureId = pictureId;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }

    public static class ProductCategories {
        @SerializedName("isFeaturedProduct")
        private boolean isFeaturedProduct;
        @SerializedName("categoryId")
        private String categoryId;
        @SerializedName("displayOrder")
        private int displayOrder;
        @SerializedName("_id")
        private String _id;

        public boolean getIsFeaturedProduct() {
            return isFeaturedProduct;
        }

        public void setIsFeaturedProduct(boolean isFeaturedProduct) {
            this.isFeaturedProduct = isFeaturedProduct;
        }

        public String getCategoryId() {
            return categoryId;
        }

        public void setCategoryId(String categoryId) {
            this.categoryId = categoryId;
        }

        public int getDisplayOrder() {
            return displayOrder;
        }

        public void setDisplayOrder(int displayOrder) {
            this.displayOrder = displayOrder;
        }

        public String get_id() {
            return _id;
        }

        public void set_id(String _id) {
            this._id = _id;
        }
    }

    public static class PictureDetails {
        @SerializedName("seoFilename")
        private String seoFilename;
        @SerializedName("mimeType")
        private String mimeType;
        @SerializedName("isNew")
        private boolean isNew;
        @SerializedName("genericAttributes")
        private List<String> genericAttributes;
        @SerializedName("_id")
        private String _id;

        public String getSeoFilename() {
            return seoFilename;
        }

        public void setSeoFilename(String seoFilename) {
            this.seoFilename = seoFilename;
        }

        public String getMimeType() {
            return mimeType;
        }

        public void setMimeType(String mimeType) {
            this.mimeType = mimeType;
        }

        public boolean getIsNew() {
            return isNew;
        }

        public void setIsNew(boolean isNew) {
            this.isNew = isNew;
        }

        public List<String> getGenericAttributes() {
            return genericAttributes;
        }

        public void setGenericAttributes(List<String> genericAttributes) {
            this.genericAttributes = genericAttributes;
        }

        public String get_id() {
            return _id;
        }

        public void set_id(String _id) {
            this._id = _id;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte(this.visibleIndividually ? (byte) 1 : (byte) 0);
        dest.writeInt(this.viewed);
        dest.writeByte(this.useMultipleWarehouses ? (byte) 1 : (byte) 0);
        dest.writeInt(this.stockQuantity);
        dest.writeInt(this.sold);
        dest.writeString(this.sku);
        dest.writeByte(this.showOnHomePage ? (byte) 1 : (byte) 0);
        dest.writeByte(this.shipSeparately ? (byte) 1 : (byte) 0);
        dest.writeString(this.seName);
        dest.writeByte(this.published ? (byte) 1 : (byte) 0);
        dest.writeInt(this.productTypeId);
        dest.writeString(this.productTemplateId);
        dest.writeStringList(this.productTags);
        dest.writeList(this.productPictures);
        dest.writeStringList(this.productManufacturers);
        dest.writeInt(this.productCost);
        dest.writeList(this.productCategories);
        dest.writeInt(this.price);
        dest.writeList(this.pictureDetails);
        dest.writeInt(this.orderMinimumQuantity);
        dest.writeInt(this.orderMaximumQuantity);
        dest.writeInt(this.onSale);
        dest.writeInt(this.oldPrice);
        dest.writeInt(this.notifyAdminForQuantityBelow);
        dest.writeByte(this.notReturnable ? (byte) 1 : (byte) 0);
        dest.writeInt(this.notApprovedTotalReviews);
        dest.writeInt(this.notApprovedRatingSum);
        dest.writeString(this.name);
        dest.writeInt(this.minStockQuantity);
        dest.writeByte(this.markAsNew ? (byte) 1 : (byte) 0);
        dest.writeInt(this.lowStockActivityId);
        dest.writeByte(this.lowStock ? (byte) 1 : (byte) 0);
        dest.writeByte(this.isShipEnabled ? (byte) 1 : (byte) 0);
        dest.writeByte(this.isFreeShipping ? (byte) 1 : (byte) 0);
        dest.writeStringList(this.genericAttributes);
        dest.writeByte(this.displayStockQuantity ? (byte) 1 : (byte) 0);
        dest.writeByte(this.displayStockAvailability ? (byte) 1 : (byte) 0);
        dest.writeInt(this.displayOrderManufacturer);
        dest.writeInt(this.displayOrderCategory);
        dest.writeInt(this.displayOrder);
        dest.writeStringList(this.customerRoles);
        dest.writeInt(this.catalogPrice);
        dest.writeInt(this.backorderModeId);
        dest.writeByte(this.auctionEnded ? (byte) 1 : (byte) 0);
        dest.writeInt(this.approvedTotalReviews);
        dest.writeInt(this.approvedRatingSum);
        dest.writeByte(this.allowCustomerReviews ? (byte) 1 : (byte) 0);
        dest.writeByte(this.allowBackInStockSubscriptions ? (byte) 1 : (byte) 0);
        dest.writeInt(this.additionalShippingCharge);
        dest.writeString(this._id);
    }

    public Content() {
    }

    protected Content(Parcel in) {
        this.visibleIndividually = in.readByte() != 0;
        this.viewed = in.readInt();
        this.useMultipleWarehouses = in.readByte() != 0;
        this.stockQuantity = in.readInt();
        this.sold = in.readInt();
        this.sku = in.readString();
        this.showOnHomePage = in.readByte() != 0;
        this.shipSeparately = in.readByte() != 0;
        this.seName = in.readString();
        this.published = in.readByte() != 0;
        this.productTypeId = in.readInt();
        this.productTemplateId = in.readString();
        this.productTags = in.createStringArrayList();
        this.productPictures = new ArrayList<ProductPictures>();
        in.readList(this.productPictures, ProductPictures.class.getClassLoader());
        this.productManufacturers = in.createStringArrayList();
        this.productCost = in.readInt();
        this.productCategories = new ArrayList<ProductCategories>();
        in.readList(this.productCategories, ProductCategories.class.getClassLoader());
        this.price = in.readInt();
        this.pictureDetails = new ArrayList<PictureDetails>();
        in.readList(this.pictureDetails, PictureDetails.class.getClassLoader());
        this.orderMinimumQuantity = in.readInt();
        this.orderMaximumQuantity = in.readInt();
        this.onSale = in.readInt();
        this.oldPrice = in.readInt();
        this.notifyAdminForQuantityBelow = in.readInt();
        this.notReturnable = in.readByte() != 0;
        this.notApprovedTotalReviews = in.readInt();
        this.notApprovedRatingSum = in.readInt();
        this.name = in.readString();
        this.minStockQuantity = in.readInt();
        this.markAsNew = in.readByte() != 0;
        this.lowStockActivityId = in.readInt();
        this.lowStock = in.readByte() != 0;
        this.isShipEnabled = in.readByte() != 0;
        this.isFreeShipping = in.readByte() != 0;
        this.genericAttributes = in.createStringArrayList();
        this.displayStockQuantity = in.readByte() != 0;
        this.displayStockAvailability = in.readByte() != 0;
        this.displayOrderManufacturer = in.readInt();
        this.displayOrderCategory = in.readInt();
        this.displayOrder = in.readInt();
        this.customerRoles = in.createStringArrayList();
        this.catalogPrice = in.readInt();
        this.backorderModeId = in.readInt();
        this.auctionEnded = in.readByte() != 0;
        this.approvedTotalReviews = in.readInt();
        this.approvedRatingSum = in.readInt();
        this.allowCustomerReviews = in.readByte() != 0;
        this.allowBackInStockSubscriptions = in.readByte() != 0;
        this.additionalShippingCharge = in.readInt();
        this._id = in.readString();
    }

    public static final Parcelable.Creator<Content> CREATOR = new Parcelable.Creator<Content>() {
        @Override
        public Content createFromParcel(Parcel source) {
            return new Content(source);
        }

        @Override
        public Content[] newArray(int size) {
            return new Content[size];
        }
    };
}
