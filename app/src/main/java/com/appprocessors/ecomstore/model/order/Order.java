package com.appprocessors.ecomstore.model.order;


import com.appprocessors.ecomstore.model.customer.BillingAddress;
import com.appprocessors.ecomstore.model.customer.GenericAttributes;
import com.appprocessors.ecomstore.model.customer.ShippingAddress;
import com.appprocessors.ecomstore.model.picture.Picture;
import com.google.gson.annotations.SerializedName;

import org.joda.time.DateTime;

import java.util.List;
import java.util.UUID;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor

public class Order {

    @SerializedName("_id")
    private String _id;
    @SerializedName("genericAttributes")
    private List<GenericAttributes> genericAttributes;
    @SerializedName("orderGuid")
    private String orderGuid;
    @SerializedName("orderNumber")
    private long orderNumber;
    @SerializedName("storeId")
    private String storeId;
    @SerializedName("customerId")
    private String customerId;
    @SerializedName("pickUpInStore")
    private boolean pickUpInStore;
    @SerializedName("orderStatusId")
    private long orderStatusId;
    @SerializedName("shippingStatusId")
    private long shippingStatusId;
    @SerializedName("paymentStatusId")
    private long paymentStatusId;
    @SerializedName("paymentMethodSystemName")
    private String paymentMethodSystemName;
    @SerializedName("customerCurrencyCode")
    private String customerCurrencyCode;
    @SerializedName("currencyRate")
    private long currencyRate;
    @SerializedName("CustomerTaxDisplayTypeId")
    private long customerTaxDisplayTypeId;
    @SerializedName("VatNumber")
    private String vatNumber;
    @SerializedName("VatNumberStatusId")
    private long vatNumberStatusId;
    @SerializedName("companyName")
    private String companyName;
    @SerializedName("customerEmail")
    private String customerEmail;
    @SerializedName("firstName")
    private String firstName;
    @SerializedName("lastName")
    private String lastName;
    @SerializedName("orderSubtotalInclTax")
    private long orderSubtotalInclTax;
    @SerializedName("orderSubtotalExclTax")
    private long orderSubtotalExclTax;
    @SerializedName("orderSubTotalDiscountInclTax")
    private long orderSubTotalDiscountInclTax;
    @SerializedName("orderSubTotalDiscountExclTax")
    private long orderSubTotalDiscountExclTax;
    @SerializedName("orderShippingInclTax")
    private long orderShippingInclTax;
    @SerializedName("orderShippingExclTax")
    private long orderShippingExclTax;
    @SerializedName("paymentMethodAdditionalFeeInclTax")
    private long paymentMethodAdditionalFeeInclTax;
    @SerializedName("paymentMethodAdditionalFeeExclTax")
    private long paymentMethodAdditionalFeeExclTax;
    @SerializedName("taxRates")
    private String taxRates;
    @SerializedName("orderTax")
    private long orderTax;
    @SerializedName("orderDiscount")
    private long orderDiscount;
    @SerializedName("orderTotal")
    private long orderTotal;
    @SerializedName("refundedAmount")
    private double refundedAmount;
    @SerializedName("rewardPointsWereAdded")
    private boolean rewardPointsWereAdded;
    @SerializedName("checkoutAttributeDescription")
    private String checkoutAttributeDescription;
    @SerializedName("checkoutAttributesXml")
    private String checkoutAttributesXml;
    @SerializedName("customerLanguageId")
    private String customerLanguageId;
    @SerializedName("affiliateId")
    private String affiliateId;
    @SerializedName("customerIp")
    private String customerIp;
    @SerializedName("allowStoringCreditCardNumber")
    private boolean allowStoringCreditCardNumber;
    @SerializedName("cardType")
    private String cardType;
    @SerializedName("cardName")
    private String cardName;
    @SerializedName("cardNumber")
    private String cardNumber;
    @SerializedName("maskedCreditCardNumber")
    private String maskedCreditCardNumber;
    @SerializedName("cardCvv2")
    private String cardCvv2;
    @SerializedName("cardExpirationMonth")
    private String cardExpirationMonth;
    @SerializedName("cardExpirationYear")
    private String cardExpirationYear;
    @SerializedName("authorizationTransactionId")
    private String authorizationTransactionId;
    @SerializedName("authorizationTransactionCode")
    private String authorizationTransactionCode;
    @SerializedName("authorizationTransactionResult")
    private String authorizationTransactionResult;
    @SerializedName("captureTransactionId")
    private String captureTransactionId;
    @SerializedName("captureTransactionResult")
    private String captureTransactionResult;
    @SerializedName("subscriptionTransactionId")
    private String subscriptionTransactionId;
    @SerializedName("paidDateUtc")
    private String paidDateUtc;
    @SerializedName("shippingMethod")
    private String shippingMethod;
    @SerializedName("shippingRateComputationMethodSystemName")
    private String shippingRateComputationMethodSystemName;
    @SerializedName("customValuesXml")
    private String customValuesXml;
    @SerializedName("deleted")
    private boolean deleted;
    @SerializedName("createdOnUtc")
    private String createdOnUtc;
    @SerializedName("imported")
    private boolean imported;
    @SerializedName("urlReferrer")
    private String urlReferrer;
    @SerializedName("shippingOptionAttributeDescription")
    private String shippingOptionAttributeDescription;
    @SerializedName("shippingOptionAttributeXml")
    private String shippingOptionAttributeXml;
    @SerializedName("billingAddress")
    private BillingAddress billingAddress;
    @SerializedName("shippingAddress")
    private ShippingAddress shippingAddress;
    @SerializedName("pickupPoint")
    private String pickupPoint;
    @SerializedName("redeemedRewardPointsEntry")
    private String redeemedRewardPointsEntry;
    @SerializedName("orderItems")
    private List<OrderItem> orderItems;
    @SerializedName("pictureDetails")
    private List<Picture> pictureDetails = null;

    public String get_id() {
        return _id;
    }


}