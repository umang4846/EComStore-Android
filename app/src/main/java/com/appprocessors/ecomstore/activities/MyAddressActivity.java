package com.appprocessors.ecomstore.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.appprocessors.ecomstore.R;
import com.appprocessors.ecomstore.adapter.MyAddressesAdapter;
import com.appprocessors.ecomstore.model.Address;
import com.appprocessors.ecomstore.retrofit.IEStoreAPI;
import com.appprocessors.ecomstore.utils.Common;
import com.appprocessors.ecomstore.utils.UserSessionManager;
import com.google.android.material.button.MaterialButton;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class MyAddressActivity extends AppCompatActivity {

    ProgressDialog loadingAddressdialog;

    private static final String TAG = "MyAddressActivity";

    IEStoreAPI mService;
    //Rx java
    CompositeDisposable compositeDisposable = new CompositeDisposable();

    // User Session Manager Class
    UserSessionManager session;

    //Butterknife Injections

    @BindView(R.id.iv_no_address)
    ImageView ivNoAddress;
    @BindView(R.id.tv_no_address)
    TextView tvNoAddress;
    @BindView(R.id.btn_add_address)
    MaterialButton btnAddAddress;
    @BindView(R.id.LL_no_address_iv_tv_btn)
    LinearLayout LLNoAddressIvTvBtn;
    @BindView(R.id.rv_addresses)
    RecyclerView rvAddresses;

    @BindView(R.id.RL_Addresslist)
    RelativeLayout RLAddresslist;
    @BindView(R.id.btn_add_new_address)
    MaterialButton btnAddNewAddress;
    @BindView(R.id.material_button)
    MaterialButton materialButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_address);
        ButterKnife.bind(this);
        setTitle("Select Delivery");

        //Set Back Button to Toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        mService = Common.getAPI();
        // User Session Manager
        session = new UserSessionManager(getApplicationContext());
        //Load All Addreses of user
        loadUserAddresses();

        //open Add Address Activity on Add Address Button Clicked
        btnAddNewAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MyAddressActivity.this, AddAddressActivity.class));
            }
        });

        //open Add Address Activity on Add Address Button which is in LL Empty Show
      /*  btnAddNewAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MyAddressActivity.this, AddAddressActivity.class));
            }
        });*/

    }

    private void loadUserAddresses() {

        loadingAddressdialog = new ProgressDialog(this);
        loadingAddressdialog.setMessage("Please wait");
        loadingAddressdialog.setCancelable(false);
        loadingAddressdialog.show();

        compositeDisposable.add(mService.getUserAddresses(session.getUserDetails().get(UserSessionManager.KEY_PHONE))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Address>>() {
                               @Override
                               public void accept(List<Address> addresses) throws Exception {

                                   if (addresses != null) {
                                       RLAddresslist.setVisibility(View.VISIBLE);
                                       LLNoAddressIvTvBtn.setVisibility(View.GONE);
                                       displayAddressList(addresses);
                                   } else {
                                       LLNoAddressIvTvBtn.setVisibility(View.VISIBLE);
                                       RLAddresslist.setVisibility(View.GONE);
                                   }

                               }
                           }
                ));

    }


    private void displayAddressList(List<Address> addresses) {
        rvAddresses.setLayoutManager(new LinearLayoutManager(this));
        rvAddresses.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        rvAddresses.setHasFixedSize(true);
        rvAddresses.setNestedScrollingEnabled(false);
        MyAddressesAdapter myAddressesAdapter = new MyAddressesAdapter(this, addresses);
        rvAddresses.setAdapter(myAddressesAdapter);
        myAddressesAdapter.notifyDataSetChanged();
        loadingAddressdialog.dismiss();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.btn_add_new_address)
    public void onViewClicked() {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case Common.tagUpdateAddress:
                break;

            case Common.tagAddAddress:
                break;
        }
    }

    @OnClick({R.id.btn_add_new_address, R.id.material_button, R.id.ib_edit_address})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_add_new_address:

                break;
            case R.id.material_button:
                break;
            case R.id.ib_edit_address:
                int position = (Integer) view.getTag();
                Log.e(TAG, "onViewClicked: " + position);
        }
    }
}
