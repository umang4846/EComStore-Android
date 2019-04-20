package com.appprocessors.ecomstore.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.appprocessors.ecomstore.R;
import com.appprocessors.ecomstore.adapter.MyAddressesAdapter;
import com.appprocessors.ecomstore.model.Address;
import com.appprocessors.ecomstore.model.customer.Addresses;
import com.appprocessors.ecomstore.model.customer.ShoppingCartItems;
import com.appprocessors.ecomstore.model.order.OrderItem;
import com.appprocessors.ecomstore.model.product.Product;
import com.appprocessors.ecomstore.retrofit.IEStoreAPI;
import com.appprocessors.ecomstore.utils.Common;
import com.appprocessors.ecomstore.utils.CommonOptionMenu;
import com.appprocessors.ecomstore.utils.UserSessionManager;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
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

public class MyAddressActivity extends CommonOptionMenu implements AdapterView.OnItemClickListener, View.OnClickListener {

    ProgressDialog loadingAddressdialog;

    private static final String TAG = "MyAddressActivity";

    IEStoreAPI mService;
    //Rx java
    CompositeDisposable compositeDisposable = new CompositeDisposable();

    // User Session Manager Class
    UserSessionManager session;

    //Butterknife Injections


    //Current Product Details
    List<OrderItem> currentProductDetails;
    List<ShoppingCartItems> shoppingCartItemsList = new ArrayList<>();

    Address selectedItems;

    List<Addresses> addressesList = new ArrayList<>();

    //RecyclerView Adapter
    public MyAddressesAdapter myAddressesAdapter = new MyAddressesAdapter(this);

    int selectedAddressPosition = 0;
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
        setTitle("My Addresses");

        Toolbar toolbar = (Toolbar) findViewById(R.id.notes_toolbar);
        setSupportActionBar(toolbar);

        //Set Back Button to Toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        mService = Common.getAPI(this);
        // User Session Manager
        session = new UserSessionManager(getApplicationContext());

        rvAddresses.setLayoutManager(new LinearLayoutManager(this));
        rvAddresses.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        rvAddresses.setHasFixedSize(true);
        rvAddresses.setNestedScrollingEnabled(false);

        btnDeliverHere.setVisibility(View.GONE);

        //get Product Details From Intent
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            if (extras.containsKey("OrderItems")) {
                ArrayList<OrderItem> orderItemArrayList = this.getIntent().getParcelableArrayListExtra("OrderItems");
                ArrayList<ShoppingCartItems> productArrayList = this.getIntent().getParcelableArrayListExtra("ShoppingCartItems");
                if (orderItemArrayList.size() != 0) {
                    currentProductDetails = orderItemArrayList;
                    shoppingCartItemsList = productArrayList;
                    btnDeliverHere.setVisibility(View.VISIBLE);
                    setTitle("Select Delivery");
                }
            }
        }
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

        compositeDisposable.add(mService.findAllAddressesByPhone(session.getUserDetails().get(UserSessionManager.KEY_PHONE).replace("+", ""))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Addresses>>() {
                               @Override
                               public void accept(List<Addresses> addresses) throws Exception {

                                   if (addresses.size() != 0) {
                                       RLAddresslist.setVisibility(View.VISIBLE);
                                       LLNoAddressIvTvBtn.setVisibility(View.GONE);
                                       displayAddressList(addresses);
                                   } else {
                                       loadingAddressdialog.dismiss();
                                       LLNoAddressIvTvBtn.setVisibility(View.VISIBLE);
                                       ivNoAddress.setImageDrawable(getResources().getDrawable(R.drawable.no_address));
                                       RLAddresslist.setVisibility(View.GONE);
                                   }

                               }
                           }
                ));

    }

    private void displayAddressList(List<Addresses> addresses) {
        addressesList = addresses;
        myAddressesAdapter = new MyAddressesAdapter(this, addressesList);
        Log.e(TAG, "displayAddressList: List of Address ::" + addresses);
        rvAddresses.setAdapter(myAddressesAdapter);
        myAddressesAdapter.setOnItemClickListener(this::onItemClick);
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
                    addressesList.set(selectedAddressPosition, data.<Addresses>getParcelableExtra(Common.addUpdatedAddress));
                    myAddressesAdapter.UpdateData(selectedAddressPosition, addressesList.get(selectedAddressPosition));
                    myAddressesAdapter.notifyItemChanged(selectedAddressPosition);
                    rvAddresses.smoothScrollToPosition(selectedAddressPosition);
                    break;

                case Common.tagAddAddress:
                    if (addressesList.size() == 0) {
                        LLNoAddressIvTvBtn.setVisibility(View.GONE);
                        RLAddresslist.setVisibility(View.VISIBLE);
                        addressesList.add(data.<Addresses>getParcelableExtra(Common.addNewAddress));
                    } else {
                        LLNoAddressIvTvBtn.setVisibility(View.GONE);
                        RLAddresslist.setVisibility(View.VISIBLE);
                        addressesList.add(data.<Addresses>getParcelableExtra(Common.addNewAddress));
                    }
                    myAddressesAdapter = new MyAddressesAdapter(this, addressesList);
                    rvAddresses.setAdapter(myAddressesAdapter);
                    myAddressesAdapter.notifyDataSetChanged();
                    rvAddresses.smoothScrollToPosition(myAddressesAdapter.getItemCount() - 1);
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
                intentDeliverHere.putExtra("deliveryAddress", addressesList.get(selectedAddressPosition));
                intentDeliverHere.putParcelableArrayListExtra("OrderItems", (ArrayList<? extends Parcelable>) currentProductDetails);
                intentDeliverHere.putParcelableArrayListExtra("ShoppingCartItems", (ArrayList<? extends Parcelable>) shoppingCartItemsList);
                startActivity(intentDeliverHere);

                // intentDeliverHere.putExtra("deliveryAddress",)
                if (currentProductDetails != null && addressesList.get(selectedAddressPosition) != null) {
                    startActivity(intentDeliverHere);
                } else {
                    Toast.makeText(this, "Please Select Delivery Address", Toast.LENGTH_SHORT).show();
                }
                break;

        }
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        selectedAddressPosition = position;

    }

    @Override
    public void onClick(View v) {

    }
}
