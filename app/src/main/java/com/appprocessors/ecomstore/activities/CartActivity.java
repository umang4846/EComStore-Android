package com.appprocessors.ecomstore.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.appprocessors.ecomstore.R;

import org.fabiomsr.moneytextview.MoneyTextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.disposables.CompositeDisposable;

public class CartActivity extends AppCompatActivity {

    CompositeDisposable compositeDisposable;

    RecyclerView cart_List;
    LinearLayout emptyCart, cartTotalPriceContinue;
    View viewDeividerMain;

    Button shopnow;

    @BindView(R.id.tv_cart_total_price)
    MoneyTextView tvCartTotalPrice;
    @BindView(R.id.tv_view_price_details)
    TextView tvViewPriceDetails;
    @BindView(R.id.btn_cart_continue)
    Button btnCartContinue;

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

        //Set Back Button to Toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        //Linear Layout for Empty Cart view
        emptyCart = findViewById(R.id.LL_empty_cart);

        cart_List = findViewById(R.id.rv_cart_list);
        cartTotalPriceContinue = findViewById(R.id.LL_cart_total_price_continue);
        viewDeividerMain = findViewById(R.id.cart_main_devider);

        compositeDisposable = new CompositeDisposable();
        cart_List.setLayoutManager(new LinearLayoutManager(this));
        cart_List.setHasFixedSize(true);
        //Load Cart Items
      //  loadCartItems();

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
        shopnow = findViewById(R.id.btn_shopnow);
        shopnow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CartActivity.this, HomeActivity.class));
                finish();
            }
        });
        //Continue Button Clicked
        btnCartContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(CartActivity.this, "Continue Clicked", Toast.LENGTH_SHORT).show();
            }
        });

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
