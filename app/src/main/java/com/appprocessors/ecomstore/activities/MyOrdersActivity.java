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
import com.appprocessors.ecomstore.model.ProductDetails;
import com.appprocessors.ecomstore.model.order.Order;
import com.appprocessors.ecomstore.model.product.Product;
import com.appprocessors.ecomstore.retrofit.IEStoreAPI;
import com.appprocessors.ecomstore.utils.Common;
import com.appprocessors.ecomstore.utils.UserSessionManager;
import com.squareup.picasso.Picasso;
import com.stepstone.apprating.listener.RatingDialogListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class MyOrdersActivity extends AppCompatActivity implements RatingDialogListener {


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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_orders);
        ButterKnife.bind(this);

        mServices = Common.getAPI();
        rvMyOrders.setLayoutManager(new LinearLayoutManager(this));
        rvMyOrders.setHasFixedSize(true);
        rvMyOrders.setNestedScrollingEnabled(false);
        // User Session Manager
        session = new UserSessionManager(getApplicationContext());
        getAllOrder();


        //Set Back Button to Toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        //open Add Address Activity on Add Address Button which is in LL Empty Show
        btnNoOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Open next activity
                Intent intent = new Intent(MyOrdersActivity.this, HomeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                // Add new Flag to start new Activity
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });
    }

    private void getAllOrder() {

        loadingOrdersdialog = new ProgressDialog(this);
        loadingOrdersdialog.setMessage("Please wait");
        loadingOrdersdialog.setCancelable(false);
        loadingOrdersdialog.show();



        compositeDisposable.add(mServices.getAllOrdersByCustomerId(session.getUserDetails().get(UserSessionManager.KEY_PHONE))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<com.appprocessors.ecomstore.model.order.Order>>() {
                    @Override
                    public void accept(List<Order> orders) throws Exception {

                        if (orders.size() != 0) {
                            List<Product> productDetailsList = new ArrayList<>();
                            for (int i = 0; i < orders.size(); i++) {
                                compositeDisposable.add(mServices.getProductById(orders.get(i).get_id())
                                        .subscribeOn(Schedulers.io())
                                        .observeOn(AndroidSchedulers.mainThread())
                                        .subscribe(new Consumer<Product>() {
                                                       @Override
                                                       public void accept(Product productDetails) throws Exception {
                                                           if (productDetails != null) {
                                                               Log.e(TAG, "accept: Single Product Details"+productDetails );
                                                               productDetailsList.add(productDetails);

                                                           }

                                                       }
                                                   }
                                        ));
                                rvMyOrders.setVisibility(View.VISIBLE);
                                LLNoOrder.setVisibility(View.GONE);
                                Log.e(TAG, "accept: "+productDetailsList);
                                displayCategory(orders,productDetailsList);
                            }
                        }else{
                                loadingOrdersdialog.dismiss();
                                LLNoOrder.setVisibility(View.VISIBLE);
                                rvMyOrders.setVisibility(View.GONE);
                            }



                    }
                }));
    }

    private void displayCategory(List<Order> orders,List<Product> productDetailsList) {

        OrderAdapter orderAdapter = new OrderAdapter(this, orders,productDetailsList);
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
