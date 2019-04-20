package com.appprocessors.ecomstore.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.appprocessors.ecomstore.R;
import com.appprocessors.ecomstore.adapter.CartAdapter;
import com.appprocessors.ecomstore.model.customer.ShoppingCartItems;
import com.appprocessors.ecomstore.model.order.OrderItem;
import com.appprocessors.ecomstore.retrofit.IEStoreAPI;
import com.appprocessors.ecomstore.utils.Common;
import com.appprocessors.ecomstore.utils.CommonOptionMenu;
import com.appprocessors.ecomstore.utils.UserSessionManager;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class CartActivity extends CommonOptionMenu {

    CompositeDisposable compositeDisposable = new CompositeDisposable();

    ProgressDialog loadingOrdersdialog;

    IEStoreAPI mServices;

    // User Session Manager Class
    UserSessionManager session;

    //Cart List Items
   public List<ShoppingCartItems> shoppingCartItemsList = new ArrayList<>();

    private static final String TAG = "CartActivity";
    @BindView(R.id.notes_toolbar)
    Toolbar notesToolbar;
    @BindView(R.id.appBarLayout)
    AppBarLayout appBarLayout;
    @BindView(R.id.iv_cart)
    ImageView ivCart;
    @BindView(R.id.tv_cart_main)
    TextView tvCartMain;
    @BindView(R.id.tv_cart_small)
    TextView tvCartSmall;
    @BindView(R.id.btn_shopnow)
    Button btnShopnow;
    @BindView(R.id.LL_empty_cart)
    public LinearLayout LLEmptyCart;
    @BindView(R.id.rv_cart_list)
    public RecyclerView rvCartList;
    @BindView(R.id.btn_cart_continue)
    public MaterialButton btnCartContinue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        ButterKnife.bind(this);
        //Set Cart title when items > 0 or =0
     /*   if (Common.cartRepository.countCartItems() > 0)
            setTitle("My Cart " + "(" + Common.cartRepository.countCartItems() + ")");
        else
            setTitle("My Cart");*/

        Toolbar toolbar = (Toolbar) findViewById(R.id.notes_toolbar);
        setSupportActionBar(toolbar);
        setTitle("My Cart");

        //Set Back Button to Toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }


        // User Session Manager
        session = new UserSessionManager(getApplicationContext());

        mServices = Common.getAPI(this);

        rvCartList.setVisibility(View.GONE);
        LLEmptyCart.setVisibility(View.GONE);
        btnCartContinue.setVisibility(View.GONE);

        rvCartList.setLayoutManager(new LinearLayoutManager(this));
        rvCartList.setHasFixedSize(true);
        // Load Cart Items
        getAllCartItems();

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
               /* if (Common.cartRepository.countCartItems() == 0) {
                    emptyCart.setGravity(Gravity.CENTER | Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);
                    emptyCart.setVisibility(View.VISIBLE);
                    cart_List.setVisibility(View.GONE);
                    cartTotalPriceContinue.setVisibility(View.GONE);
                    viewDeividerMain.setVisibility(View.GONE);
                } else {
                    emptyCart.setVisibility(View.GONE);
                    cart_List.setVisibility(View.VISIBLE);
                    cartTotalPriceContinue.setVisibility(View.VISIBLE);
                    viewDeividerMain.setVisibility(View.VISIBLE);
                    //badge.setText(String.valueOf(Common.cartRepository.countCartItems()));
                }*/
            }
        });

        //Shop now Button when no items in cart
        btnShopnow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CartActivity.this, HomeActivity.class);
                // Add new Flag to start new Activity
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });
        //Continue Button Clicked
        btnCartContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (shoppingCartItemsList.size() != 0) {

                    List<OrderItem> orderItemArrayList = new ArrayList<>();
                    for (int i = 0; i < shoppingCartItemsList.size(); i++) {
                        OrderItem orderItem = new OrderItem();
                        orderItem.setProductId(shoppingCartItemsList.get(i).getProductDetails().get_id());
                        orderItem.setPriceExclTax(shoppingCartItemsList.get(i).getProductDetails().getPrice());
                        orderItem.setPriceInclTax(shoppingCartItemsList.get(i).getProductDetails().getPrice());
                        orderItem.setUnitPriceExclTax(shoppingCartItemsList.get(i).getProductDetails().getPrice());
                        orderItem.setUnitPriceInclTax(shoppingCartItemsList.get(i).getProductDetails().getPrice());
                        orderItem.setUnitPriceWithoutDiscExclTax(shoppingCartItemsList.get(i).getProductDetails().getPrice());
                        orderItem.setUnitPriceWithoutDiscInclTax(shoppingCartItemsList.get(i).getProductDetails().getPrice());
                        orderItem.setQuantity(shoppingCartItemsList.get(i).getQuantity());
                        orderItemArrayList.add(orderItem);

                    }
                    Intent addressIntent = new Intent(CartActivity.this, MyAddressActivity.class);
                    addressIntent.putParcelableArrayListExtra("OrderItems", (ArrayList<? extends Parcelable>) orderItemArrayList);
                    addressIntent.putParcelableArrayListExtra("ShoppingCartItems", (ArrayList<? extends Parcelable>) shoppingCartItemsList);
                    startActivity(addressIntent);

                } else {
                    Toast.makeText(CartActivity.this, "No Cart Items Found !", Toast.LENGTH_SHORT).show();

                }
            }
        });

    }

    private void getAllCartItems() {

        loadingOrdersdialog = new ProgressDialog(this);
        loadingOrdersdialog.setMessage("Please wait");
        loadingOrdersdialog.setCancelable(false);
        loadingOrdersdialog.show();


        compositeDisposable.add(mServices.getAllCartItemsByPhone(session.getUserDetails().get(UserSessionManager.KEY_PHONE).replace("+", ""))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<ShoppingCartItems>>() {
                    @Override
                    public void accept(List<ShoppingCartItems> orders) throws Exception {

                        if (orders.size() != 0) {
                            rvCartList.setVisibility(View.VISIBLE);
                            btnCartContinue.setVisibility(View.VISIBLE);
                            LLEmptyCart.setVisibility(View.GONE);
                            Log.e(TAG, "accept: ");
                            displayCartList(orders);
                        } else {
                            loadingOrdersdialog.dismiss();
                            LLEmptyCart.setVisibility(View.VISIBLE);
                            btnShopnow.setVisibility(View.VISIBLE);
                            btnCartContinue.setVisibility(View.GONE);
                            rvCartList.setVisibility(View.GONE);
                        }


                    }
                }));
    }

    private void displayCartList(List<ShoppingCartItems> shoppingCartItemsList) {

        CartAdapter cartAdapter = new CartAdapter(CartActivity.this, shoppingCartItemsList);
        rvCartList.setAdapter(cartAdapter);
        cartAdapter.notifyDataSetChanged();
        this.shoppingCartItemsList = shoppingCartItemsList;
        loadingOrdersdialog.dismiss();

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }

    @Override
    protected void onStop() {
        compositeDisposable.clear();
        super.onStop();
    }

}
