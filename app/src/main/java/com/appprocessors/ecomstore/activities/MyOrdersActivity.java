package com.appprocessors.ecomstore.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.appprocessors.ecomstore.R;
import com.appprocessors.ecomstore.adapter.OrderAdapter;
import com.appprocessors.ecomstore.model.customer.Customer;
import com.appprocessors.ecomstore.model.order.Order;
import com.appprocessors.ecomstore.retrofit.IEStoreAPI;
import com.appprocessors.ecomstore.utils.Common;
import com.appprocessors.ecomstore.utils.CommonOptionMenu;
import com.appprocessors.ecomstore.utils.UserSessionManager;
import com.stepstone.apprating.listener.RatingDialogListener;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyOrdersActivity extends CommonOptionMenu implements RatingDialogListener {


    ProgressDialog loadingOrdersdialog;

    IEStoreAPI mServices;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    @BindView(R.id.iv_no_orders)
    ImageView ivNoOrders;
    @BindView(R.id.tv_no_orders)
    TextView tvNoOrders;
    @BindView(R.id.btn_no_orders)
    Button btnNoOrders;
    @BindView(R.id.LL_no_order)
    LinearLayout LLNoOrder;
    @BindView(R.id.rv_my_orders)
    RecyclerView rvMyOrders;

    // User Session Manager Class
    UserSessionManager session;
    private static final String TAG = "MyOrdersActivity";

    Customer currentCustomer = new Customer();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_orders);
        ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.notes_toolbar);
        setSupportActionBar(toolbar);

        //Set Back Button to Toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        mServices = Common.getAPI(this);
        rvMyOrders.setLayoutManager(new LinearLayoutManager(this));
        rvMyOrders.setHasFixedSize(true);
        rvMyOrders.setNestedScrollingEnabled(false);
        // User Session Manager
        session = new UserSessionManager(getApplicationContext());
        getCustomerDetails();


        //open Add Address Activity on Add Address Button which is in LL Empty Show
        btnNoOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Open next activity
                Intent intent = new Intent(MyOrdersActivity.this, HomeActivity.class);
                // Add new Flag to start new Activity
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });
    }


    private void getCustomerDetails() {
        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage("Please wait");
        dialog.setCancelable(false);
        dialog.show();

        mServices.checkUserExists(session.getUserDetails().get(UserSessionManager.KEY_PHONE).replace("+", ""))
                .enqueue(new Callback<Customer>() {
                    @Override
                    public void onResponse(Call<Customer> call, @NonNull Response<Customer> response) {

                        if (response.isSuccessful()) {
                            Customer customerResponse = response.body();

                            if (customerResponse != null) {
                                //Perform Password matching
                                currentCustomer = customerResponse;
                                dialog.dismiss();
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
                                    getAllOrder(customerResponse);
                                    Toast.makeText(MyOrdersActivity.this, "Successfully Got Customer Details", Toast.LENGTH_SHORT).show();


                                } else {
                                    Intent intent = new Intent(MyOrdersActivity.this, HomeActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    // Add new Flag to start new Activity
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);
                                    finish();
                                    Toast.makeText(MyOrdersActivity.this, "Unable to get Customer Details", Toast.LENGTH_SHORT).show();

                                }

                            }
                        } else {
                            //User not exists
                            dialog.dismiss();
                            Intent intent = new Intent(MyOrdersActivity.this, LoginSignUp.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            // Add new Flag to start new Activity
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            finish();
                            session.logoutUser();
                            Toast.makeText(MyOrdersActivity.this, "Phone number is not registered !", Toast.LENGTH_SHORT).show();

                        }

                    }


                    @Override
                    public void onFailure(Call<Customer> call, Throwable t) {
                        dialog.dismiss();
                        Toast.makeText(MyOrdersActivity.this, "Something went wrong !" + t.getMessage(), Toast.LENGTH_SHORT).show();
                        Log.d("checkUserExists :", t.getMessage() + "\n");

                    }
                });


    }


    private void getAllOrder(Customer currentCustomer) {

        loadingOrdersdialog = new ProgressDialog(this);
        loadingOrdersdialog.setMessage("Please wait");
        loadingOrdersdialog.setCancelable(false);
        loadingOrdersdialog.show();


        compositeDisposable.add(mServices.getAllOrdersByCustomerId(currentCustomer.get_id())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Order>>() {
                    @Override
                    public void accept(List<Order> orders) throws Exception {

                        if (orders.size() != 0) {
                            rvMyOrders.setVisibility(View.VISIBLE);
                            LLNoOrder.setVisibility(View.GONE);
                            Log.e(TAG, "accept: ");
                            displayCategory(orders);
                        } else {
                            loadingOrdersdialog.dismiss();
                            LLNoOrder.setVisibility(View.VISIBLE);
                            rvMyOrders.setVisibility(View.GONE);
                        }


                    }
                }));
    }

    private void displayCategory(List<Order> orders) {

        OrderAdapter orderAdapter = new OrderAdapter(this, orders);
        rvMyOrders.setAdapter(orderAdapter);
        orderAdapter.notifyDataSetChanged();

        loadingOrdersdialog.dismiss();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onNegativeButtonClicked() {
    }

    @Override
    public void onNeutralButtonClicked() {
    }

    @Override
    public void onPositiveButtonClicked(int rate, @NotNull String review) {
        Toast.makeText(this, "Rate :" + rate + "\nReview : " + review, Toast.LENGTH_SHORT).show();

    }
}
