package com.appprocessors.ecomstore.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;
public class ProductDetails implements Parcelable {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("productName")
    @Expose
    private String productName;
    @SerializedName("mrp")
    @Expose
    private String mrp;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("imageMain")
    @Expose
    private String imageMain;
    @SerializedName("menuid")
    @Expose
    private String menuid;
    @SerializedName("soldBy")
    @Expose
    private String soldBy;
    @SerializedName("productAverageRating")
    @Expose
    private String productAverageRating;
    @SerializedName("productNoOfRatings")
    @Expose
    private String productNoOfRatings;
    @SerializedName("sellerName")
    @Expose
    private String sellerName;
    @SerializedName("createdDate")
    @Expose
    private String createdDate;
    @SerializedName("productCode")
    @Expose
    private String productCode;
    @SerializedName("productCategoryName")
    @Expose
    private String productCategoryName;
    @SerializedName("maincategory")
    @Expose
    private List<String> maincategory = null;
    @SerializedName("subCategory")
    @Expose
    private String subCategory;
    @SerializedName("shippingFee")
    @Expose
    private String shippingFee;
    @SerializedName("returnsInDays")
    @Expose
    private String returnsInDays;
    @SerializedName("estoreAssured")
    @Expose
    private Boolean estoreAssured;
    @SerializedName("brandCertifiedSeller")
    @Expose
    private Boolean brandCertifiedSeller;
    @SerializedName("genuineProduct")
    @Expose
    private Boolean genuineProduct;
    @SerializedName("productNoOfReviews")
    @Expose
    private String productNoOfReviews;
    @SerializedName("categoryPath")
    @Expose
    private List<CategoryPath> categoryPath = null;
    @SerializedName("productHighlight")
    @Expose
    private List<String> productHighlight = null;
    @SerializedName("productImages")
    @Expose
    private List<String> productImages = null;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("brand")
    @Expose
    private String brand;
    @SerializedName("packageContet")
    @Expose
    private String packageContet;
    @SerializedName("disclaimer")
    @Expose
    private String disclaimer;
    @SerializedName("quantity")
    @Expose
    private String quantity;
    @SerializedName("idealFor")
    @Expose
    private String idealFor;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("expiryDate")
    @Expose
    private String expiryDate;
    @SerializedName("bestBefore")
    @Expose
    private String bestBefore;
    @SerializedName("inStock")
    @Expose
    private Boolean inStock;
    @SerializedName("totolItems")
    @Expose
    private String totolItems;
    @SerializedName("cartItems")
    @Expose
    private String cartItems;
    @SerializedName("leftItems")
    @Expose
    private String leftItems;
    @SerializedName("codAvailable")
    @Expose
    private Boolean codAvailable;
    @SerializedName("emiAvailable")
    @Expose
    private Boolean emiAvailable;
    @SerializedName("cashBack")
    @Expose
    private Boolean cashBack;
    @SerializedName("offers")
    @Expose
    private List<Object> offers = null;
    @SerializedName("size")
    @Expose
    private String size;
    @SerializedName("color")
    @Expose
    private String color;
    @SerializedName("sizeUnit")
    @Expose
    private String sizeUnit;
    @SerializedName("dimensions")
    @Expose
    private Dimensions dimensions;
    @SerializedName("colorVariants")
    @Expose
    private List<String> colorVariants = null;
    @SerializedName("sizeVariants")
    @Expose
    private List<String> sizeVariants = null;
    @SerializedName("sellerAverageRating")
    @Expose
    private String sellerAverageRating;
    @SerializedName("sellerNoOfRatings")
    @Expose
    private String sellerNoOfRatings;
    @SerializedName("sellerNoOfReviews")
    @Expose
    private String sellerNoOfReviews;
    @SerializedName("lifeStyleInfo")
    @Expose
    private LifeStyleInfo lifeStyleInfo;
    @SerializedName("replaceAvailable")
    @Expose
    private Boolean replaceAvailable;
    @SerializedName("cancellationAvailable")
    @Expose
    private Boolean cancellationAvailable;
    @SerializedName("installationAvailable")
    @Expose
    private Boolean installationAvailable;
    @SerializedName("shippingFree")
    @Expose
    private Boolean shippingFree;
    @SerializedName("returnAvailable")
    @Expose
    private Boolean returnAvailable;
    @SerializedName("available")
    @Expose
    private Boolean available;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getMrp() {
        return mrp;
    }

