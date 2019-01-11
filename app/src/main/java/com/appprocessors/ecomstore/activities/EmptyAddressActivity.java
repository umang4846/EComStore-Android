package com.appprocessors.ecomstore.activities;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.appprocessors.ecomstore.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EmptyAddressActivity extends AppCompatActivity {

    @BindView(R.id.iv_empty_cart)
    ImageView ivEmptyCart;
    @BindView(R.id.tv_empty_address_main)
    TextView tvEmptyAddressMain;
    @BindView(R.id.tv_empty_address_small)
    TextView tvEmptyAddressSmall;
    @BindView(R.id.btn_empty_address_add_address)
    Button btnEmptyAddressAddAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empty_address);
        ButterKnife.bind(this);

        //Set Back Button to Toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }


        btnEmptyAddressAddAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EmptyAddressActivity.this,AddAddressActivity.class);
                startActivity(intent);
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
