package com.appprocessors.ecomstore.model.product;

import android.os.Parcel;
import android.os.Parcelable;

import com.appprocessors.ecomstore.model.customer.CustomerRoles;
import com.appprocessors.ecomstore.model.customer.GenericAttributes;
import com.appprocessors.ecomstore.model.picture.Picture;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Product implements Parcelable {

    @SerializedName("_id")
    private String _id;
    @SerializedName("genericAttributes")
    private List<GenericAttributes> genericAttributes = null;
    @SerializedName("productTypeId")
    private long productTypeId;
    @SerializedName("parentGroupedProductId")
    private String parentGroupedProductId;
    @SerializedName("visibleIndividually")
    private boolean visibleIndividually;
    @SerializedName("name")
    private String name;
    @SerializedName("seName")
    private String seName;
    @SerializedName("shortDescription")
    private String shortDescription;
    @SerializedName("fullDescription")
    private String fullDescription;
    @SerializedName("adminComment")
    private String adminComment;
    @SerializedName("productTemplateId")
    private String productTemplateId;
    @SerializedName("vendorId")
    private String vendorId;
    @SerializedName("showOnHomePage")
    private boolean showOnHomePage;
    @SerializedName("metaKeywords")
    private String metaKeywords;
    @SerializedName("metaDescription")
    private String metaDescription;
    @SerializedName("metaTitle")
    private String metaTitle;
    @SerializedName("allowCustomerReviews")
    private boolean allowCustomerReviews;
    @SerializedName("approvedRatingSum")
    private long approvedRatingSum;
    @SerializedName("notApprovedRatingSum")
    private long notApprovedRatingSum;
    @SerializedName("approvedTotalReviews")
    private long approvedTotalReviews;
    @SerializedName("notApprovedTotalReviews")
    private long notApprovedTotalReviews;
    @SerializedName("subjectToAcl")
    private boolean subjectToAcl;
    @SerializedName("customerRoles")
    private List<CustomerRoles> customerRoles = null;
    @SerializedName("limitedToStores")
    private boolean limitedToStores;
    @SerializedName("stores")
    private List<String> stores = null;
    @SerializedName("externalId")
    private String externalId;
    @SerializedName("sku")
    private String sku;
    @SerializedName("manufacturerPartNumber")
    private String manufacturerPartNumber;
    @SerializedName("gtin")
    private String gtin;
    @SerializedName("isGiftCard")
    private boolean isGiftCard;
    @SerializedName("giftCardTypeId")
    private long giftCardTypeId;
    @SerializedName("overriddenGiftCardAmount")
    private String overriddenGiftCardAmount;
    @SerializedName("requireOtherProducts")
    private boolean requireOtherProducts;
    @SerializedName("requiredProductIds")
    private String requiredProductIds;
    @SerializedName("automaticallyAddRequiredProducts")
    private boolean automaticallyAddRequiredProducts;
    @SerializedName("isDownload")
    private boolean isDownload;
    @SerializedName("downloadId")
    private String downloadId;
    @SerializedName("unlimitedDownloads")
    private boolean unlimitedDownloads;
    @SerializedName("maxNumberOfDownloads")
    private long maxNumberOfDownloads;
    @SerializedName("downloadExpirationDays")
    private String downloadExpirationDays;
    @SerializedName("downloadActivationTypeId")
    private long downloadActivationTypeId;
    @SerializedName("hasSampleDownload")
    private boolean hasSampleDownload;
    @SerializedName("sampleDownloadId")
    private String sampleDownloadId;
    @SerializedName("hasUserAgreement")
    private boolean hasUserAgreement;
    @SerializedName("userAgreementText")
    private String userAgreementText;
    @SerializedName("isRecurring")
    private boolean isRecurring;
    @SerializedName("recurringCycleLength")
    private long recurringCycleLength;
    @SerializedName("recurringCyclePeriodId")
    private long recurringCyclePeriodId;
    @SerializedName("recurringTotalCycles")
    private long recurringTotalCycles;
    @SerializedName("incBothDate")
    private boolean incBothDate;
    @SerializedName("interval")
    private long interval;
    @SerializedName("intervalUnitId")
    private long intervalUnitId;
    @SerializedName("isShipEnabled")
    private boolean isShipEnabled;
    @SerializedName("isFreeShipping")
    private boolean isFreeShipping;
    @SerializedName("shipSeparately")
    private boolean shipSeparately;
    @SerializedName("additionalShippingCharge")
    private double additionalShippingCharge;
    @SerializedName("deliveryDateId")
    private String deliveryDateId;
    @SerializedName("isTaxExempt")
    private boolean isTaxExempt;
    @SerializedName("taxCategoryId")
    private String taxCategoryId;
    @SerializedName("isTelecommunicationsOrBroadcastingOrElectronicServices")
    private boolean isTelecommunicationsOrBroadcastingOrElectronicServices;
    @SerializedName("manageInventoryMethodId")
    private long manageInventoryMethodId;
    @SerializedName("useMultipleWarehouses")
    private boolean useMultipleWarehouses;
    @SerializedName("warehouseId")
    private String warehouseId;
    @SerializedName("stockQuantity")
    private long stockQuantity;
    @SerializedName("displayStockAvailability")
    private boolean displayStockAvailability;
    @SerializedName("displayStockQuantity")
    private boolean displayStockQuantity;
    @SerializedName("minStockQuantity")
    private long minStockQuantity;
    @SerializedName("lowStock")
    private boolean lowStock;
    @SerializedName("lowStockActivityId")
    private long lowStockActivityId;
    @SerializedName("notifyAdminForQuantityBelow")
    private long notifyAdminForQuantityBelow;
    @SerializedName("backorderModeId")
    private long backorderModeId;
    @SerializedName("allowBackInStockSubscriptions")
    private boolean allowBackInStockSubscriptions;
    @SerializedName("orderMinimumQuantity")
    private long orderMinimumQuantity;
    @SerializedName("orderMaximumQuantity")
    private long orderMaximumQuantity;
    @SerializedName("allowedQuantities")
    private String allowedQuantities;
    @SerializedName("allowAddingOnlyExistingAttributeCombinations")
    private boolean allowAddingOnlyExistingAttributeCombinations;
    @SerializedName("notReturnable")
    private boolean notReturnable;
    @SerializedName("disableBuyButton")
    private boolean disableBuyButton;
    @SerializedName("disableWishlistButton")
    private boolean disableWishlistButton;
    @SerializedName("availableForPreOrder")
    private boolean availableForPreOrder;
    @SerializedName("preOrderAvailabilityStartDateTimeUtc")
    private Object preOrderAvailabilityStartDateTimeUtc;
    @SerializedName("callForPrice")
    private boolean callForPrice;
    @SerializedName("price")
    private double price;
    @SerializedName("oldPrice")
    private double oldPrice;
    @SerializedName("catalogPrice")
    private double catalogPrice;
    @SerializedName("productCost")
    private double productCost;
    @SerializedName("customerEntersPrice")
    private boolean customerEntersPrice;
    @SerializedName("minimumCustomerEnteredPrice")
    private double minimumCustomerEnteredPrice;
    @SerializedName("maximumCustomerEnteredPrice")
    private double maximumCustomerEnteredPrice;
    @SerializedName("basepriceEnabled")
    private boolean basepriceEnabled;
    @SerializedName("basepriceAmount")
    private double basepriceAmount;
    @SerializedName("basepriceUnitId")
    private String basepriceUnitId;
    @SerializedName("basepriceBaseAmount")
    private double basepriceBaseAmount;
    @SerializedName("basepriceBaseUnitId")
    private String basepriceBaseUnitId;
    @SerializedName("unitId")
    private Object unitId;
    @SerializedName("markAsNew")
    private boolean markAsNew;
    @SerializedName("markAsNewStartDateTimeUtc")
    private String markAsNewStartDateTimeUtc;
    @SerializedName("markAsNewEndDateTimeUtc")
    private String markAsNewEndDateTimeUtc;
    @SerializedName("weight")
    private double weight;
    @SerializedName("length")
    private double length;
    @SerializedName("width")
    private double width;
    @SerializedName("height")
    private double height;
    @SerializedName("availableStartDateTimeUtc")
    private String availableStartDateTimeUtc;
    @SerializedName("availableEndDateTimeUtc")
    private String availableEndDateTimeUtc;
    @SerializedName("startPrice")
    private double startPrice;
    @SerializedName("highestBid")
    private double highestBid;
    @SerializedName("highestBidder")
    private String highestBidder;
    @SerializedName("auctionEnded")
    private boolean auctionEnded;
    @SerializedName("displayOrder")
    private long displayOrder;
    @SerializedName("displayOrderCategory")
    private long displayOrderCategory;
    @SerializedName("displayOrderManufacturer")
    private long displayOrderManufacturer;
    @SerializedName("published")
    private boolean published;
    @SerializedName("createdOnUtc")
    private String createdOnUtc;
    @SerializedName("updatedOnUtc")
    private String updatedOnUtc;
    @SerializedName("sold")
    private long sold;
    @SerializedName("viewed")
    private long viewed;
    @SerializedName("onSale")
    private long onSale;
    @SerializedName("flag")
    private Object flag;
    @SerializedName("locales")
    private List<String> locales = null;
    @SerializedName("poductCategories")
    private List<ProductCategories> productCategories = null;
    @SerializedName("productManufacturers")
    private List<String> productManufacturers = null;
    @SerializedName("productPictures")
    private List<ProductPictures> productPictures = null;
    @SerializedName("productSpecificationAttributes")
    private List<String> productSpecificationAttributes = null;
    @SerializedName("productTags")
    private List<String> productTags = null;
    @SerializedName("productAttributeMappings")
    private List<String> productAttributeMappings = null;
    @SerializedName("productAttributeCombinations")
    private List<String> productAttributeCombinations = null;
    @SerializedName("pierPrices")
    private List<String> tierPrices = null;
    @SerializedName("appliedDiscounts")
    private List<String> appliedDiscounts = null;
    @SerializedName("productWarehouseInventory")
    private List<String> productWarehouseInventory = null;
    @SerializedName("crossSellProduct")
    private List<String> crossSellProduct = null;
    @SerializedName("relatedProducts")
    private List<String> relatedProducts = null;
    @SerializedName("bundleProducts")
    private List<String> bundleProducts = null;
    @SerializedName("pictureDetails")
    private List<Picture> PictureDetails = null;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this._id);
        dest.writeList(this.genericAttributes);
        dest.writeLong(this.productTypeId);
        dest.writeString(this.parentGroupedProductId);
        dest.writeByte(this.visibleIndividually ? (byte) 1 : (byte) 0);
        dest.writeString(this.name);
        dest.writeString(this.seName);
        dest.writeString(this.shortDescription);
        dest.writeString(this.fullDescription);
        dest.writeString(this.adminComment);
        dest.writeString(this.productTemplateId);
        dest.writeString(this.vendorId);
        dest.writeByte(this.showOnHomePage ? (byte) 1 : (byte) 0);
        dest.writeString(this.metaKeywords);
        dest.writeString(this.metaDescription);
        dest.writeString(this.metaTitle);
        dest.writeByte(this.allowCustomerReviews ? (byte) 1 : (byte) 0);
        dest.writeLong(this.approvedRatingSum);
        dest.writeLong(this.notApprovedRatingSum);
        dest.writeLong(this.approvedTotalReviews);
        dest.writeLong(this.notApprovedTotalReviews);
        dest.writeByte(this.subjectToAcl ? (byte) 1 : (byte) 0);
        dest.writeList(this.customerRoles);
        dest.writeByte(this.limitedToStores ? (byte) 1 : (byte) 0);
        dest.writeStringList(this.stores);
        dest.writeString(this.externalId);
        dest.writeString(this.sku);
        dest.writeString(this.manufacturerPartNumber);
        dest.writeString(this.gtin);
        dest.writeByte(this.isGiftCard ? (byte) 1 : (byte) 0);
        dest.writeLong(this.giftCardTypeId);
        dest.writeString(this.overriddenGiftCardAmount);
        dest.writeByte(this.requireOtherProducts ? (byte) 1 : (byte) 0);
        dest.writeString(this.requiredProductIds);
        dest.writeByte(this.automaticallyAddRequiredProducts ? (byte) 1 : (byte) 0);
        dest.writeByte(this.isDownload ? (byte) 1 : (byte) 0);
        dest.writeString(this.downloadId);
        dest.writeByte(this.unlimitedDownloads ? (byte) 1 : (byte) 0);
        dest.writeLong(this.maxNumberOfDownloads);
        dest.writeString(this.downloadExpirationDays);
        dest.writeLong(this.downloadActivationTypeId);
        dest.writeByte(this.hasSampleDownload ? (byte) 1 : (byte) 0);
        dest.writeString(this.sampleDownloadId);
        dest.writeByte(this.hasUserAgreement ? (byte) 1 : (byte) 0);
        dest.writeString(this.userAgreementText);
        dest.writeByte(this.isRecurring ? (byte) 1 : (byte) 0);
        dest.writeLong(this.recurringCycleLength);
        dest.writeLong(this.recurringCyclePeriodId);
        dest.writeLong(this.recurringTotalCycles);
        dest.writeByte(this.incBothDate ? (byte) 1 : (byte) 0);
        dest.writeLong(this.interval);
        dest.writeLong(this.intervalUnitId);
        dest.writeByte(this.isShipEnabled ? (byte) 1 : (byte) 0);
        dest.writeByte(this.isFreeShipping ? (byte) 1 : (byte) 0);
        dest.writeByte(this.shipSeparately ? (byte) 1 : (byte) 0);
        dest.writeDouble(this.additionalShippingCharge);
        dest.writeString(this.deliveryDateId);
        dest.writeByte(this.isTaxExempt ? (byte) 1 : (byte) 0);
        dest.writeString(this.taxCategoryId);
        dest.writeByte(this.isTelecommunicationsOrBroadcastingOrElectronicServices ? (byte) 1 : (byte) 0);
        dest.writeLong(this.manageInventoryMethodId);
        dest.writeByte(this.useMultipleWarehouses ? (byte) 1 : (byte) 0);
        dest.writeString(this.warehouseId);
        dest.writeLong(this.stockQuantity);
        dest.writeByte(this.displayStockAvailability ? (byte) 1 : (byte) 0);
        dest.writeByte(this.displayStockQuantity ? (byte) 1 : (byte) 0);
        dest.writeLong(this.minStockQuantity);
        dest.writeByte(this.lowStock ? (byte) 1 : (byte) 0);
        dest.writeLong(this.lowStockActivityId);
        dest.writeLong(this.notifyAdminForQuantityBelow);
        dest.writeLong(this.backorderModeId);
        dest.writeByte(this.allowBackInStockSubscriptions ? (byte) 1 : (byte) 0);
        dest.writeLong(this.orderMinimumQuantity);
        dest.writeLong(this.orderMaximumQuantity);
        dest.writeString(this.allowedQuantities);
        dest.writeByte(this.allowAddingOnlyExistingAttributeCombinations ? (byte) 1 : (byte) 0);
        dest.writeByte(this.notReturnable ? (byte) 1 : (byte) 0);
        dest.writeByte(this.disableBuyButton ? (byte) 1 : (byte) 0);
        dest.writeByte(this.disableWishlistButton ? (byte) 1 : (byte) 0);
        dest.writeByte(this.availableForPreOrder ? (byte) 1 : (byte) 0);
        dest.writeParcelable((Parcelable) this.preOrderAvailabilityStartDateTimeUtc, flags);
        dest.writeByte(this.callForPrice ? (byte) 1 : (byte) 0);
        dest.writeDouble(this.price);
        dest.writeDouble(this.oldPrice);
        dest.writeDouble(this.catalogPrice);
        dest.writeDouble(this.productCost);
        dest.writeByte(this.customerEntersPrice ? (byte) 1 : (byte) 0);
        dest.writeDouble(this.minimumCustomerEnteredPrice);
        dest.writeDouble(this.maximumCustomerEnteredPrice);
        dest.writeByte(this.basepriceEnabled ? (byte) 1 : (byte) 0);
        dest.writeDouble(this.basepriceAmount);
        dest.writeString(this.basepriceUnitId);
        dest.writeDouble(this.basepriceBaseAmount);
        dest.writeString(this.basepriceBaseUnitId);
        dest.writeParcelable((Parcelable) this.unitId, flags);
        dest.writeByte(this.markAsNew ? (byte) 1 : (byte) 0);
        dest.writeString(this.markAsNewStartDateTimeUtc);
        dest.writeString(this.markAsNewEndDateTimeUtc);
        dest.writeDouble(this.weight);
        dest.writeDouble(this.length);
        dest.writeDouble(this.width);
        dest.writeDouble(this.height);
        dest.writeString(this.availableStartDateTimeUtc);
        dest.writeString(this.availableEndDateTimeUtc);
        dest.writeDouble(this.startPrice);
        dest.writeDouble(this.highestBid);
        dest.writeString(this.highestBidder);
        dest.writeByte(this.auctionEnded ? (byte) 1 : (byte) 0);
        dest.writeLong(this.displayOrder);
        dest.writeLong(this.displayOrderCategory);
        dest.writeLong(this.displayOrderManufacturer);
        dest.writeByte(this.published ? (byte) 1 : (byte) 0);
        dest.writeString(this.createdOnUtc);
        dest.writeString(this.updatedOnUtc);
        dest.writeLong(this.sold);
        dest.writeLong(this.viewed);
        dest.writeLong(this.onSale);
        dest.writeParcelable((Parcelable) this.flag, flags);
        dest.writeStringList(this.locales);
        dest.writeList(this.productCategories);
        dest.writeStringList(this.productManufacturers);
        dest.writeList(this.productPictures);
        dest.writeStringList(this.productSpecificationAttributes);
        dest.writeStringList(this.productTags);
        dest.writeStringList(this.productAttributeMappings);
        dest.writeStringList(this.productAttributeCombinations);
        dest.writeStringList(this.tierPrices);
        dest.writeStringList(this.appliedDiscounts);
        dest.writeStringList(this.productWarehouseInventory);
        dest.writeStringList(this.crossSellProduct);
        dest.writeStringList(this.relatedProducts);
        dest.writeStringList(this.bundleProducts);
    }

    protected Product(Parcel in) {
        this._id = in.readString();
        this.genericAttributes = new ArrayList<GenericAttributes>();
        in.readList(this.genericAttributes, GenericAttributes.class.getClassLoader());
        this.productTypeId = in.readLong();
        this.parentGroupedProductId = in.readString();
        this.visibleIndividually = in.readByte() != 0;
        this.name = in.readString();
        this.seName = in.readString();
        this.shortDescription = in.readString();
        this.fullDescription = in.readString();
        this.adminComment = in.readString();
        this.productTemplateId = in.readString();
        this.vendorId = in.readString();
        this.showOnHomePage = in.readByte() != 0;
        this.metaKeywords = in.readString();
        this.metaDescription = in.readString();
        this.metaTitle = in.readString();
        this.allowCustomerReviews = in.readByte() != 0;
        this.approvedRatingSum = in.readLong();
        this.notApprovedRatingSum = in.readLong();
        this.approvedTotalReviews = in.readLong();
        this.notApprovedTotalReviews = in.readLong();
        this.subjectToAcl = in.readByte() != 0;
        this.customerRoles = new ArrayList<CustomerRoles>();
        in.readList(this.customerRoles, CustomerRoles.class.getClassLoader());
        this.limitedToStores = in.readByte() != 0;
        this.stores = in.createStringArrayList();
        this.externalId = in.readString();
        this.sku = in.readString();
        this.manufacturerPartNumber = in.readString();
        this.gtin = in.readString();
        this.isGiftCard = in.readByte() != 0;
        this.giftCardTypeId = in.readLong();
        this.overriddenGiftCardAmount = in.readString();
        this.requireOtherProducts = in.readByte() != 0;
        this.requiredProductIds = in.readString();
        this.automaticallyAddRequiredProducts = in.readByte() != 0;
        this.isDownload = in.readByte() != 0;
        this.downloadId = in.readString();
        this.unlimitedDownloads = in.readByte() != 0;
        this.maxNumberOfDownloads = in.readLong();
        this.downloadExpirationDays = in.readString();
        this.downloadActivationTypeId = in.readLong();
        this.hasSampleDownload = in.readByte() != 0;
        this.sampleDownloadId = in.readString();
        this.hasUserAgreement = in.readByte() != 0;
        this.userAgreementText = in.readString();
        this.isRecurring = in.readByte() != 0;
        this.recurringCycleLength = in.readLong();
        this.recurringCyclePeriodId = in.readLong();
        this.recurringTotalCycles = in.readLong();
        this.incBothDate = in.readByte() != 0;
        this.interval = in.readLong();
        this.intervalUnitId = in.readLong();
        this.isShipEnabled = in.readByte() != 0;
        this.isFreeShipping = in.readByte() != 0;
        this.shipSeparately = in.readByte() != 0;
        this.additionalShippingCharge = in.readDouble();
        this.deliveryDateId = in.readString();
        this.isTaxExempt = in.readByte() != 0;
        this.taxCategoryId = in.readString();
        this.isTelecommunicationsOrBroadcastingOrElectronicServices = in.readByte() != 0;
        this.manageInventoryMethodId = in.readLong();
        this.useMultipleWarehouses = in.readByte() != 0;
        this.warehouseId = in.readString();
        this.stockQuantity = in.readLong();
        this.displayStockAvailability = in.readByte() != 0;
        this.displayStockQuantity = in.readByte() != 0;
        this.minStockQuantity = in.readLong();
        this.lowStock = in.readByte() != 0;
        this.lowStockActivityId = in.readLong();
        this.notifyAdminForQuantityBelow = in.readLong();
        this.backorderModeId = in.readLong();
        this.allowBackInStockSubscriptions = in.readByte() != 0;
        this.orderMinimumQuantity = in.readLong();
        this.orderMaximumQuantity = in.readLong();
        this.allowedQuantities = in.readString();
        this.allowAddingOnlyExistingAttributeCombinations = in.readByte() != 0;
        this.notReturnable = in.readByte() != 0;
        this.disableBuyButton = in.readByte() != 0;
        this.disableWishlistButton = in.readByte() != 0;
        this.availableForPreOrder = in.readByte() != 0;
        this.preOrderAvailabilityStartDateTimeUtc = in.readParcelable(Object.class.getClassLoader());
        this.callForPrice = in.readByte() != 0;
        this.price = in.readDouble();
        this.oldPrice = in.readDouble();
        this.catalogPrice = in.readDouble();
        this.productCost = in.readDouble();
        this.customerEntersPrice = in.readByte() != 0;
        this.minimumCustomerEnteredPrice = in.readDouble();
        this.maximumCustomerEnteredPrice = in.readDouble();
        this.basepriceEnabled = in.readByte() != 0;
        this.basepriceAmount = in.readDouble();
        this.basepriceUnitId = in.readString();
        this.basepriceBaseAmount = in.readDouble();
        this.basepriceBaseUnitId = in.readString();
        this.unitId = in.readParcelable(Object.class.getClassLoader());
        this.markAsNew = in.readByte() != 0;
        this.markAsNewStartDateTimeUtc = in.readString();
        this.markAsNewEndDateTimeUtc = in.readString();
        this.weight = in.readDouble();
        this.length = in.readDouble();
        this.width = in.readDouble();
        this.height = in.readDouble();
        this.availableStartDateTimeUtc = in.readString();
        this.availableEndDateTimeUtc = in.readString();
        this.startPrice = in.readDouble();
        this.highestBid = in.readDouble();
        this.highestBidder = in.readString();
        this.auctionEnded = in.readByte() != 0;
        this.displayOrder = in.readLong();
        this.displayOrderCategory = in.readLong();
        this.displayOrderManufacturer = in.readLong();
        this.published = in.readByte() != 0;
        this.createdOnUtc = in.readString();
        this.updatedOnUtc = in.readString();
        this.sold = in.readLong();
        this.viewed = in.readLong();
        this.onSale = in.readLong();
        this.flag = in.readParcelable(Object.class.getClassLoader());
        this.locales = in.createStringArrayList();
        this.productCategories = new ArrayList<ProductCategories>();
        in.readList(this.productCategories, ProductCategories.class.getClassLoader());
        this.productManufacturers = in.createStringArrayList();
        this.productPictures = new ArrayList<ProductPictures>();
        in.readList(this.productPictures, ProductPictures.class.getClassLoader());
        this.productSpecificationAttributes = in.createStringArrayList();
        this.productTags = in.createStringArrayList();
        this.productAttributeMappings = in.createStringArrayList();
        this.productAttributeCombinations = in.createStringArrayList();
        this.tierPrices = in.createStringArrayList();
        this.appliedDiscounts = in.createStringArrayList();
        this.productWarehouseInventory = in.createStringArrayList();
        this.crossSellProduct = in.createStringArrayList();
        this.relatedProducts = in.createStringArrayList();
        this.bundleProducts = in.createStringArrayList();
    }

    public static final Parcelable.Creator<Product> CREATOR = new Parcelable.Creator<Product>() {
        @Override
        public Product createFromParcel(Parcel source) {
            return new Product(source);
        }

        @Override
        public Product[] newArray(int size) {
            return new Product[size];
        }
    };
}
