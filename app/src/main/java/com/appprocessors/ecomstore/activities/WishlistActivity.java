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
import com.appprocessors.ecomstore.adapter.WishlistAdapter;
import com.appprocessors.ecomstore.model.customer.ShoppingCartItems;
import com.appprocessors.ecomstore.retrofit.IEStoreAPI;
import com.appprocessors.ecomstore.retrofit.RetrofitClient;
import com.appprocessors.ecomstore.utils.Common;
import com.appprocessors.ecomstore.utils.CommonOptionMenu;
import com.appprocessors.ecomstore.utils.UserSessionManager;
import com.google.android.material.appbar.AppBarLayout;

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
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WishlistActivity extends CommonOptionMenu {


    CompositeDisposable compositeDisposable = new CompositeDisposable();

    ProgressDialog loadingOrdersdialog;

    IEStoreAPI mServices;

    // User Session Manager Class
    UserSessionManager session;

    //Cart List Items
    List<ShoppingCartItems> shoppingCartItemsList = null;

    private static final String TAG = "WishlistActivity";
    @BindView(R.id.notes_toolbar)
    Toolbar notesToolbar;
    @BindView(R.id.appBarLayout)
    AppBarLayout appBarLayout;
    @BindView(R.id.iv_no_wishlist)
    ImageView ivNoWishlist;
    @BindView(R.id.tv_wishlist_main)
    TextView tvWishlistMain;
    @BindView(R.id.btn_shopnow)
    Button btnShopnow;
    @BindView(R.id.LL_empty_wishlist)
    public LinearLayout LLEmptyWishlist;
    @BindView(R.id.rv_wishlist)
    public RecyclerView rvWishlist;
    @BindView(R.id.btn_remove_all)
    public Button btnRemoveAll;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wishlist);
        ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.notes_toolbar);
        setSupportActionBar(toolbar);
        setTitle("My Wishlist");

        //Set Back Button to Toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        // User Session Manager
        session = new UserSessionManager(getApplicationContext());

        mServices = Common.getAPI(this);

        rvWishlist.setLayoutManager(new LinearLayoutManager(this));
        rvWishlist.setHasFixedSize(true);
        // Load Wishlist Items
        getAllWishlistItems();

        btnShopnow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WishlistActivity.this, HomeActivity.class);
                // Add new Flag to start new Activity
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });

        //Remove All  Button Clicked
        btnRemoveAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (shoppingCartItemsList.size() != 0) {

                    loadingOrdersdialog = new ProgressDialog(WishlistActivity.this);
                    loadingOrdersdialog.setMessage("Please wait");
                    loadingOrdersdialog.setCancelable(false);
                    loadingOrdersdialog.show();


                    Call<Void> call = RetrofitClient.getRestClient(WishlistActivity.this).deleteAllWishListItemsByPhone(session.getUserDetails().get(UserSessionManager.KEY_PHONE).replace("+", ""));
                    call.enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            if (response.isSuccessful()) {
                                loadingOrdersdialog.dismiss();
                                rvWishlist.setVisibility(View.GONE);
                                btnRemoveAll.setVisibility(View.GONE);
                                LLEmptyWishlist.setVisibility(View.VISIBLE);


                            }
                        }

                        @Override
                        public void onFailure(Call call, Throwable t) {
                            loadingOrdersdialog.dismiss();

                            Toast.makeText(WishlistActivity.this, "Error :" + t.getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    });


                }
            }
        });
    }

    private void getAllWishlistItems() {


        loadingOrdersdialog = new ProgressDialog(WishlistActivity.this);
        loadingOrdersdialog.setMessage("Please wait");
        loadingOrdersdialog.setCancelable(false);
        loadingOrdersdialog.show();

        compositeDisposable.add(mServices.getAllWishlistItemsByPhone(session.getUserDetails().get(UserSessionManager.KEY_PHONE).replace("+", ""))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<ShoppingCartItems>>() {
                    @Override
                    public void accept(List<ShoppingCartItems> orders) throws Exception {

                        if (orders.size() != 0) {
                            rvWishlist.setVisibility(View.VISIBLE);
                            btnRemoveAll.setVisibility(View.VISIBLE);
                            LLEmptyWishlist.setVisibility(View.GONE);
                            Log.e(TAG, "accept: ");
                            displayWishList(orders);
                        } else {
                            loadingOrdersdialog.dismiss();
                            LLEmptyWishlist.setVisibility(View.VISIBLE);
                            btnShopnow.setVisibility(View.VISIBLE);
                            rvWishlist.setVisibility(View.GONE);
                            btnRemoveAll.setVisibility(View.GONE);
                        }


                    }
                }));

    }

    private void displayWishList(List<ShoppingCartItems> shoppingCartItemsList) {

        WishlistAdapter wishlistAdapter = new WishlistAdapter(this, shoppingCartItemsList);
        rvWishlist.setAdapter(wishlistAdapter);
        wishlistAdapter.notifyDataSetChanged();
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
