package com.appprocessors.ecomstore.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.appprocessors.ecomstore.R;
import com.appprocessors.ecomstore.model.Address;
import com.appprocessors.ecomstore.model.OrderModel;
import com.appprocessors.ecomstore.model.ProductDetails;
import com.appprocessors.ecomstore.retrofit.IEStoreAPI;
import com.appprocessors.ecomstore.utils.Common;
import com.appprocessors.ecomstore.utils.UserSessionManager;

import org.fabiomsr.moneytextview.MoneyTextView;

import java.util.Calendar;
import java.util.Date;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityPayment extends AppCompatActivity {


    ProgressDialog placingOrderDialog;

    @BindView(R.id.tv_payment_options)
    TextView tvPaymentOptions;
    @BindView(R.id.rb_COD)
    RadioButton rbCOD;
    @BindView(R.id.tv_total_price_details)
    TextView tvTotalPriceDetails;
    @BindView(R.id.tv_price)
    TextView tvPrice;
    @BindView(R.id.tv_price_rs)
    MoneyTextView tvPriceRs;
    @BindView(R.id.tv_shipping)
    TextView tvShipping;
    @BindView(R.id.tv_shipping_fee)
    TextView tvShippingFee;
    @BindView(R.id.tv_total_amount)
    TextView tvTotalAmount;
    @BindView(R.id.tv_total_amount_price)
    MoneyTextView tvTotalAmountPrice;
    @BindView(R.id.btn_place_order)
    Button btnPlaceOrder;

    //Selected Product Details
    ProductDetails currentProductDetails;

    //Selected Address
    Address deliveryAddress;

    // User Session Manager Class
    UserSessionManager session;

    //Retrofit API Interface
    IEStoreAPI mService;

    private static final String TAG = "ActivityPayment";
    @BindView(R.id.LL_payment_price)
    LinearLayout LLPaymentPrice;
    @BindView(R.id.tv_ordered_successfully)
    TextView tvOrderedSuccessfully;
    @BindView(R.id.tv_ordered_date)
    TextView tvOrderedDate;
    @BindView(R.id.btn_continue_shopping)
    Button btnContinueShopping;
    @BindView(R.id.FL_ordered)
    FrameLayout FLOrdered;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        ButterKnife.bind(this);
        setTitle("Select Payment");

        //Set Back Button to Toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        //API
        mService = Common.getAPI();
        // User Session Manager
        session = new UserSessionManager(getApplicationContext());

        //Set Visibility
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
            if (extras.containsKey("productDetails")) {
                ProductDetails productDetails = getIntent().getExtras().getParcelable("productDetails");
                if (productDetails != null) {
                    currentProductDetails = productDetails;
                    setProductDetais(productDetails);
                }
            }
            if (extras.containsKey("deliveryAddress")) {
                Address address = getIntent().getExtras().getParcelable("deliveryAddress");
                if (address != null) {
                    deliveryAddress = address;

                }
            }
        }


    }

    private void setProductDetais(ProductDetails productDetais) {
        tvPriceRs.setAmount(Float.parseFloat(productDetais.getPrice()));
        //Shipping Fee
        if (productDetais.getShippingFree()) {
            tvShippingFee.setText("FREE");
            tvShippingFee.setTextColor(getResources().getColor(R.color.green));

        } else {
            String shippingFee = String.valueOf(new StringBuilder(getResources().getString(R.string.rupee)).append(" ").append(productDetais.getShippingFee()));
            tvShippingFee.setText(shippingFee);
            tvShippingFee.setTextColor(getResources().getColor(R.color.black));
        }
        if (productDetais.getShippingFree())
            tvTotalAmountPrice.setAmount(Float.parseFloat(productDetais.getPrice()));
        else
            tvTotalAmountPrice.setAmount(Float.parseFloat(productDetais.getPrice()) + Float.parseFloat(productDetais.getShippingFee()));

        btnPlaceOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentProductDetails != null) {
                    placeOrderToServer();
                    placingOrderDialog = new ProgressDialog(ActivityPayment.this);
                    placingOrderDialog.setMessage("Please wait");
                    placingOrderDialog.setCancelable(false);
                    placingOrderDialog.show();
                }
            }
        });


    }

    private void placeOrderToServer() {

        OrderModel orderModel = new OrderModel();
        orderModel.setProductName(currentProductDetails.getProductName());
        orderModel.setProductPrice(currentProductDetails.getPrice());
        orderModel.setShippingFee(currentProductDetails.getShippingFee());
        orderModel.setProductQuanity("");
        orderModel.setTotalAmount(String.valueOf(tvTotalAmountPrice.getAmount()));
        orderModel.setProductDetails(currentProductDetails);
        if (rbCOD.isChecked()) {
            orderModel.setPaymentMode(rbCOD.getText().toString());
        }
        orderModel.setDeliveryAddress(deliveryAddress);
        orderModel.setOrderedAccountMobileNo(session.getUserDetails().get(UserSessionManager.KEY_PHONE));
        //Date and Time
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        String hour = String.valueOf(calendar.get(Calendar.HOUR));
        String minute = String.valueOf(calendar.get(Calendar.MINUTE));
        String second = String.valueOf(calendar.get(Calendar.SECOND));
        String milliSeconds = String.valueOf(calendar.get(Calendar.MILLISECOND));
        Date date = new Date();
        String dayOfTheWeek = (String) DateFormat.format("EEEE", date); // Thursday
        String day = (String) DateFormat.format("dd", date); // 20
        String monthNumber = (String) DateFormat.format("MM", date); // 06
        String year = (String) DateFormat.format("yyyy", date); // 2013
        String orderDatetime = dayOfTheWeek + " " + day + " " + monthNumber + " " + year + " " + hour + " " + minute + " " + second + " " + milliSeconds;
        orderModel.setOrderDateTime(orderDatetime);
        orderModel.setId(session.getUserDetails().get(UserSessionManager.KEY_PHONE) + orderDatetime.replace(" ", "-"));

        Log.d(TAG, "placeOrderToServer: " + orderModel.toString());

        mService.addOrder(orderModel).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    placingOrderDialog.dismiss();
                    Toast.makeText(ActivityPayment.this, "Successfully Ordered !", Toast.LENGTH_SHORT).show();
                    //startActivity(new Intent(ActivityPayment.this, MyOrdersActivity.class));
                    FLOrdered.setVisibility(View.VISIBLE);
                    btnContinueShopping.setVisibility(View.VISIBLE);
                    btnPlaceOrder.setVisibility(View.GONE);
                    LLPaymentPrice.setVisibility(View.GONE);
                    tvOrderedDate.setText(dayOfTheWeek+", "+day+"th "+DateFormat.format("MMM",date)+" "+year);
                    setTitle("Ordered");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e(TAG, "onFailure: Adding Order Failed " + t.getMessage());
                Toast.makeText(ActivityPayment.this, "Placing Order Failed !! " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }
}
