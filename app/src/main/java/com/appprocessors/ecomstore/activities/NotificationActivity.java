package com.appprocessors.ecomstore.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.appprocessors.ecomstore.R;
import com.appprocessors.ecomstore.utils.CommonOptionMenu;

import androidx.appcompat.widget.Toolbar;
import butterknife.BindView;
import butterknife.ButterKnife;

public class NotificationActivity extends CommonOptionMenu {

    @BindView(R.id.tv_no_notification)
    TextView tvNoNotification;
    @BindView(R.id.iv_no_notification)
    ImageView ivNoNotification;
    @BindView(R.id.tv_no_notifications_desc)
    TextView tvNoNotificationsDesc;
    @BindView(R.id.btn_no_notification_shoping)
    Button btnNoNotificationShoping;
    @BindView(R.id.FL_no_notifications)
    FrameLayout FLNoNotifications;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.notes_toolbar);
        setSupportActionBar(toolbar);
        setTitle("Notifications");

        //Set Back Button to Toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        btnNoNotificationShoping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startShopping = new Intent(NotificationActivity.this, HomeActivity.class);
                startShopping.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(startShopping);
                finish();
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
