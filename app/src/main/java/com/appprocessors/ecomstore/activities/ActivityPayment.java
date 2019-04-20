package com.appprocessors.ecomstore.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.appprocessors.ecomstore.R;
import com.appprocessors.ecomstore.adapter.ProductOrderItemAdapter;
import com.appprocessors.ecomstore.model.customer.Addresses;
import com.appprocessors.ecomstore.model.customer.BillingAddress;
import com.appprocessors.ecomstore.model.customer.Customer;
import com.appprocessors.ecomstore.model.customer.ShippingAddress;
import com.appprocessors.ecomstore.model.customer.ShoppingCartItems;
import com.appprocessors.ecomstore.model.order.Order;
import com.appprocessors.ecomstore.model.order.OrderItem;
import com.appprocessors.ecomstore.retrofit.IEStoreAPI;
import com.appprocessors.ecomstore.utils.Common;
import com.appprocessors.ecomstore.utils.CommonOptionMenu;
import com.appprocessors.ecomstore.utils.UserSessionManager;
import com.appprocessors.ecomstore.utils.Utils;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.button.MaterialButton;

import org.fabiomsr.moneytextview.MoneyTextView;
import org.joda.time.LocalTime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityPayment extends CommonOptionMenu {

    ProgressDialog placingOrderDialog;

    //Current Product Details
    public List<OrderItem> currentOrderItems;
    public List<ShoppingCartItems> shoppingCartItemsList = new ArrayList<>();

    //Selected Address
    Addresses deliveryAddress = new Addresses();
    BillingAddress billingAddress = new BillingAddress();
    ShippingAddress shippingAddress = new ShippingAddress();

    // User Session Manager Class
    UserSessionManager session;

    //Retrofit API Interface
    IEStoreAPI mService;

    private static final String TAG = "ActivityPayment";


    Customer currentCustomer = new Customer();

    public Order order = new Order();
    @BindView(R.id.notes_toolbar)
    Toolbar notesToolbar;
    @BindView(R.id.appBarLayout)
    AppBarLayout appBarLayout;
    @BindView(R.id.tv_payment_options)
    TextView tvPaymentOptions;
    @BindView(R.id.rb_COD)
    RadioButton rbCOD;
    @BindView(R.id.AP_rv_OrderItems)
    RecyclerView APRvOrderItems;
    @BindView(R.id.tv_total_price_details)
    TextView tvTotalPriceDetails;
    @BindView(R.id.tv_price)
    TextView tvPrice;
    @BindView(R.id.tv_price_rs)
    public MoneyTextView tvPriceRs;
    @BindView(R.id.tv_shipping)
    TextView tvShipping;
    @BindView(R.id.tv_shipping_fee)
    TextView tvShippingFee;
    @BindView(R.id.tv_total_amount)
    TextView tvTotalAmount;
    @BindView(R.id.tv_total_amount_price)
    public MoneyTextView tvTotalAmountPrice;
    @BindView(R.id.btn_place_order)
    MaterialButton btnPlaceOrder;
    @BindView(R.id.LL_payment_price)
    LinearLayout LLPaymentPrice;
    @BindView(R.id.tv_thankyou)
    TextView tvThankyou;
    @BindView(R.id.tv_ordere_confirmed)
    TextView tvOrdereConfirmed;
    @BindView(R.id.tv_orderid)
    TextView tvOrderid;
    @BindView(R.id.tv_ordere_date)
    TextView tvOrdereDate;
    @BindView(R.id.tv_order_date_value)
    TextView tvOrderDateValue;
    @BindView(R.id.tv_order_address)
    TextView tvOrderAddress;
    @BindView(R.id.tv_order_address_value)
    TextView tvOrderAddressValue;
    @BindView(R.id.tv_order_amount)
    TextView tvOrderAmount;
    @BindView(R.id.tv_order_amount_value)
    TextView tvOrderAmountValue;
    @BindView(R.id.tv_order_payment_method)
    TextView tvOrderPaymentMethod;
    @BindView(R.id.btn_continue_shopping)
    Button btnContinueShopping;
    @BindView(R.id.FL_ordered)
    FrameLayout FLOrdered;
    @BindView(R.id.RL_Activity_Payment)
    RelativeLayout RLActivityPayment;
    @BindView(R.id.PB_Activity_Payment)
    ProgressBar PBActivityPayment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.notes_toolbar);
        setSupportActionBar(toolbar);

        setTitle("Select Payment");

        //Set Back Button to Toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        //API
        mService = Common.getAPI(this);
        // User Session Manager
        session = new UserSessionManager(getApplicationContext());

        //Set Visibility
        PBActivityPayment.setVisibility(View.VISIBLE);
        RLActivityPayment.setVisibility(View.GONE);
        LLPaymentPrice.setVisibility(View.VISIBLE);
        btnPlaceOrder.setVisibility(View.VISIBLE);
        FLOrdered.setVisibility(View.GONE);
        btnContinueShopping.setVisibility(View.GONE);
        btnContinueShopping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startShopping = new Intent(ActivityPayment.this, HomeActivity.class);
                startShopping.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(startShopping);
                finish();
            }
        });

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            if (extras.containsKey("OrderItems")) {
                ArrayList<OrderItem> orderItemArrayList = this.getIntent().getParcelableArrayListExtra("OrderItems");
                if (orderItemArrayList.size() != 0) {
                    currentOrderItems = orderItemArrayList;

                }
            }
            if (extras.containsKey("ShoppingCartItems")) {
                ArrayList<ShoppingCartItems> shoppingCartItems = this.getIntent().getParcelableArrayListExtra("ShoppingCartItems");
                if (shoppingCartItems.size() != 0) {
                    shoppingCartItemsList = shoppingCartItems;

                }
            }
            if (extras.containsKey("deliveryAddress")) {
                Addresses address = getIntent().getExtras().getParcelable("deliveryAddress");
                if (address != null) {
                    deliveryAddress = address;
                    billingAddress.set_id("");
                    billingAddress.setGenericAttributes(new ArrayList<>());
                    billingAddress.setFirstName(address.getFirstName());
                    billingAddress.setLastName(address.getLastName());
                    billingAddress.setEmail(currentCustomer.getEmail());
                    billingAddress.setCompany(address.getCompany());
                    billingAddress.setVatNumber("");
                    billingAddress.setCountryId("");
                    billingAddress.setStateProvinceId("");
                    billingAddress.setCity(address.getCity());
                    billingAddress.setAddress1(address.getAddress1());
                    billingAddress.setAddress2(address.getAddress2());
                    billingAddress.setZipPostalCode(address.getZipPostalCode());
                    billingAddress.setPhoneNumber(address.getPhoneNumber());
                    billingAddress.setFaxNumber("");
                    billingAddress.setCustomAttributes("");
                    billingAddress.setCreatedOnUtc("");

                    shippingAddress.set_id("");
                    shippingAddress.setGenericAttributes(new ArrayList<>());
                    shippingAddress.setFirstName(address.getFirstName());
                    shippingAddress.setLastName(address.getLastName());
                    shippingAddress.setEmail(currentCustomer.getEmail());
                    shippingAddress.setCompany(address.getCompany());
                    shippingAddress.setVatNumber("");
                    shippingAddress.setCountryId("");
                    shippingAddress.setStateProvinceId("");
                    shippingAddress.setCity(address.getCity());
                    shippingAddress.setAddress1(address.getAddress1());
                    shippingAddress.setAddress2(address.getAddress2());
                    shippingAddress.setZipPostalCode(address.getZipPostalCode());
                    shippingAddress.setPhoneNumber(address.getPhoneNumber());
                    shippingAddress.setFaxNumber("");
                    shippingAddress.setCustomAttributes("");
                    shippingAddress.setCreatedOnUtc("");

                }
                if (billingAddress != null && shippingAddress != null) {
                    setProductItemsList(shoppingCartItemsList);
                    getCustomerDetails(billingAddress, shippingAddress);
                    setProductDetais(currentOrderItems);
                }
            }

        }
    }

    private void setProductItemsList(List<ShoppingCartItems> currentShoppingCartItems) {

        if (currentShoppingCartItems.size() != 0) {
            ProductOrderItemAdapter orderItemAdapter = new ProductOrderItemAdapter(this, currentShoppingCartItems);
            APRvOrderItems.setLayoutManager(new LinearLayoutManager(this));
            APRvOrderItems.setHasFixedSize(true);
            APRvOrderItems.setNestedScrollingEnabled(false);
            APRvOrderItems.setAdapter(orderItemAdapter);
            orderItemAdapter.notifyDataSetChanged();
        } else {
            Toast.makeText(this, "No Item To Display Product List", Toast.LENGTH_SHORT).show();
        }

    }

    private void getCustomerDetails(BillingAddress billingAddress, ShippingAddress shippingAddress) {

        mService.checkUserExists(session.getUserDetails().get(UserSessionManager.KEY_PHONE).replace("+", ""))
                .enqueue(new Callback<Customer>() {
                    @Override
                    public void onResponse(Call<Customer> call, @NonNull Response<Customer> response) {

                        if (response.isSuccessful()) {
                            Customer customerResponse = response.body();

                            if (customerResponse != null) {
                                //Perform Password matching
                                currentCustomer = customerResponse;
                                String phone = null, firstName = null, lastName = null, gender = null;
                                for (int i = 0; i < customerResponse.getGenericAttributes().size(); i++) {
                                    if (customerResponse.getGenericAttributes().get(i).getKey().equalsIgnoreCase("FirstName")) {
                                        firstName = customerResponse.getGenericAttributes().get(i).getValue();
                                    }
                                    if (customerResponse.getGenericAttributes().get(i).getKey().equalsIgnoreCase("LastName")) {
                                        lastName = customerResponse.getGenericAttributes().get(i).getValue();
                                    }
                                    if (customerResponse.getGenericAttributes().get(i).getKey().equalsIgnoreCase("phone")) {
                                        phone = customerResponse.getGenericAttributes().get(i).getValue();
                                    }
                                    if (customerResponse.getGenericAttributes().get(i).getKey().equalsIgnoreCase("Gender")) {
                                        gender = customerResponse.getGenericAttributes().get(i).getValue();
                                    }
                                }
                                if (phone != null && firstName != null && lastName != null && gender != null) {
                                    //Save user Credentials in Shared Preference
                                    session.createUserLoginSession(phone,
                                            firstName,
                                            lastName,
                                            customerResponse.getEmail(),
                                            customerResponse.getPassword(),
                                            gender
                                    );
                                    Toast.makeText(ActivityPayment.this, "Successfully Got Customer Details", Toast.LENGTH_SHORT).show();


                                } else {
                                    Intent intent = new Intent(ActivityPayment.this, HomeActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    // Add new Flag to start new Activity
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);
                                    finish();
                                    Toast.makeText(ActivityPayment.this, "Unable to get Customer Details", Toast.LENGTH_SHORT).show();

                                }

                            }
                        } else {
                            //User not exists
                            Intent intent = new Intent(ActivityPayment.this, LoginSignUp.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            // Add new Flag to start new Activity
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            finish();
                            session.logoutUser();
                            Toast.makeText(ActivityPayment.this, "Phone number is not registered !", Toast.LENGTH_SHORT).show();

                        }

                    }


                    @Override
                    public void onFailure(Call<Customer> call, Throwable t) {
                        Toast.makeText(ActivityPayment.this, "Something went wrong !" + t.getMessage(), Toast.LENGTH_SHORT).show();
                        Log.d("checkUserExists :", t.getMessage() + "\n");

                    }
                });


    }


    private void setProductDetais(List<OrderItem> orderItemList) {
        tvPrice.setText(new StringBuilder().append("Price (").append(+orderItemList.size()).append(" Items)"));

        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                tvPriceRs.setAmount((float) totalOrderPrice());
                tvTotalAmountPrice.setAmount((float) totalOrderPrice());
            }
        });


        //Shipping Fee
        tvShippingFee.setText("FREE");
        tvShippingFee.setTextColor(getResources().getColor(R.color.green));

        btnPlaceOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentOrderItems != null) {
                    placeOrderToServer();

                }
            }
        });

      RLActivityPayment.setVisibility(View.VISIBLE);
      PBActivityPayment.setVisibility(View.GONE);
      btnPlaceOrder.setVisibility(View.VISIBLE);
    }

    private void placeOrderToServer() {

        placingOrderDialog = new ProgressDialog(ActivityPayment.this);
        placingOrderDialog.setMessage("Please wait");
        placingOrderDialog.setCancelable(false);
        placingOrderDialog.show();

        if (currentCustomer.get_id() != null && currentOrderItems != null) {
            order.setGenericAttributes(new ArrayList<>());
            order.setCustomerId(currentCustomer.get_id());
            order.setPaymentMethodSystemName("Payments.CashOnDelivery");
            order.setCustomerEmail(currentCustomer.getEmail() != null ? currentCustomer.getEmail() : "NoEmail@Email.com");

            String phone = null, firstName = null, lastName = null, gender = null;
            for (int i = 0; i < currentCustomer.getGenericAttributes().size(); i++) {
                if (currentCustomer.getGenericAttributes().get(i).getKey().equalsIgnoreCase("FirstName")) {
                    firstName = currentCustomer.getGenericAttributes().get(i).getValue();
                }
                if (currentCustomer.getGenericAttributes().get(i).getKey().equalsIgnoreCase("LastName")) {
                    lastName = currentCustomer.getGenericAttributes().get(i).getValue();
                }

            }

            for (int i = 0; i < currentOrderItems.size(); i++) {
                currentOrderItems.get(i).setPriceExclTax(currentOrderItems.get(i).getUnitPriceExclTax() * currentOrderItems.get(i).getQuantity());
                currentOrderItems.get(i).setPriceInclTax(currentOrderItems.get(i).getUnitPriceExclTax() * currentOrderItems.get(i).getQuantity());
            }

            order.setFirstName(firstName);
            order.setLastName(lastName);
            order.setOrderSubtotalInclTax(totalOrderPrice());
            order.setOrderSubtotalExclTax(totalOrderPrice());
            order.setOrderSubTotalDiscountInclTax(0);
            order.setOrderSubTotalDiscountExclTax(0);
            order.setOrderShippingInclTax(0);
            order.setOrderShippingExclTax(0);
            order.setPaymentMethodAdditionalFeeInclTax(0);
            order.setPaymentMethodAdditionalFeeExclTax(0);
            order.setOrderShippingInclTax(0);
            order.setOrderShippingExclTax(0);
            order.setTaxRates("0:0;");
            order.setOrderTax(0);
            order.setOrderDiscount(0);
            order.setOrderTotal(totalOrderPrice());
            order.setRefundedAmount(0);
            order.setCustomerIp(Utils.getIpAddress(this));
            order.setShippingMethod("Ground");
            order.setShippingRateComputationMethodSystemName("Shipping.ByWeight");
            order.setBillingAddress(billingAddress);
            order.setShippingAddress(shippingAddress);
            order.setOrderItems(currentOrderItems);
        } else {
            Toast.makeText(this, "Details Are Null !", Toast.LENGTH_SHORT).show();
        }

        mService.addNewOrder(order).enqueue(new Callback<Order>() {
            @Override
            public void onResponse(Call<Order> call, Response<Order> response) {
                if (response.isSuccessful()) {
                    Order orderedDetails = response.body();
                    placingOrderDialog.dismiss();
                    Toast.makeText(ActivityPayment.this, "Successfully Ordered !", Toast.LENGTH_SHORT).show();
                    FLOrdered.setVisibility(View.VISIBLE);
                    btnContinueShopping.setVisibility(View.VISIBLE);
                    btnPlaceOrder.setVisibility(View.GONE);
                    LLPaymentPrice.setVisibility(View.GONE);

                    tvOrderid.setText(new StringBuilder().append("Order #").append(String.valueOf(orderedDetails.getOrderNumber())).toString());

                    final String ISO8601DATEFORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSZ";

                    SimpleDateFormat dateFormat = new SimpleDateFormat(ISO8601DATEFORMAT, Locale.US);
                    SimpleDateFormat output = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
                    Date convertedDate = new Date();
                    try {
                        convertedDate = dateFormat.parse(orderedDetails.getCreatedOnUtc());
                    } catch (ParseException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }


                    Calendar calendar = Calendar.getInstance(TimeZone.getDefault(), Locale.getDefault());
                    SimpleDateFormat dateformat = new SimpleDateFormat(ISO8601DATEFORMAT, Locale.getDefault());
                    try {
                        Date date = dateformat.parse(orderedDetails.getCreatedOnUtc());
                        calendar.setTime(date);
                        LocalTime.fromCalendarFields(calendar);
                        tvOrderDateValue.setText(calendar.toString());
                        tvOrderDateValue.setText(output.format(convertedDate));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    StringBuilder addressFullDetails = new StringBuilder();
                    addressFullDetails.append(orderedDetails.getShippingAddress().getFirstName())
                            .append(" ")
                            .append(orderedDetails.getShippingAddress().getLastName())
                            .append("\n")
                            .append(orderedDetails.getShippingAddress().getAddress1())
                            .append(" ,")
                            .append(orderedDetails.getShippingAddress().getAddress2())
                            .append(" ,")
                            .append(orderedDetails.getShippingAddress().getCity())
                            .append(" ,")
                            .append(orderedDetails.getShippingAddress().getPhoneNumber());


                    tvOrderAddressValue.setText(addressFullDetails.toString());
                    tvOrderAmountValue.setText(new StringBuilder().append(getString(R.string.rupee)).append(" ").append(String.valueOf(orderedDetails.getOrderTotal())).toString());
                    setTitle("Ordered");
                }
            }

            @Override
            public void onFailure(Call<Order> call, Throwable t) {
                placingOrderDialog.dismiss();
                Log.e(TAG, "onFailure: Adding Order Failed " + t.getMessage());
                Toast.makeText(ActivityPayment.this, "Placing Order Failed !! " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }

    public long totalOrderPrice() {

        double totalPrice = 0;
        for (int i = 0; i < currentOrderItems.size(); i++) {
            totalPrice += currentOrderItems.get(i).getUnitPriceExclTax() * currentOrderItems.get(i).getQuantity();
        }
        return Math.round(totalPrice);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }
}
