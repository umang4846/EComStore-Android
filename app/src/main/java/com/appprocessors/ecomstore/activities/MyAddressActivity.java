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
import com.appprocessors.ecomstore.interfaces.MyAddressItemClickListner;
import com.appprocessors.ecomstore.interfaces.OnItemSelectedListener;
import com.appprocessors.ecomstore.model.Address;
import com.appprocessors.ecomstore.model.ProductDetails;
import com.appprocessors.ecomstore.model.SelectableAddress;
import com.appprocessors.ecomstore.retrofit.IEStoreAPI;
import com.appprocessors.ecomstore.utils.Common;
import com.appprocessors.ecomstore.utils.UserSessionManager;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
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

public class MyAddressActivity extends AppCompatActivity implements OnItemSelectedListener, MyAddressItemClickListner, View.OnClickListener {

    ProgressDialog loadingAddressdialog;

    private static final String TAG = "MyAddressActivity";

    IEStoreAPI mService;
    //Rx java
    CompositeDisposable compositeDisposable = new CompositeDisposable();

    // User Session Manager Class
    UserSessionManager session;

    //Butterknife Injections


    //Current Product Details
    ProductDetails currentProductDetails;

    Address selectedItems;

    List<Address> addressesList = new ArrayList<>();

    //RecyclerView Adapter
    public MyAddressesAdapter myAddressesAdapter = new MyAddressesAdapter(this);

    int position = 0;
    @BindView(R.id.iv_no_address)
    ImageView ivNoAddress;
    @BindView(R.id.tv_no_address)
    TextView tvNoAddress;
    @BindView(R.id.btn_add_address)
    MaterialButton btnAddAddress;
    @BindView(R.id.LL_no_address_iv_tv_btn)
    LinearLayout LLNoAddressIvTvBtn;
    @BindView(R.id.btn_add_new_address)
    MaterialButton btnAddNewAddress;
    @BindView(R.id.rv_addresses)
    RecyclerView rvAddresses;
    @BindView(R.id.btn_deliver_here)
    MaterialButton btnDeliverHere;
    @BindView(R.id.RL_Addresslist)
    RelativeLayout RLAddresslist;


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

        //get Product Details From Intent
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            if (extras.containsKey("productDetails")) {
                ProductDetails productDetails = getIntent().getExtras().getParcelable("productDetails");
                if (productDetails != null) {
                    currentProductDetails = productDetails;
                }
            }
        }
        mService = Common.getAPI();
        // User Session Manager
        session = new UserSessionManager(getApplicationContext());

        rvAddresses.setLayoutManager(new LinearLayoutManager(this));
        rvAddresses.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        rvAddresses.setHasFixedSize(true);
        rvAddresses.setNestedScrollingEnabled(false);

        //Load All Addreses of user
        loadUserAddresses();

        //open Add Address Activity on Add Address Button which is in LL Empty Show
        btnAddAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(MyAddressActivity.this, AddAddressActivity.class), Common.tagAddAddress);
            }
        });


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

                                   if (addresses.size() != 0) {
                                       RLAddresslist.setVisibility(View.VISIBLE);
                                       LLNoAddressIvTvBtn.setVisibility(View.GONE);
                                       displayAddressList(addresses);
                                   } else {
                                       loadingAddressdialog.dismiss();
                                       LLNoAddressIvTvBtn.setVisibility(View.VISIBLE);
                                       RLAddresslist.setVisibility(View.GONE);
                                   }

                               }
                           }
                ));

    }

    private void displayAddressList(List<Address> addresses) {
        addressesList = addresses;
        myAddressesAdapter = new MyAddressesAdapter(this, addressesList, this, this);
        Log.e(TAG, "displayAddressList: List of Address ::" + addresses);
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


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case Common.tagUpdateAddress:
                    addressesList.set(position, data.<Address>getParcelableExtra(Common.addUpdatedAddress));
                    myAddressesAdapter.UpdateData(position, addressesList.get(position));
                    myAddressesAdapter.notifyItemChanged(position);
                    rvAddresses.smoothScrollToPosition(position);
                    break;

                case Common.tagAddAddress:
                    if (addressesList.size() == 0) {
                        LLNoAddressIvTvBtn.setVisibility(View.GONE);
                        RLAddresslist.setVisibility(View.VISIBLE);
                        addressesList.add(data.<Address>getParcelableExtra(Common.addNewAddress));
                    }
                    else {
                        LLNoAddressIvTvBtn.setVisibility(View.GONE);
                        RLAddresslist.setVisibility(View.VISIBLE);
                        addressesList.add(data.<Address>getParcelableExtra(Common.addNewAddress));
                    }
                    myAddressesAdapter = new MyAddressesAdapter(this, addressesList, this, this);
                    rvAddresses.setAdapter(myAddressesAdapter);
                    myAddressesAdapter.notifyDataSetChanged();
                    rvAddresses.smoothScrollToPosition(myAddressesAdapter.getItemCount()-1);
                    break;
            }
        }
    }

    @OnClick({R.id.btn_add_new_address, R.id.btn_deliver_here})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_add_new_address:
                startActivityForResult(new Intent(MyAddressActivity.this, AddAddressActivity.class), Common.tagAddAddress);
                break;
            case R.id.btn_deliver_here:

                Intent intentDeliverHere = new Intent(MyAddressActivity.this, ActivityPayment.class);
                intentDeliverHere.putExtra("productDetails", currentProductDetails);
                intentDeliverHere.putExtra("deliveryAddress", selectedItems);
                // intentDeliverHere.putExtra("deliveryAddress",)
                startActivity(intentDeliverHere);
                break;

        }
    }


    @Override
    public void onItemSelected(SelectableAddress item) {
        selectedItems = myAddressesAdapter.getSelectedAddress();
    }

    @Override
    public void onClick(View view, int positions, Address address) {
        Log.e(TAG, "onClick: Postion" + positions);
        position = positions;
    }

    @Override
    public void onClick(View v) {

    }
}
