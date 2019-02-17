package com.appprocessors.ecomstore.activities;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.MenuItem;

import com.appprocessors.ecomstore.adapter.OrderAdapter;
import com.appprocessors.ecomstore.R;
import com.appprocessors.ecomstore.model.Order;
import com.appprocessors.ecomstore.retrofit.IEStoreAPI;
import com.appprocessors.ecomstore.utils.Common;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class MyOrdersActivity extends AppCompatActivity {


    IEStoreAPI mServices;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    @BindView(R.id.rv_my_orders)
    RecyclerView rvMyOrders;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_orders);
        ButterKnife.bind(this);

        mServices = Common.getAPI();
        rvMyOrders.setLayoutManager(new LinearLayoutManager(this));
        rvMyOrders.setHasFixedSize(true);

        getAllOrder();


        //Set Back Button to Toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    private void getAllOrder() {
        compositeDisposable.add(mServices.getAllOrders()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Order>>() {
                    @Override
                    public void accept(List<Order> orders) throws Exception {
                        displayCategory(orders);
                    }
                }));
    }

    private void displayCategory(List<Order> orders) {
        OrderAdapter categoryAdapter = new OrderAdapter(this, orders);
        rvMyOrders.setAdapter(categoryAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }
}
