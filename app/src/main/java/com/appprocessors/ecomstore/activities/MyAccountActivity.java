package com.appprocessors.ecomstore.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.appprocessors.ecomstore.R;
import com.appprocessors.ecomstore.utils.UserSessionManager;
import com.google.android.material.button.MaterialButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MyAccountActivity extends AppCompatActivity {


    // User Session Manager Class
    UserSessionManager session;
    @BindView(R.id.iv_profile_pic)
    ImageView ivProfilePic;
    @BindView(R.id.tv_customer_name)
    TextView tvCustomerName;
    @BindView(R.id.tv_customer_phone)
    TextView tvCustomerPhone;
    @BindView(R.id.ib_edit_profile)
    ImageButton ibEditProfile;
    @BindView(R.id.CV_orders)
    CardView CVOrders;
    @BindView(R.id.CV_wishlist)
    CardView CVWishlist;
    @BindView(R.id.CV_my_addressess)
    CardView CVMyAddressess;
    @BindView(R.id.CV_notifications)
    CardView CVNotifications;
    @BindView(R.id.CV_app_language)
    CardView CVAppLanguage;
    @BindView(R.id.btn_logout)
    MaterialButton btnLogout;
    @BindView(R.id.ivErrorIcon)
    ImageView ivErrorIcon;
    @BindView(R.id.tvError)
    TextView tvError;
    @BindView(R.id.btnTryAgain)
    Button btnTryAgain;
    @BindView(R.id.LL_Product_List_Error)
    LinearLayout LLProductListError;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_account);
        ButterKnife.bind(this);

        //Set Back Button to Toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        // Session class instance
        session = new UserSessionManager(getApplicationContext());
        setCustomerDetails();

        ibEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyAccountActivity.this, EditProfileActivity.class);
                startActivity(intent);
            }
        });

        CVOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyAccountActivity.this, MyOrdersActivity.class);
                startActivity(intent);
            }
        });

        CVWishlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyAccountActivity.this, WishlistActivity.class);
                startActivity(intent);
            }
        });

        CVMyAddressess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyAccountActivity.this, MyAddressActivity.class);
                startActivity(intent);
            }
        });

        CVNotifications.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MyAccountActivity.this, "Coming Soon !", Toast.LENGTH_SHORT).show();
            }
        });

        CVAppLanguage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MyAccountActivity.this, "Coming Soon !", Toast.LENGTH_SHORT).show();
            }
        });
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                session.logoutUser();
            }
        });


    }

    private void setCustomerDetails() {


        if (session.isUserLoggedIn()) {

            if (session.getUserDetails().get(UserSessionManager.KEY_PHONE) != null && session.getUserDetails().get(UserSessionManager.KEY_FIRST_NAME) != null && session.getUserDetails().get(UserSessionManager.KEY_LAST_NAME) != null) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(session.getUserDetails().get(UserSessionManager.KEY_FIRST_NAME)).append(" ").append(session.getUserDetails().get(UserSessionManager.KEY_LAST_NAME));
                tvCustomerName.setText(stringBuilder.toString());
                tvCustomerPhone.setText(session.getUserDetails().get(UserSessionManager.KEY_PHONE).contains("+91") ? session.getUserDetails().get(UserSessionManager.KEY_PHONE) : "+91" + session.getUserDetails().get(UserSessionManager.KEY_PHONE));
                ivProfilePic.setImageDrawable(session.getUserDetails().get(UserSessionManager.KEY_GENDER).equalsIgnoreCase("M") ? getResources().getDrawable(R.drawable.male) : getResources().getDrawable(R.drawable.female));
            } else {
                // user is not logged in redirect him to Login Activity
                Intent i = new Intent(this, LoginSignUp.class);

                // Closing all the Activities from stack
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                // Add new Flag to start new Activity
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                finish();
                // Staring Login Activity
                startActivity(i);

            }
        } else {
            // user is not logged in redirect him to Login Activity
            Intent i = new Intent(this, LoginSignUp.class);

            // Closing all the Activities from stack
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            // Add new Flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            finish();
            // Staring Login Activity
            startActivity(i);

        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }

}