    public void setMrp(String mrp) {
        this.mrp = mrp;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getImageMain() {
        return imageMain;
    }

    public void setImageMain(String imageMain) {
        this.imageMain = imageMain;
    }

    public String getMenuid() {
        return menuid;
    }

    public void setMenuid(String menuid) {
        this.menuid = menuid;
    }

    public String getSoldBy() {
        return soldBy;
    }

    public void setSoldBy(String soldBy) {
        this.soldBy = soldBy;
    }

    public String getProductAverageRating() {
        return productAverageRating;
    }

    public void setProductAverageRating(String productAverageRating) {
        this.productAverageRating = productAverageRating;
    }

    public String getProductNoOfRatings() {
        return productNoOfRatings;
    }

    public void setProductNoOfRatings(String productNoOfRatings) {
        this.productNoOfRatings = productNoOfRatings;
    }

    public String getSellerName() {
        return sellerName;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getProductCategoryName() {
        return productCategoryName;
    }

    public void setProductCategoryName(String productCategoryName) {
        this.productCategoryName = productCategoryName;
    }

    public List<String> getMaincategory() {
        return maincategory;
    }

    public void setMaincategory(List<String> maincategory) {
        this.maincategory = maincategory;
    }

    public String getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(String subCategory) {
        this.subCategory = subCategory;
    }

    public String getShippingFee() {
        return shippingFee;
    }

    public void setShippingFee(String shippingFee) {
        this.shippingFee = shippingFee;
    }

    public String getReturnsInDays() {
        return returnsInDays;
    }

    public void setReturnsInDays(String returnsInDays) {
        this.returnsInDays = returnsInDays;
    }

    public Boolean getEstoreAssured() {
        return estoreAssured;
    }

    public void setEstoreAssured(Boolean estoreAssured) {
        this.estoreAssured = estoreAssured;
    }

    public Boolean getBrandCertifiedSeller() {
        return brandCertifiedSeller;
    }

    public void setBrandCertifiedSeller(Boolean brandCertifiedSeller) {
        this.brandCertifiedSeller = brandCertifiedSeller;
    }

    public Boolean getGenuineProduct() {
        return genuineProduct;
    }

    public void setGenuineProduct(Boolean genuineProduct) {
        this.genuineProduct = genuineProduct;
    }

    public String getProductNoOfReviews() {
        return productNoOfReviews;
    }

    public void setProductNoOfReviews(String productNoOfReviews) {
        this.productNoOfReviews = productNoOfReviews;
    }

    public List<CategoryPath> getCategoryPath() {
        return categoryPath;
    }

    public void setCategoryPath(List<CategoryPath> categoryPath) {
        this.categoryPath = categoryPath;
    }

    public List<String> getProductHighlight() {
        return productHighlight;
    }

    public void setProductHighlight(List<String> productHighlight) {
        this.productHighlight = productHighlight;
    }

    public List<String> getProductImages() {
        return productImages;
    }

    public void setProductImages(List<String> productImages) {
        this.productImages = productImages;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getPackageContet() {
        return packageContet;
    }

    public void setPackageContet(String packageContet) {
        this.packageContet = packageContet;
    }

    public String getDisclaimer() {
        return disclaimer;
    }

    public void setDisclaimer(String disclaimer) {
        this.disclaimer = disclaimer;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getIdealFor() {
        return idealFor;
    }

    public void setIdealFor(String idealFor) {
        this.idealFor = idealFor;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getBestBefore() {
        return bestBefore;
    }

    public void setBestBefore(String bestBefore) {
        this.bestBefore = bestBefore;
    }

    public Boolean getInStock() {
        return inStock;
    }

    public void setInStock(Boolean inStock) {
        this.inStock = inStock;
    }

    public String getTotolItems() {
        return totolItems;
    }

    public void setTotolItems(String totolItems) {
        this.totolItems = totolItems;
    }

    public String getCartItems() {
        return cartItems;
    }

    public void setCartItems(String cartItems) {
        this.cartItems = cartItems;
    }

    public String getLeftItems() {
        return leftItems;
    }

    public void setLeftItems(String leftItems) {
        this.leftItems = leftItems;
    }

    public Boolean getCodAvailable() {
        return codAvailable;
    }

    public void setCodAvailable(Boolean codAvailable) {
        this.codAvailable = codAvailable;
    }

    public Boolean getEmiAvailable() {
        return emiAvailable;
    }

    public void setEmiAvailable(Boolean emiAvailable) {
        this.emiAvailable = emiAvailable;
    }

    public Boolean getCashBack() {
        return cashBack;
    }

    public void setCashBack(Boolean cashBack) {
        this.cashBack = cashBack;
    }

    public List<Object> getOffers() {
        return offers;
    }

    public void setOffers(List<Object> offers) {
        this.offers = offers;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getSizeUnit() {
        return sizeUnit;
    }

    public void setSizeUnit(String sizeUnit) {
        this.sizeUnit = sizeUnit;
    }

    public Dimensions getDimensions() {
        return dimensions;
    }

    public void setDimensions(Dimensions dimensions) {
        this.dimensions = dimensions;
    }

    public List<String> getColorVariants() {
        return colorVariants;
    }

    public void setColorVariants(List<String> colorVariants) {
        this.colorVariants = colorVariants;
    }

    public List<String> getSizeVariants() {
        return sizeVariants;
    }

    public void setSizeVariants(List<String> sizeVariants) {
        this.sizeVariants = sizeVariants;
    }

    public String getSellerAverageRating() {
        return sellerAverageRating;
    }

    public void setSellerAverageRating(String sellerAverageRating) {
        this.sellerAverageRating = sellerAverageRating;
    }

    public String getSellerNoOfRatings() {
        return sellerNoOfRatings;
    }

    public void setSellerNoOfRatings(String sellerNoOfRatings) {
        this.sellerNoOfRatings = sellerNoOfRatings;
    }

    public String getSellerNoOfReviews() {
        return sellerNoOfReviews;
    }

    public void setSellerNoOfReviews(String sellerNoOfReviews) {
        this.sellerNoOfReviews = sellerNoOfReviews;
    }

    public LifeStyleInfo getLifeStyleInfo() {
        return lifeStyleInfo;
    }

    public void setLifeStyleInfo(LifeStyleInfo lifeStyleInfo) {
        this.lifeStyleInfo = lifeStyleInfo;
    }

    public Boolean getReplaceAvailable() {
        return replaceAvailable;
    }

    public void setReplaceAvailable(Boolean replaceAvailable) {
        this.replaceAvailable = replaceAvailable;
    }

    public Boolean getCancellationAvailable() {
        return cancellationAvailable;
    }

    public void setCancellationAvailable(Boolean cancellationAvailable) {
        this.cancellationAvailable = cancellationAvailable;
    }

    public Boolean getInstallationAvailable() {
        return installationAvailable;
    }

    public void setInstallationAvailable(Boolean installationAvailable) {
        this.installationAvailable = installationAvailable;
    }

    public Boolean getShippingFree() {
        return shippingFree;
    }

    public void setShippingFree(Boolean shippingFree) {
        this.shippingFree = shippingFree;
    }

    public Boolean getReturnAvailable() {
        return returnAvailable;
    }

    public void setReturnAvailable(Boolean returnAvailable) {
        this.returnAvailable = returnAvailable;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.productName);
        dest.writeString(this.mrp);
        dest.writeString(this.price);
        dest.writeString(this.imageMain);
        dest.writeString(this.menuid);
        dest.writeString(this.soldBy);
        dest.writeString(this.productAverageRating);
        dest.writeString(this.productNoOfRatings);
        dest.writeString(this.sellerName);
        dest.writeString(this.createdDate);
        dest.writeString(this.productCode);
        dest.writeString(this.productCategoryName);
        dest.writeStringList(this.maincategory);
        dest.writeString(this.subCategory);
        dest.writeString(this.shippingFee);
        dest.writeString(this.returnsInDays);
        dest.writeValue(this.estoreAssured);
        dest.writeValue(this.brandCertifiedSeller);
        dest.writeValue(this.genuineProduct);
        dest.writeString(this.productNoOfReviews);
        dest.writeTypedList(this.categoryPath);
        dest.writeStringList(this.productHighlight);
        dest.writeStringList(this.productImages);
        dest.writeString(this.description);
        dest.writeString(this.brand);
        dest.writeString(this.packageContet);
        dest.writeString(this.disclaimer);
        dest.writeString(this.quantity);
        dest.writeString(this.idealFor);
        dest.writeString(this.type);
        dest.writeString(this.expiryDate);
        dest.writeString(this.bestBefore);
        dest.writeValue(this.inStock);
        dest.writeString(this.totolItems);
        dest.writeString(this.cartItems);
        dest.writeString(this.leftItems);
        dest.writeValue(this.codAvailable);
        dest.writeValue(this.emiAvailable);
        dest.writeValue(this.cashBack);
        dest.writeList(this.offers);
        dest.writeString(this.size);
        dest.writeString(this.color);
        dest.writeString(this.sizeUnit);
        dest.writeParcelable(this.dimensions, flags);
        dest.writeStringList(this.colorVariants);
        dest.writeStringList(this.sizeVariants);
        dest.writeString(this.sellerAverageRating);
        dest.writeString(this.sellerNoOfRatings);
        dest.writeString(this.sellerNoOfReviews);
        dest.writeParcelable(this.lifeStyleInfo, flags);
        dest.writeValue(this.replaceAvailable);
        dest.writeValue(this.cancellationAvailable);
        dest.writeValue(this.installationAvailable);
        dest.writeValue(this.shippingFree);
        dest.writeValue(this.returnAvailable);
        dest.writeValue(this.available);
    }

    public ProductDetails() {
    }

    protected ProductDetails(Parcel in) {
        this.id = in.readString();
        this.productName = in.readString();
        this.mrp = in.readString();
        this.price = in.readString();
        this.imageMain = in.readString();
        this.menuid = in.readString();
        this.soldBy = in.readString();
        this.productAverageRating = in.readString();
        this.productNoOfRatings = in.readString();
        this.sellerName = in.readString();
        this.createdDate = in.readString();
        this.productCode = in.readString();
        this.productCategoryName = in.readString();
        this.maincategory = in.createStringArrayList();
        this.subCategory = in.readString();
        this.shippingFee = in.readString();
        this.returnsInDays = in.readString();
        this.estoreAssured = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.brandCertifiedSeller = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.genuineProduct = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.productNoOfReviews = in.readString();
        this.categoryPath = in.createTypedArrayList(CategoryPath.CREATOR);
        this.productHighlight = in.createStringArrayList();
        this.productImages = in.createStringArrayList();
        this.description = in.readString();
        this.brand = in.readString();
        this.packageContet = in.readString();
        this.disclaimer = in.readString();
        this.quantity = in.readString();
        this.idealFor = in.readString();
        this.type = in.readString();
        this.expiryDate = in.readString();
        this.bestBefore = in.readString();
        this.inStock = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.totolItems = in.readString();
        this.cartItems = in.readString();
        this.leftItems = in.readString();
        this.codAvailable = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.emiAvailable = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.cashBack = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.offers = new ArrayList<Object>();
        in.readList(this.offers, Object.class.getClassLoader());
        this.size = in.readString();
        this.color = in.readString();
        this.sizeUnit = in.readString();
        this.dimensions = in.readParcelable(Dimensions.class.getClassLoader());
        this.colorVariants = in.createStringArrayList();
        this.sizeVariants = in.createStringArrayList();
        this.sellerAverageRating = in.readString();
        this.sellerNoOfRatings = in.readString();
        this.sellerNoOfReviews = in.readString();
        this.lifeStyleInfo = in.readParcelable(LifeStyleInfo.class.getClassLoader());
        this.replaceAvailable = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.cancellationAvailable = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.installationAvailable = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.shippingFree = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.returnAvailable = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.available = (Boolean) in.readValue(Boolean.class.getClassLoader());
    }

    public static final Parcelable.Creator<ProductDetails> CREATOR = new Parcelable.Creator<ProductDetails>() {
        @Override
        public ProductDetails createFromParcel(Parcel source) {
            return new ProductDetails(source);
        }

        @Override
        public ProductDetails[] newArray(int size) {
            return new ProductDetails[size];
        }
    };
}
